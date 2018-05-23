package larvaGenerator;

import RE.lib.basic.Symbol;
import RE.parser.ParsedRE;
import RE.parser.Parser;
import larvaGenerator.exceptions.IllegalParametersException;
import util.FileOutput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

class Generator_RE extends AbstractLarvaGenerator {

    private static final String ISEMPTY = ".isEmpty()";
    private static final String HASEMPTY = ".hasEmptyString()";

    private LinkedHashSet<Symbol> symbolsCopy = null;
    private Iterator<String> paramIterator = null;
    private List<String> foreachParamList = null;

    Generator_RE(final String input, final BooleanArguments boolArgs) {
        this(input, DEFAULT_OUT_FOLDER, boolArgs);
    }

    Generator_RE(final String input, final String outFolder, final BooleanArguments boolArgs) {
        super(input, outFolder, boolArgs);

        // Perform main operations
        int counter = 0;
        try {
            parseResult = new Parser(true, true).parse(input);
            for (final ParsedRE pre : parseResult) {
                symbolsCopy = new LinkedHashSet<>(pre.getAlphabet());
                paramIterator = pre.getParameters().iterator();
                foreachParamList = new ArrayList<>();
                createScript(pre, String.format(DEFAULT_SCRIPT_NAME_NUMBERED, ++counter), pre.getOriginalInput());
            }
        } catch (Exception e) {
            e.printStackTrace();
            terminateGenerator(counter);
        }
        successMessage(counter);
    }

    private void createScript(final ParsedRE re, final String scriptName, final String toParse) throws IOException {

        final String script = getImportsSection(re) + "\n\n" + getContext(re, toParse, true) + "\n";
        final String tabified = tabify(script);

        System.out.print("Generating " + scriptName + "...");
        FileOutput.outputStringToFile(outFolder, scriptName, tabified);
        System.out.println("DONE.");
    }

    //------------------------------------------------------------------------------------------ Sections

    private String getImportsSection(final ParsedRE re) {
        final String paramImports = re.getParamClassMap().values().stream().distinct().map(c -> "import " + c + ";\n").collect(Collectors.joining());
        return "IMPORTS{\n" +
                "import java.util.*;\n" +
                "import larvaTools.LarvaController;\n" +
                "import larvaTools.RE.RegExpHolder;\n" +
                "import RE.lib.basic.Symbol;\n" +
                paramImports +
                "}";
    }

    private String getContext(final ParsedRE re, final String toParse, final boolean isGlobalContext) throws IllegalParametersException {

        // Add the parameter in consideration to the foreach parameter list
        if (!isGlobalContext && paramIterator.hasNext()) {
            foreachParamList.add(paramIterator.next());
        }

        // Come up with the subset of symbols for which events will be defined in this context
        final LinkedHashSet<Symbol> symSubset = new LinkedHashSet<>();
        for (final Symbol s : symbolsCopy) {
            if (foreachParamList.containsAll(s.getParams())) {
                if (foreachParamList.size() != s.getParams().size()) {
                    throw new IllegalParametersException(re.getOriginalInput(), s.getSymbol());
                }
                symSubset.add(s);
            }
        }
        symbolsCopy.removeAll(symSubset);

        // Come up with the context title
        final String contextTitle;
        if (isGlobalContext) {
            contextTitle = "GLOBAL";
        } else {
            final String latestParam = foreachParamList.get(foreachParamList.size() - 1);
            final String latestClass = re.getParamClassMap().get(latestParam);
            contextTitle = "FOREACH(" + toJavaClassName(latestClass) + " _" + latestParam + ")";
        }

        // Return the context (possibly including variables, events, and sub-context)
        final boolean lastForeach = !paramIterator.hasNext();
        return contextTitle + "{\n\n" +
                (isGlobalContext || lastForeach ?
                        getVariablesSection(re, isGlobalContext, lastForeach, toParse) + "\n\n" : "") +
                (!symSubset.isEmpty() || isGlobalContext ?
                        getEventsSection(symSubset, re, isGlobalContext) + "\n\n" : "") +
                (paramIterator.hasNext() ?
                        getContext(re, toParse, false) + "\n" :
                        getPropertySection(re) + "\n") +
                "}";
    }

    private String getVariablesSection(final ParsedRE re, final boolean isGlobalContext, final boolean lastForeach, final String toParse) {
        return "VARIABLES{\n" +
                (isGlobalContext ? "" +
                        "int exprIndex = RegExpHolder.parseAndStore(\"" + toParse + "\");\n" +
                        getSymbolVariablesList(re.getAlphabet()) : "") +
                (isGlobalContext && lastForeach ? "\n" : "") +
                (lastForeach ? "" +
                        "RegExpHolder reh = null;\n" +
                        ifTest("int currentState = 0;\n") : "") +
                "}";
    }

