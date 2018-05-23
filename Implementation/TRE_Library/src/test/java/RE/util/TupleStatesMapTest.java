package RE.util;

import TA.lib.State;
import TA.lib.StateTest;
import TA.lib.TimedAutomaton;
import javafx.util.Pair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;

public class TupleStatesMapTest {

    private TimedAutomaton newTimedAutomaton;
    private TupleStatesMap tupleStatesMap;

    @Before
    public void setUp() {
        newTimedAutomaton = new TimedAutomaton();
        tupleStatesMap = new TupleStatesMap(newTimedAutomaton);
    }

    @After
    public void tearDown() {
        newTimedAutomaton = null;
        tupleStatesMap = null;
    }

    @Test
    public void setState_addsPairToTupleMap() {
        final State newState1 = StateTest.newState();
        final State newState2 = StateTest.newState();
        final State toSetAs = StateTest.newState();
        final Pair<State, State> newStatePair = new Pair<>(newState1, newState2);

        tupleStatesMap.setState(newState1, newState2, toSetAs);

        Assert.assertTrue(tupleStatesMap.getTupleStates().containsKey(newStatePair));
        Assert.assertEquals(toSetAs, tupleStatesMap.getTupleStates().get(newStatePair));
    }

    @Test
    public void getState_returnsExistingValueIfItWasSetAndDoesNotModifyAutomatonStates() {
        final State newState1 = StateTest.newState();
        final State newState2 = StateTest.newState();
        final State toSetAs = StateTest.newState();
        final Pair<State, State> newStatePair = new Pair<>(newState1, newState2);

        final LinkedHashSet<State> statesBefore = new LinkedHashSet<>(newTimedAutomaton.getStates());
        tupleStatesMap.getTupleStates().put(newStatePair, toSetAs);
        final LinkedHashSet<State> statesAfter = new LinkedHashSet<>(newTimedAutomaton.getStates());

        Assert.assertEquals(toSetAs, tupleStatesMap.getState(newState1, newState2));
        Assert.assertArrayEquals(statesBefore.toArray(), statesAfter.toArray());
    }

    @Test
    public void getState_returnsNewUniqueValueIfItWasNotSetAndAddsNewStateToAutomaton() {
        final State newState1 = StateTest.newState();
        final State newState2 = StateTest.newState();
        final Pair<State, State> newStatePair = new Pair<>(newState1, newState2);

        final LinkedHashSet<State> statesBefore = new LinkedHashSet<>(newTimedAutomaton.getStates());
        final State newState = tupleStatesMap.getState(newState1, newState2);
        final LinkedHashSet<State> statesAfter = new LinkedHashSet<>(newTimedAutomaton.getStates());

        Assert.assertNotEquals(newState, newState1);
        Assert.assertNotEquals(newState, newState2);
        Assert.assertEquals(newState, tupleStatesMap.getTupleStates().get(newStatePair));

        // Sets are equal if we remove new state from statesAfter
        Assert.assertEquals(statesBefore.size() + 1, statesAfter.size());
        statesAfter.remove(newState);
        Assert.assertArrayEquals(statesBefore.toArray(), statesAfter.toArray());
    }
}