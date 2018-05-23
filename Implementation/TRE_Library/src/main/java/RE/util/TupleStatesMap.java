package RE.util;

import TA.lib.State;
import TA.lib.TimedAutomaton;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class TupleStatesMap {

    private final TimedAutomaton ta;
    private final Map<Pair<State, State>, State> tupleStates = new HashMap<>();

    public TupleStatesMap(final TimedAutomaton ta) {
        this.ta = ta;
    }

    public void setState(final State s1, final State s2, final State toSetAs) {
        final Pair<State, State> pair = new Pair<>(s1, s2);
        tupleStates.put(pair, toSetAs);
    }

    public State getState(final State s1, final State s2) {
        final Pair<State, State> tuple = new Pair<>(s1, s2);
        final State toReturn;
        if (tupleStates.containsKey(tuple)) {
            toReturn = tupleStates.get(tuple);
        } else {
            tupleStates.put(tuple, (toReturn = ta.addState(false)));
        }
        return toReturn;
    }

    /**
     * (Only for testing purposes!)
     */
    public Map<Pair<State, State>, State> getTupleStates() {
        return tupleStates;
    }
}