    private String getEventsSection(final LinkedHashSet<Symbol> symSubset, final ParsedRE re, final boolean isGlobalContext) {
        return "EVENTS{ " + (!symSubset.isEmpty() ? "" : "") + "\n" +
                getSymbolEventsList(re, symSubset) +
                (isGlobalContext ? "" +
                        "timeout_isE(int id) = { RegExpHolder r.triggerTimeout_isEmpty() } where { id = r.getId(); }\n" +
                        (re.isNegative() ? "timeout_hasE(int id) = { RegExpHolder r.triggerTimeout_hasEmptyString() } where { id = r.getId(); }\n" : "") +
                        ifTest(getEvent(LARVA_RESET, "LarvaController *.triggerReset()")) +
                        ifTest(getEvent(LARVA_STOP, "LarvaController *.triggerStop()")) : "") +
                "}";

    }

    private String getPropertySection(final ParsedRE re) {
        return "PROPERTY property{\n\n" +
                getStatesSection(re) + "\n\n" +
                getTransitionsSection(re) + "\n" +
                "}";
    }

    private String getStatesSection(final ParsedRE re) {
        return "STATES{\n" +
                (re.isNegative() ? "ACCEPTING{ " + GOOD + ifTest("{ currentState = 1; }") + "}\n" : "") +
                "BAD{ " + BAD + ifTest("{ currentState = -1; }", " ") + "}\n" +
                ifTest("NORMAL { stopped }\n") +
                getStartingState() + "\n" +
                "}";
    }

    private String getTransitionsSection(final ParsedRE re) {
        final String reCondition = re.isPositive() ? ISEMPTY : HASEMPTY;
        return "TRANSITIONS{\n" +
                getTimeoutTransitions(re) +
                getSymbolTransitionsList(re, reCondition) +
                (testing ? getResetTransitionsList() : "") +
                (testing ? getStopTransitionsList(re) : "") +
                "}";
    }

    //------------------------------------------------------------------------------------------ Lists

    private String getSymbolVariablesList(final LinkedHashSet<Symbol> symSubset) {
        return symSubset.stream().map(AbstractLarvaGenerator::getSymbolVariable).collect(joining());
    }

    private String getSymbolEventsList(final ParsedRE re, final LinkedHashSet<Symbol> symSubset) {
        if (!foreachParamList.isEmpty()) {
            final String lastParam = foreachParamList.get(foreachParamList.size() - 1);
            return symSubset.stream().map(s -> getSymbolEvent(s, lastParam, re.getParamClassMap())).collect(joining());
        } else {
            return symSubset.stream().map(AbstractLarvaGenerator::getSymbolEvent).collect(joining());
        }
    }

    private String getTimeoutTransitions(final ParsedRE re) {
        final String suffix = re.isPositive() ? "_isE" : "_hasE";
        return getTransition(START, BAD, ("timeout" + suffix), "reh.getId()==id", "reh.stop();") +
                (re.isNegative() ? getTransition(START, GOOD, "timeout_isE", "reh.getId()==id", "reh.stop();") : "");
    }

    private String getSymbolTransitionsList(final ParsedRE re, final String reCondition) {

        final StringBuilder transitions = new StringBuilder();

        // Bad-state transitions based on the reCondition (depends on whether bad or good-behaviour)
        for (final Symbol symbol : re.getAlphabet()) {
            transitions.append(getBadStateTransition(symbol, reCondition));
        }

        // Accepting transitions in the case of a bad-behaviour expression
        if (re.isNegative()) {
            for (final Symbol symbol : re.getAlphabet()) {
                transitions.append(getAcceptStateTransition(symbol, ISEMPTY));
            }
        }

        return transitions.toString();
    }

    private String getResetTransitionsList() {
        return getTransition(STOPPED, START, evt(LARVA_RESET));
    }

    private String getStopTransitionsList(final ParsedRE re) {
        return getTransition(START, STOPPED, evt(LARVA_STOP), "", "reh.stop();") +
                getTransition(BAD, STOPPED, evt(LARVA_STOP), "", "reh.stop();") +
                (re.isNegative() ? getTransition(GOOD, STOPPED, evt(LARVA_STOP), "", "reh.stop();") : "");
    }

    //------------------------------------------------------------------------------------------ States

    private String getStartingState() {
        return "STARTING{ " + START + "{ " +
                ifTest("currentState = 0; ") +
                "reh = new RegExpHolder(exprIndex); " +
                "}}";
    }

    //------------------------------------------------------------------------------------------ Transitions

    private static String getBadStateTransition(final Symbol symbol, final String condition) {
        return getTransition(START, BAD, evt(paramsName(symbol)), ("reh.toDerivative(" + sym(paramsName(symbol)) + ")" + condition), "reh.stop();");
    }

    private static String getAcceptStateTransition(final Symbol symbol, final String condition) {
        return getTransition(START, GOOD, evt(paramsName(symbol)), ("reh.getRegExp()" + condition), "reh.stop();");
    }
}
