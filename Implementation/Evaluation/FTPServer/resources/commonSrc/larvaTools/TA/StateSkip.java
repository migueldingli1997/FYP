package larvaTools.TA;

import larva.Clock;

import java.util.Arrays;

public class StateSkip {

    public static boolean loggingEnabled = false;
    private final AutomatonInstance instance;
    private final String destination;
    private final Clock clocks[];
    private final Object vars[];

    public StateSkip(final AutomatonInstance instance, final String destination) {
        this(instance, destination, null, null);
    }

    public StateSkip(final AutomatonInstance instance, final String destination, final Clock clocks[]) {
        this(instance, destination, clocks, null);
    }

    public StateSkip(final AutomatonInstance instance, final String destination, final Object vars[]) {
        this(instance, destination, null, vars);
    }

    public StateSkip(final AutomatonInstance instance, final String destination, final Clock clocks[], final Object[] vars) {
        this.instance = instance;
        this.destination = destination;
        this.clocks = clocks;
        this.vars = vars;
        if (loggingEnabled) {
            System.out.println("State skip: " + instance +
                    " to " + destination +
                    " with clocks " + Arrays.toString(clocks) +
                    " and vars " + Arrays.toString(vars));
        }
    }

    public AutomatonInstance getInstance() {
        return instance;
    }

    public String getDestination() {
        return destination;
    }

    public Clock[] getClocks() {
        return clocks;
    }

    public Object getVar(final int index) {
        return vars[index];
    }
}
