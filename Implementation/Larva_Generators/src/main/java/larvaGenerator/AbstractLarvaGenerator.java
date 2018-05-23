package larvaGenerator;

import RE.lib.basic.Symbol;
import RE.parser.ParsedRE;
import TA.lib.Clock;
import TA.lib.ClockCondition;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public abstract class AbstractLarvaGenerator {

    static final String DEFAULT_OUT_FOLDER = "./inputOutput/out/";
    static final String DEFAULT_SCRIPT_NAME_NUMBERED = "script_%d.lrv";

    static final String START = "start"; // the starting state
    static final String BAD = "bad"; // the violation state
    static final String GOOD = "good"; // the acceptance state
    static final String STOPPED = "stopped"; // the stopped state

    static final String LARVA_RESET = "LarvaReset"; // event name of Larva reset action
    static final String LARVA_STOP = "LarvaStop"; // event name of Larva stop action

    final String outFolder; // the output folder (refer to above default for an example)
    final boolean testing; // =true if "-test" argument added (adds reset transitions to the script)

    List<ParsedRE> parseResult; // the result of a parse (containing one or more regular expression)

    AbstractLarvaGenerator(final String input, final String outFolder, final BooleanArguments boolArgs) {

        this.outFolder = outFolder;
        this.testing = boolArgs.testing;

        // Check if input is empty
        if (input.replaceAll("\\s", "").length() == 0) {
            System.out.println("No list of regular expressions was found.");
            System.exit(1);
        }

        // Check if output folder exists
        if (!new File(outFolder).isDirectory()) {
            System.out.println("Output folder \"" + outFolder + "\" does not exist.");
            System.exit(1);
        }
    }

    void terminateGenerator(final int scriptsGenerated) {
        System.err.println("Generation of scripts had to terminate prematurely due to an error.");
        System.err.println("Larva scripts generated: " + scriptsGenerated);
        System.exit(1);
    }

    void successMessage(final int scriptsGenerated) {
        System.out.println("Successfully generated " + scriptsGenerated + " Larva script(s).");
    }

    //------------------------------------------------------------------------------------------ Names

    /**
     * @return Sym_SYMBOLNAME
     */
    static String sym(final String symbolName) {
        return "Sym_" + symbolName;
    }

    /**
     * @return clock_ID
     */
    public static String clock(final Clock clock) {
        return "clock_" + clock.id;
    }

    /**
     * @return clock_ID_dyn
     */
    static String clock_dyn(final Clock clock) {
        return "clock_" + clock.id + "_dyn";
    }

    /**
     * @return channel_CHANNELNAME
     */
    public static String channel(final String channelName) {
        return "channel_" + channelName;
    }

    /**
     * @return evt_EVENTNAME
     */
    public static String evt(final String eventName) {
        return "evt_" + eventName;
    }

    //------------------------------------------------------------------------------------------ Variables

    /**
     * @return Symbol sym_SYMBOLNAME = new Symbol("SYMBOLNAME")\n
     */
    static String getSymbolVariable(final Symbol symbol) {
        final String sym = symbol.getSymbol();
        if (symbol.getParams().size() == 0) {
            return "Symbol " + sym(paramsName(symbol)) + " = new Symbol(\"" + sym + "\");\n";
        } else {
            final String par = symbol.getParams().stream().map(p -> "\"" + p + "\"").collect(Collectors.joining(", "));
            return "Symbol " + sym(paramsName(symbol)) + " = new Symbol(\"" + sym + "\", Arrays.asList(" + par + "));\n";
        }
    }

    /**
     * @return Clock clock_CLOCK = new Clock();\n
     */
    static String getClockVariable(final Clock clock) {
        return "Clock " + clock(clock) + " = new Clock();\n";
    }

    /**
     * @return Channel channel_CHANNELNAME = new Channel();\n
     */
    static String getChannelVariable(final String channelName) {
        return "Channel " + channel(channelName) + " = new Channel();\n";
    }

    //------------------------------------------------------------------------------------------ Events

    /**
     * @return evt_EVENTNAME() = { FUNCTION }\n
     */
    static String getEvent(final String eventName, final String function) {
        return evt(eventName) + "() = { " + function + " }\n";
    }

    /**
     * @return evt_EVENTNAME() = { FUNCTION } where { WHEREASSIGNMENTS }\n
     */
    static String getEvent(final String eventName, final String function, final String whereAssignments) {
        return evt(eventName) + "() = { " + function + " } where { " + whereAssignments + " }\n";
    }

    /**
     * @return evt_SYMBOL() = { *.SYMBOL() }\n
     */
    static String getSymbolEvent(final Symbol symbol) {
        return getEvent(paramsName(symbol), String.format("*.%s()", symbol));
    }

    /**
     * @return evt_SYMBOL_a_b_..._z() = { Z z.SYMBOL() } where { _z = z; _y = >>><<<; _x = >>><<<; ...; _a = >>><<<; }\n
     */
    static String getSymbolEvent(final Symbol symbol, final String lastParam, final Map<String, String> paramClassMap) {
        final String justSymbol = symbol.getSymbol();
        final String lastObject = toJavaClassName(paramClassMap.get(lastParam)) + " " + lastParam;
        return getEvent(paramsName(symbol), String.format("%s.%s()", lastObject, justSymbol), getWhereAssignments(symbol, lastParam));
    }

    /**
     * @return evt_SYMBOL_a_b_..._z() = { *.SYMBOL(a,b,...,z) } where { _z = z; _y = >>><<<; _x = >>><<<; ...; _a = >>><<<; }\n
     */
    //static String getSymbolEvent(final Symbol symbol, final String lastParam, final Map<String, String> paramClassMap) {
    //    final String justSymbol = symbol.getSymbol();
    //    final String params = symbol.getParams().stream().map(p -> toJavaClassName(paramClassMap.get(p)) + " " + p).collect(joining(", "));
    //    return getEvent(paramsName(symbol), String.format("*.%s(%s)", justSymbol, params), getWhereAssignments(symbol, lastParam));
    //}

    /**
     * @return _z = z; _y = >>><<<; _x = >>><<<; ...; _a = >>><<<;
     */
    static String getWhereAssignments(final Symbol symbol, final String lastParam) {
        final StringBuilder where = new StringBuilder();
        final List<String> params = symbol.getParams();
        for (int i = params.size() - 1; i >= 0; i--) {
            where.append("_" + params.get(i) + " = ");
            where.append(i < params.size() - 1 ? ">>><<<;" : lastParam + ";");
            where.append(i > 0 ? " " : "");
        }
        return where.toString();
    }

    /**
     * @return evt_clock_ID() = { clock_ID@SECONDS }\n
     */
    static String getClockEvent(final Clock clock) {
        final long millis = clock.getTimeoutInMillis();
        final String seconds = millis % 1000 == 0 ?
                Long.toString(millis / 1000) : // round to a long, which removes fractional part
                Double.toString(millis / 1000.0).replaceAll("0*&", ""); // remove trailing zeros
        return getEvent(clock(clock), (clock(clock) + "@" + seconds));
    }

    /**
     * @return evt_clock_ID() = { clock_ID@@ }\n
     */
    static String getClockEvent_dynamic(final Clock clock) {
        return getEvent(clock_dyn(clock), (clock(clock) + "@@"));
    }

    //------------------------------------------------------------------------------------------ Transitions

    /**
     * @return FROM -> TO [ EVENT ]\n
     */
    static String getTransition(final String from, final String to, final String event) {
        return getTransition(from, to, event, "", "");
    }

    /**
     * @return FROM -> TO [ EVENT \CONDITIONS \RESETACTIONS ]\n
     */
    static String getTransition(final String from, final String to, final String event, final List<ClockCondition> guard, final LinkedHashSet<Clock> toReset) {

        // The transition guard, consisting of clock conditions
        final String clockConditions = guard.stream().map(cc -> cc.toJavaCondition(AbstractLarvaGenerator::clock)).collect(joining(" && "));

        // Pause clocks that have a timeout, excluding ones which will get reset
        final String pauseActions = guard.stream().map(ClockCondition::getClock).filter(
                c -> c.hasTimeout() && !toReset.contains(c)).map(c ->
                clock(c) + ".pause(); ").collect(joining());

        // Reset clocks which should be reset in this transition
        final String resetActions = toReset.stream().map(c ->
                clock(c) + ".reset(); ").collect(joining());

        return getTransition(from, to, event, clockConditions, pauseActions + resetActions);
    }

    /**
     * @return FROM -> TO [ EVENT \CONDITION \ACTION ]\n
     */
    static String getTransition(final String from, final String to, final String event, final String condition, final String action) {
        final boolean condIncl = condition.length() > 0;
        final boolean actIncl = action.length() > 0;
        final String slash1 = actIncl || condIncl ? "\\" : "";
        final String slash2 = actIncl ? "\\" : "";
        final String space1 = !condIncl || condition.charAt(condition.length() - 1) == ' ' ? "" : " ";
        final String space2 = !actIncl || action.charAt(action.length() - 1) == ' ' ? "" : " ";
        return String.format("%s -> %s [ %s %s%s%s%s%s%s]\n", from, to, event, slash1, condition, space1, slash2, action, space2);
    }

    //------------------------------------------------------------------------------------------ Other

    /**
     * @return SYMBOL_a_b_..._z
     */
    static String paramsName(final Symbol symbol) {
        final String params = symbol.getParams().stream().collect(joining("_"));
        return params.length() == 0 ? symbol.getSymbol() : (symbol.getSymbol() + "_" + params);
    }

    /**
     * @return The argument if testing is enabled; empty string otherwise.
     */
    String ifTest(final String ifTesting) {
        return ifTest(ifTesting, "");
    }

    /**
     * @return First argument if testing is enabled; Second argument otherwise.
     */
    String ifTest(final String ifTesting, String otherwise) {
        return testing ? ifTesting : otherwise;
    }

    /**
     * @return The tabified version of the argument.
     */
    static String tabify(final String toTabify) {

        int tabCount = 0;
        final StringBuilder tabbed = new StringBuilder(toTabify);
        for (int i = 0; i < tabbed.length(); i++) {
            switch (tabbed.charAt(i)) {
                case '{':
                    tabCount++;
                    break;
                case '}':
                    tabCount--;
                    break;
                case '\n':
                    if (i + 1 < tabbed.length()) {
                        final char nextChar = tabbed.charAt(i + 1);
                        if (nextChar == '}') {
                            tabbed.insert(i + 1, tabs(tabCount - 1));
                        } else if (nextChar != '\n') {
                            tabbed.insert(i + 1, tabs(tabCount));
                        }
                    }
                    break;
            }
        }
        return tabbed.toString();
    }

    /**
     * @return The specified number of tabs.
     */
    static String tabs(final int count) {

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("    ");
        }
        return sb.toString();
    }

    /**
     * @return C, if argument is a1.a2...a3.C;
     */
    static String toJavaClassName(final String javaClassPath) {
        return javaClassPath.replaceAll("([a-zA-Z_$][a-zA-Z\\d_$]*\\.)*", "");
    }
}
