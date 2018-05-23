package larvaGenerator;

import RE.lib.basic.Symbol;
import RE.parser.ParsedRE;
import RE.parser.Parser;
import TA.lib.*;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import larvaGenerator.exceptions.IllegalParametersException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.status.StatusLogger;
import util.FileOutput;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

class Generator_TA extends AbstractLarvaGenerator {

    private final static String BAD_MSG = "bad"; // message sent to parent upon violation
    private final static String GOOD_MSG = "good"; // message sent to parent upon acceptance

    private LinkedHashSet<Symbol> symbolsCopy = null;
    private Iterator<String> paramIterator = null;
    private List<String> foreachParamList = null;

    private Map<String, String> paramClassMap = null;
    private List<String> anySymbolEvents = null;
    private String originalInput = null;

    Generator_TA(final String input, final BooleanArguments boolArgs) {
        this(input, DEFAULT_OUT_FOLDER, boolArgs);
    }

    Generator_TA(final String input, final String outFolder, final BooleanArguments boolArgs) {
        super(input, outFolder, boolArgs);

        if (boolArgs.outputDOT) {
            // Disable logging (for Graphviz-Java dependency)
            StatusLogger.getLogger().setLevel(Level.OFF);
        }

        // Perform main operations
        int scriptsGenerated = 0;
        try {
            parseResult = new Parser(true, true).parse(input);
            for (final ParsedRE pre : parseResult) {

                final TimedAutomaton ta = pre.toTimedAutomaton();
                //outputPNG(ta, String.format("script_%d_original.png", scriptsGenerated + 1));

                ta.eliminateNonDetAndSilentTrans();
                if (boolArgs.outputPNG) {
                    outputPNG(ta, String.format("script_%d.png", scriptsGenerated + 1));
                }
                if (boolArgs.outputDOT) {
                    outputDOT(ta, String.format("script_%d.dot", scriptsGenerated + 1));
                }
                ta.setStateNamesAndClockIDs();

                // Initialize members
                symbolsCopy = new LinkedHashSet<>(ta.getAlphabet());
                paramIterator = pre.getParameters().iterator();
                foreachParamList = new ArrayList<>();
                paramClassMap = pre.getParamClassMap();
                anySymbolEvents = new ArrayList<>();
                originalInput = pre.getOriginalInput();

                final String scriptName = String.format(DEFAULT_SCRIPT_NAME_NUMBERED, scriptsGenerated + 1);
                outputScript(ta, scriptName, pre.isPositive());
                scriptsGenerated++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            terminateGenerator(scriptsGenerated);
        }
        successMessage(scriptsGenerated);
    }

    private void outputDOT(final TimedAutomaton ta, final String fileName) throws IOException {
        System.out.print("Generating " + fileName + "...");
        FileOutput.outputStringToFile(outFolder, fileName, ta.toString());
        System.out.println("DONE.");
    }

    private void outputPNG(final TimedAutomaton ta, final String fileName) throws IOException {
        System.out.print("Generating " + fileName + "...");
        Graphviz.fromString(ta.toString()).render(Format.PNG).toFile(new File(outFolder, fileName));
        System.out.println("DONE.");
    }

    private void outputScript(final TimedAutomaton ta, final String scriptName, final boolean isPositive) throws IOException {

        final String script = getImportsSection() + "\n\n" + getContext(ta, isPositive, true) + "\n\n" + getMethodsSection(ta);
        final String tabified = tabify(script);

        System.out.print("Generating " + scriptName + "...");
        FileOutput.outputStringToFile(outFolder, scriptName, tabified);
        System.out.println("DONE.");
    }

    //------------------------------------------------------------------------------------------ Sections

    private String getImportsSection() {
        final String paramImports = paramClassMap.values().stream().distinct().map(c -> "import " + c + ";\n").collect(Collectors.joining());
        return "IMPORTS{\n" +
                "import java.util.*;\n" +
                "import larvaTools.LarvaController;\n" +
                "import larvaTools.TA.AutomatonInstance;\n" +
                "import larvaTools.TA.StateSkip;\n" +
                "import larvaTools.TA.Outcome;\n" +
                paramImports +
                "}";
    }

    private String getContext(final TimedAutomaton ta, final boolean isPositive, final boolean isGlobalContext) throws IllegalParametersException {

        // Add the parameter in consideration to the foreach parameter list
        if (!isGlobalContext && paramIterator.hasNext()) {
            foreachParamList.add(paramIterator.next());
        }

        // Come up with the subset of symbols for which events will be defined in this context
        final LinkedHashSet<Symbol> symSubset = new LinkedHashSet<>();
        for (final Symbol s : symbolsCopy) {
            if (foreachParamList.containsAll(s.getParams())) {
                if (foreachParamList.size() != s.getParams().size()) {
                    throw new IllegalParametersException(originalInput, s.getSymbol());
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
            final String latestClass = paramClassMap.get(latestParam);
            contextTitle = "FOREACH(" + toJavaClassName(latestClass) + " _" + latestParam + ")";
        }

        // Return the context (possibly including variables, events, and sub-context or TaGlobalSection)
        final boolean lastForeach = !paramIterator.hasNext();
        return contextTitle + "{\n\n" +
                (lastForeach ? getVariablesSection() + "\n\n" : "") +
                (!symSubset.isEmpty() || isGlobalContext || lastForeach ? getEventsSection(symSubset, isGlobalContext, lastForeach) + "\n\n" : "") +
                (!lastForeach ?
                        getContext(ta, isPositive, false) + "\n" :
                        getTaGlobalSection(ta, isPositive) + "\n") +
                "}";
    }

    private String getVariablesSection() {
        return "VARIABLES{\n" +
                getChannelVariable("stateSkip") +
                getChannelVariable("TAsToMain") +
                "int instanceCount = 0;\n" +
                ifTest("int currentState = 0;\n") +
                "}";
    }

    private String getEventsSection(final LinkedHashSet<Symbol> symSubset, final boolean isGlobalContext, final boolean lastForeach) {

        // Come up with strings which will be used for the "any symbol" event
        final String allSymbolEvents;
        final String anySymbolName;
        if (!symSubset.isEmpty()) {
            allSymbolEvents = symSubset.stream().map(s -> evt(paramsName(s))).collect(joining(" | "));
            anySymbolName = "anySymbol" + (isGlobalContext ? "" : "_" + foreachParamList.get(foreachParamList.size() - 1));
            anySymbolEvents.add(anySymbolName);
        } else {
            allSymbolEvents = anySymbolName = "";
        }

        // Come up with assignments of all foreach parameter variables, which are passed through
        // the Outcome object when an automaton instance reaches a bad or accepting state.
        final StringBuilder allVars = new StringBuilder();
        if (lastForeach) {
            for (int i = 0; i < foreachParamList.size(); i++) {
                final String p = foreachParamList.get(i);
                final String c = toJavaClassName(paramClassMap.get(p));
                allVars.append("\n_" + p + " = (" + c + ") outcome.getVar(" + i + "); ");
            }
        }

        // Return the events section
        return "EVENTS{" + (!symSubset.isEmpty() ? "" : "") + "\n" +
                (!symSubset.isEmpty() ? "" +
                        getSymbolEventsList(symSubset) +
                        getEvent(anySymbolName, allSymbolEvents) : "") +
                (lastForeach ?
                        (!symSubset.isEmpty() ? "\n" : "") +
                                String.format("%s(String state) = { %s.receive(Object outcomeObj); } where { \n" +
                                                "final Outcome outcome = (Outcome) outcomeObj;\n" +
                                                "state = outcome.getState(); %s\n" +
                                                "}\n",
                                        evt("channelTAsToMain"), channel("TAsToMain"), allVars.toString()) : "") +
                (isGlobalContext ?
                        (!symSubset.isEmpty() || lastForeach ? "\n" : "") +
                                ifTest(getEvent(LARVA_RESET, "LarvaController *.triggerReset()")) +
                                ifTest(getEvent(LARVA_STOP, "LarvaController *.triggerStop()")) : "") +
                "}";
    }

    private String getTaGlobalSection(final TimedAutomaton ta, final boolean isPositive) {
        return getTaGlobalPropertySection(ta, isPositive) + "\n\n" +
                "FOREACH(AutomatonInstance ta){\n\n" +
                getTaForeachVariablesSection(ta) + "\n\n" +
                getTaForeachEventsSection(ta) + "\n\n" +
                getTaForeachPropertySection(ta, isPositive) + "\n" +
                "}";
    }

    private String getTaGlobalPropertySection(final TimedAutomaton ta, final boolean isPositive) {
        return "PROPERTY main{\n" +
                getTaGlobalStatesSection(ta, isPositive) + "\n" +
                getTaGlobalTransitionsSection(ta, isPositive) + "\n" +
                "}";
    }

    private String getTaGlobalStatesSection(final TimedAutomaton ta, final boolean isPositive) {
        return "STATES{\n" +
                (!isPositive ? "ACCEPTING{ " + GOOD + ifTest("{ currentState = 1; }", " ") + "}\n" : "") +
                "BAD{ " + BAD + ifTest("{ currentState = -1; }", " ") + "}\n" +
                ifTest("NORMAL{ " + STOPPED + " }\n") +
                getTaGlobalStartState(ta) + "\n" +
                "}";
    }

    private String getTaGlobalTransitionsSection(final TimedAutomaton ta, final boolean isPositive) {
        final String START = ta.getInitialState().name;
        return "TRANSITIONS{\n" +
                getTransition(START, BAD, evt("channelTAsToMain"), "state.equals(\"" + BAD_MSG + "\")" + (isPositive ? " && --instanceCount == 0" : ""), "") +
                (!isPositive ? getTransition(START, GOOD, evt("channelTAsToMain"), "state.equals(\"" + GOOD_MSG + "\") && --instanceCount == 0", "") : "") +
                ifTest(getTransition(START, STOPPED, evt(LARVA_STOP), "", "instanceCount = 0;") +
                        getTransition(BAD, STOPPED, evt(LARVA_STOP), "", "instanceCount = 0;") +
                        (!isPositive ? getTransition(GOOD, STOPPED, evt(LARVA_STOP), "", "instanceCount = 0;") : "") +
                        getTransition(STOPPED, START, evt(LARVA_RESET), "", "")) +
                "}";
    }

    private String getTaForeachVariablesSection(final TimedAutomaton ta) {
        return "VARIABLES{ " +
                (!ta.getClocks().isEmpty() ? "\n" + getClockVariablesList(ta) : "") +
                "}";
    }

    private String getTaForeachEventsSection(final TimedAutomaton ta) {

        // Come up with all events of the two types of clock events (for clocks with timeouts)
        final boolean anyTimeoutClocks = ta.getClocks().stream().anyMatch(Clock::hasTimeout);
        final String allClockEvents1 = ta.getClocks().stream().filter(Clock::hasTimeout).map(c -> evt(clock(c))).collect(joining(" | "));
        final String allClockEvents2 = ta.getClocks().stream().filter(Clock::hasTimeout).map(c -> evt(clock_dyn(c))).collect(joining(" | "));

        // Come up with assignments of all foreach parameter variables which are passed
        // through the StateSkip object when the new automaton instance is created.
        final StringBuilder allVars = new StringBuilder();
        for (int i = 0; i < foreachParamList.size(); i++) {
            final String p = foreachParamList.get(i);
            final String c = toJavaClassName(paramClassMap.get(p));
            allVars.append("\n_" + p + " = (" + c + ") ss.getVar(" + i + "); ");
        }

        // Return the events section
        return "EVENTS{\n" +
                getClockEventsList(ta) +
                (anyTimeoutClocks ? getEvent("anyTimeout", allClockEvents1 + " | " + allClockEvents2) : "") +
                String.format("%s(StateSkip ss) = { %s.receive(Object ssObj); } where { " +
                        "\nss = (StateSkip) ssObj; \nta = ss.getInstance(); %s\n}\n" +
                        "}", evt("channelStateSkip"), channel("stateSkip"), allVars.toString());
    }

    private String getTaForeachPropertySection(final TimedAutomaton ta, final boolean isPositive) {
        return "PROPERTY property{\n" +
                getTaForeachStatesSection(ta, isPositive) + "\n" +
                getTaForeachTransitionsSection(ta, isPositive) + "\n" +
                "}";
    }

    private String getTaForeachStatesSection(final TimedAutomaton ta, final boolean isPositive) {
        return "STATES{\n" +
                getTaForeachNormalStates(ta, isPositive) + "\n" +
                getTaForeachStartingState(ta, isPositive) + "\n" +
                "}";
    }

    private String getTaForeachTransitionsSection(final TimedAutomaton ta, final boolean isPositive) {
        return "TRANSITIONS{\n" +
                getSymbolTransitionsList(ta, isPositive) +
                getStateSkipTransitionsList(ta, isPositive) +
                getTotalityTransitionsList(ta, isPositive) +
                "}";
    }

    private String getMethodsSection(final TimedAutomaton ta) {

        final StringBuilder clockTimeoutValues = new StringBuilder();
        for (final Clock c : ta.getClocks()) {
            if (c.hasTimeout()) {
                clockTimeoutValues.append((clockTimeoutValues.length() > 0 ? ", " : "")).append(c.getTimeoutInMillis());
            }
        }

        return "METHODS{\n" +
                /**/"void setClocks(final StateSkip ss, final Clock...clocks) {\n" +
                /*    */"final long timeouts[] = { " + clockTimeoutValues.toString() + " };\n" +
                /*    */"final Clock ssClocks[] = ss.getClocks();\n" +
                /*    */"if (ssClocks != null) {\n" +
                /*        */"for (int i = 0; i < ssClocks.length; i++) {\n" +
                /*            */"if (ssClocks[i] != null) {\n" +
                /*                */"if (ssClocks[i].paused) {\n" +
                /*                    */"clocks[i].pause();\n" +
                /*                */"} else {\n" +
                /*                    */"final long newValueLong = System.currentTimeMillis() - ssClocks[i].current_long();\n" +
                /*                    */"clocks[i].registerDynamically(newValueLong + timeouts[i], newValueLong);\n" +
                /*                */"}\n" +
                /*            */"}\n" +
                /*        */"}\n" +
                /*    */"}\n" +
                /**/"}\n" +
                "}";
    }

    //------------------------------------------------------------------------------------------ Lists

    private String getClockVariablesList(final TimedAutomaton ta) {
        return ta.getClocks().stream().map(AbstractLarvaGenerator::getClockVariable).collect(joining());
    }

    private String getSymbolEventsList(final LinkedHashSet<Symbol> symSubset) {
        if (!foreachParamList.isEmpty()) {
            final String lastParam = foreachParamList.get(foreachParamList.size() - 1);
            return symSubset.stream().map(s -> getSymbolEvent(s, lastParam, paramClassMap)).collect(joining());
        } else {
            return symSubset.stream().map(AbstractLarvaGenerator::getSymbolEvent).collect(joining());
        }
    }

    private String getClockEventsList(final TimedAutomaton ta) {
        final StringBuilder clockEvents_normal = new StringBuilder();
        final StringBuilder clockEvents_dynamic = new StringBuilder();
        for (final Clock c : ta.getClocks()) {
            if (c.hasTimeout()) {
                clockEvents_normal.append(getClockEvent(c));
                clockEvents_dynamic.append(getClockEvent_dynamic(c));
            }
        }
        return clockEvents_normal.toString() + clockEvents_dynamic.toString();
    }

    private String getSymbolTransitionsList(final TimedAutomaton ta, final boolean isPositive) {

        final StringBuilder transitions = new StringBuilder();
        for (final Transition t : ta.getTransitions()) {
            final String to = !isPositive && ta.isAcceptingState(t.to) ? BAD : t.to.name;
            transitions.append(getTransition(t.from.name, to, evt(paramsName(t.symbol)), t.guard, t.toReset));
        }
        return transitions.toString();
    }

    private String getStateSkipTransitionsList(final TimedAutomaton ta, final boolean isPositive) {
        final StringBuilder transitions = new StringBuilder();
        boolean initStateIncluded = false;
        for (final State s : ta.getStates()) {
            for (final StateSkip t : s.getStateSkips()) {
                transitions.append(getStateSkipTransition(ta, s.name, t.to, isPositive));
                initStateIncluded = initStateIncluded || isStartState(t.to, ta);
            }
        }
        if (!initStateIncluded) {
            // This is required since it's used by GLOBAL to set the initial timeouts
            transitions.append(getStateSkipTransition(ta, "main", ta.getInitialState(), isPositive));
        }
        return transitions.toString();
    }

    private String getTotalityTransitionsList(final TimedAutomaton ta, final boolean isPositive) {
        // Totality transitions (bad if positive, good if negative)
        // Note: in REs these transitions would cause a violation
        final StringBuilder totality = new StringBuilder();
        final String to = isPositive ? BAD : GOOD;
        final boolean anyTimeoutClocks = ta.getClocks().stream().anyMatch(Clock::hasTimeout);
        for (final State s : ta.getStates()) {
            // If state does not have state-skips or (has state-skips and...) has outward transitions
            if (s.getStateSkips().isEmpty() || ta.getTransitions().stream().anyMatch(t -> t.from.equals(s))) {
                for (final String evt : anySymbolEvents) {
                    totality.append(getTransition(s.name, to, evt(evt)));
                }
                if (anyTimeoutClocks) {
                    totality.append(getTransition(s.name, to, evt("anyTimeout")));
                }
            }
        }
        return totality.toString();
    }

    //------------------------------------------------------------------------------------------ States

    private String getTaGlobalStartState(final TimedAutomaton ta) {
        final String allParams = foreachParamList.stream().map(p -> "_" + p).collect(joining(", "));
        final String objectsArray = allParams.length() == 0 ? "" : ", new Object[]{" + allParams + "}";
        final String stateSkipString = "%s.send(new StateSkip(new AutomatonInstance(true, true), \"main_to_%s\"%s));\n";
        return "STARTING{\n" +
                START + " {\n" +
                ifTest("currentState = 0;\n") +
                "instanceCount = 1;\n" +
                String.format(stateSkipString, channel("stateSkip"), ta.getInitialState().name, objectsArray) +
                "}\n" +
                "}";
    }

    private String getTaForeachNormalStates(final TimedAutomaton ta, final boolean isPositive) {

        final String allParams = foreachParamList.stream().map(p -> "_" + p).collect(joining(", "));
        final String objectsArray = allParams.length() == 0 ? "" : ", new Object[]{" + allParams + "}";

        final StringBuilder overall = new StringBuilder();
        final StringBuilder plainStates = new StringBuilder();
        final StringBuilder withStateSkips = new StringBuilder();

        overall.append("NORMAL { ");
        for (final State s : ta.getStates()) {
            if (!isStartState(s, ta)) {
                // Note: don't set currentState (already set by start state)
                if (s.getStateSkips().isEmpty()) {
                    plainStates.append(s.name + " ");
                } else {
                    withStateSkips.append("\n" + s.name + " {\n" + getStateSkipActions(ta, s, isPositive) + "}");
                }
            }
        }
        overall.append(plainStates.toString());
        overall.append(withStateSkips.toString());
        overall.append("\n" +
                String.format(BAD + " { %s.send(new Outcome(\"" + BAD_MSG + "\"%s)); _killThis(); }\n", channel("TAsToMain"), objectsArray) +
                (!isPositive ? String.format(GOOD + " { %s.send(new Outcome(\"" + GOOD_MSG + "\"%s)); _killThis(); }\n", channel("TAsToMain"), objectsArray) : "")
        );
        overall.append("}");
        return overall.toString();
    }

    private String getTaForeachStartingState(final TimedAutomaton ta, final boolean isPositive) {

        final State INIT = ta.getInitialState();
        final boolean noSSkips = INIT.getStateSkips().isEmpty();
        final boolean noClocks = ta.getClocks().isEmpty();

        final String startState;
        if (noSSkips && noClocks) {
            startState = INIT.name + " ";
        } else {
            startState = "\n" + INIT.name + " {\n" +
                    (!noClocks ? getInitialClockPauses(ta) + "\n" : "") +
                    (!noSSkips ? "" +
                            "if (ta.shouldPerformStartActions) {\n" +
                            "%% The below actions should only be performed once\n" +
                            "ta.shouldPerformStartActions = false;\n" +
                            getStateSkipActions(ta, INIT, isPositive) +
                            "}\n" : "") +
                    "}\n";
        }
        return "STARTING{ " + startState + "}";
    }

    private String getStateSkipActions(final TimedAutomaton ta, final State state, final boolean isPositive) {

        if (state.getStateSkips().isEmpty()) {
            return "";
        }
        final StringBuilder stateSkipActions = new StringBuilder();

        // Come up with list of all parameters
        final String allParams = foreachParamList.stream().map(p -> "_" + p).collect(joining(", "));
        final String objectsArray = allParams.length() == 0 ? "" : ", new Object[]{" + allParams + "}";

        // Add state-skip-related actions
        for (final StateSkip stateSkip : state.getStateSkips()) {
            stateSkipActions.append("%% New branch\n");

            // Open if statement (if has a guard, add the conditions)
            if (!stateSkip.guard.isEmpty()) {
                final Stream<String> condStream = stateSkip.guard.stream().map(cc -> cc.toJavaCondition(AbstractLarvaGenerator::clock));
                final String condString = condStream.collect(joining(" && "));
                stateSkipActions.append("if (" + condString + ") {\n");
            } else {
                stateSkipActions.append("if (true) {\n");
            }

            // Come up with the current clock times
            final String allClocks = ta.getClocks().stream().map(c ->
                    stateSkip.toReset.contains(c) ? "null" : clock(c)).collect(joining(", "));

            // Append an instances increment and sending of state skip instructions,
            // which includes new TA instance, destination, and clock values as arguments
            stateSkipActions.append("instanceCount++;\n" +
                    String.format("%s.send(new StateSkip(", channel("stateSkip")) +
                    "new AutomatonInstance(" + isStartState(stateSkip.to, ta) + "), \"" + state + "_to_" + stateSkip.to.name + "\"" +
                    ", new Clock[]{" + allClocks + "}" +
                    objectsArray + "));\n");

            // Close if statement
            stateSkipActions.append("}\n");
        }

        // If state has no outgoing transitions, it turns off its clocks and sends a termination message
        //
        // ... but if we are dealing with a bad-behaviour automaton and the state in consideration
        // is the initial state and is an accepting state, sending of the termination message and
        // the automaton kill will be performed by the "main_to_start" transition to the bad state.
        if (!isPositive && state.equals(ta.getInitialState()) && ta.isAcceptingState(state)) {
            // do nothing
        } else if (ta.getTransitions().stream().noneMatch(t -> t.from.equals(state))) {
            stateSkipActions.append("%% Send termination message and kill the automaton\n");
            stateSkipActions.append(String.format("%s.send(new Outcome(\"" +
                    (isPositive ? BAD_MSG : GOOD_MSG) +
                    "\"%s));\n", channel("TAsToMain"), objectsArray));
            stateSkipActions.append("_killThis();\n");
        }

        return stateSkipActions.toString();
    }

    private String getInitialClockPauses(final TimedAutomaton ta) {

        // Come up with set of clocks that require pausing
        final LinkedHashSet<Clock> toPause = new LinkedHashSet<>();
        for (final Transition t : ta.getTransitions()) {
            if (!t.to.equals(ta.getInitialState())) {
                toPause.addAll(t.toReset); // note: add, not remove (opposite of collecting toResets)
            }
        }

        final String pauseActions = toPause.stream().map(c -> clock(c) + ".pause();\n").collect(joining());
        return "if (ta.shouldPauseClocks){\n" +
                pauseActions +
                "ta.shouldPauseClocks = false;\n" +
                "}";
    }

    //------------------------------------------------------------------------------------------ Transitions

    /**
     * @return start -> TO [ evt_channelStateSkip \... \... ]
     */
    private String getStateSkipTransition(final TimedAutomaton ta, final String from, final State to, final boolean isPositive) {

        final StringBuilder allClocks = new StringBuilder();
        ta.getClocks().forEach(c -> {
            if (c.hasTimeout()) allClocks.append(", ").append(clock(c));
        });

        final String condition = "ss.getDestination().equals(\"" + from + "_to_" + to + "\")";
        final String action = (allClocks.length() > 0 ? "setClocks(ss" + allClocks.toString() + ");" : "");
        final String toStr = !isPositive && ta.isAcceptingState(to) ? BAD : to.name;
        return getTransition(ta.getInitialState().name, toStr, evt("channelStateSkip"), condition, action);
    }

    //------------------------------------------------------------------------------------------ Other

    /**
     * @return True if the state is the initial state of the specified automaton.
     */
    private boolean isStartState(final State state, final TimedAutomaton ta) {
        return ta.getInitialState().equals(state);
    }
}
