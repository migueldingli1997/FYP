package larvaTools.TA;

import java.util.Arrays;

public class Outcome {

    public static boolean loggingEnabled = false;
    private final String state;
    private final Object vars[];

    public Outcome(final String state) {
        this(state, null);
    }

    public Outcome(final String state, final Object[] vars) {
        this.state = state;
        this.vars = vars;
        if (loggingEnabled) {
            System.out.println("New outcome: " + state + " with vars " + Arrays.toString(vars));
        }
    }

    public String getState() {
        return state;
    }

    public Object getVar(final int index) {
        return vars[index];
    }
}
