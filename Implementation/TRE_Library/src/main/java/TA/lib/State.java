package TA.lib;

import java.util.LinkedHashSet;

public class State {

    public String name = "";

    // Transitions which this state must manually trigger
    // due to non-determinism or empty string transitions
    final LinkedHashSet<StateSkip> stateSkips = new LinkedHashSet<>();

    State() {
    }

    public void addStateSkip(final StateSkip trans) {
        stateSkips.add(trans);
    }

    public LinkedHashSet<StateSkip> getStateSkips() {
        return stateSkips;
    }

    @Override
    public String toString() {
        if (name.equals("")) {
            return super.toString();
        } else {
            return name;
        }
    }
}
