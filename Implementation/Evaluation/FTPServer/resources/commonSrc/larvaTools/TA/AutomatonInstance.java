package larvaTools.TA;

public class AutomatonInstance {

    public static boolean loggingEnabled = false;
    public boolean shouldPauseClocks;
    public boolean shouldPerformStartActions;

    public AutomatonInstance(final boolean shouldPerformStartActions) {
        this(shouldPerformStartActions, false);
    }

    public AutomatonInstance(final boolean shouldPerformStartActions, final boolean shouldPauseClocks) {
        this.shouldPauseClocks = shouldPauseClocks;
        this.shouldPerformStartActions = shouldPerformStartActions;
        if (loggingEnabled) {
            System.out.println("Created automaton instance " + this + " with " + shouldPerformStartActions + " and " + shouldPauseClocks);
        }
    }
}
