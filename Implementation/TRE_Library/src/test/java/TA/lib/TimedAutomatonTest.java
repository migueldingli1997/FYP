package TA.lib;

import RE.lib.basic.Symbol;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class TimedAutomatonTest {

    private final State INIT_STATE = new State();
    private TimedAutomaton ta;

    private final static Symbol sym1 = new Symbol("sym1");
    private final static Symbol sym2 = new Symbol("sym2");
    private final static Symbol sym3 = new Symbol("sym3");
    private final static Symbol sym4 = new Symbol("sym4");
    private final static Symbol sym5 = new Symbol("sym5");

    @Before
    public void setUp() {
        ta = new TimedAutomaton();
    }

    @After
    public void tearDown() {
        ta = null;
    }

    @Test
    public void defaultMemberValues() {
        Assert.assertEquals(1, ta.states.size());
        Assert.assertEquals(0, ta.clocks.size());
        Assert.assertEquals(0, ta.transitions.size());
        Assert.assertEquals(0, ta.alphabet.size());
        Assert.assertEquals(0, ta.acceptingStates.size());

        final TimedAutomaton ta_customInit = new TimedAutomaton(INIT_STATE);
        Assert.assertEquals(INIT_STATE, ta_customInit.initialState);
    }

    @Test
    public void addState_addsNewState_doesNotAddToAcceptingIfFalseArg() {
        final State state = ta.addState(false);
        Assert.assertTrue(ta.states.contains(state));
        Assert.assertFalse(ta.acceptingStates.contains(state));
    }

    @Test
    public void addState_addsNewState_addsToAcceptingIfTrueArg() {
        final State state = ta.addState(true);
        Assert.assertTrue(ta.states.contains(state));
        Assert.assertTrue(ta.acceptingStates.contains(state));
    }

    @Test
    public void addClock_addsNewClock() {
        final Clock clock = ta.addClock();
        Assert.assertTrue(ta.clocks.contains(clock));
    }

    @Test
    public void addTransition_addsTransitionToTransitionsAndSymbolToAlphabet() {
        final State states[] = {new State(), new State()};
        final Transition trans = new Transition(states[0], states[1], new Symbol("symbol"));
        Assert.assertEquals(trans, ta.addTransition(trans));
        Assert.assertFalse(ta.states.contains(states[0])); // does not add the states
        Assert.assertFalse(ta.states.contains(states[1])); // does not add the states
        Assert.assertTrue(ta.transitions.contains(trans));
        Assert.assertTrue(ta.alphabet.contains(new Symbol("symbol")));
    }

    @Test
    public void isAcceptingState_returnsTrueIfStateIsAccepting() {
        final State state = ta.addState(true);
        Assert.assertTrue(ta.isAcceptingState(state));
    }

    @Test
    public void isAcceptingState_returnsFalseIfStateIsNotAccepting() {
        final State state = ta.addState(false);
        Assert.assertFalse(ta.isAcceptingState(state));
    }

    @Test
    public void getTransitionsLeadingToAcceptingStates_returnsAsExpected() {
        final List<Transition> trans = Arrays.asList(
                new Transition(ta.addState(false), ta.addState(false), sym1),
                new Transition(ta.addState(false), ta.addState(true), sym2),
                new Transition(ta.addState(true), ta.addState(false), sym3),
                new Transition(ta.addState(false), ta.addState(true), sym4)
        );
        ta.transitions.addAll(trans);

        final LinkedHashSet<Transition> returned = ta.getTransitionsLeadingToAcceptingStates();
        Assert.assertFalse(returned.contains(trans.get(0)));
        Assert.assertTrue(returned.contains(trans.get(1)));
        Assert.assertFalse(returned.contains(trans.get(2)));
        Assert.assertTrue(returned.contains(trans.get(3)));
    }

    @Test
    public void joinWith_joinsStatesClocksTransAlphabet_andAcceptingStatesIfTrueArgument() {
        final TimedAutomaton toJoinWith = new TimedAutomaton();
        final State states[] = {
                toJoinWith.addState(false),
                toJoinWith.addState(false),
                toJoinWith.addState(false),
                toJoinWith.addState(true)
        };
        toJoinWith.addClock();
        toJoinWith.addTransition(new Transition(states[0], states[1], sym1));
        toJoinWith.addTransition(new Transition(states[1], states[2], sym2));
        toJoinWith.addTransition(new Transition(states[2], states[3], sym3));
        toJoinWith.addTransition(new Transition(states[3], states[0], sym4));

        ta.joinWith(toJoinWith, true);
        Assert.assertTrue(ta.states.containsAll(toJoinWith.states));
        Assert.assertTrue(ta.clocks.containsAll(toJoinWith.clocks));
        Assert.assertTrue(ta.transitions.containsAll(toJoinWith.transitions));
        Assert.assertTrue(ta.alphabet.containsAll(toJoinWith.alphabet));
        Assert.assertTrue(ta.acceptingStates.containsAll(toJoinWith.acceptingStates));
    }

    @Test
    public void joinWith_joinsStatesClocksTransAlphabet_andRemovesAnythingAcceptingStateRelatedIfFalseArgument() {
        final TimedAutomaton toJoinWith = new TimedAutomaton();
        final State states[] = {
                toJoinWith.addState(false),
                toJoinWith.addState(false),
                toJoinWith.addState(false),
                toJoinWith.addState(true)
        };
        toJoinWith.addClock();
        final Transition trans1 = toJoinWith.addTransition(new Transition(states[0], states[1], sym1));
        final Transition trans2 = toJoinWith.addTransition(new Transition(states[1], states[2], sym2));
        final Transition trans3 = toJoinWith.addTransition(new Transition(states[2], states[3], sym3));
        final Transition trans4 = toJoinWith.addTransition(new Transition(states[3], states[0], sym4));

        ta.joinWith(toJoinWith, false);
        Assert.assertTrue(ta.states.containsAll(Arrays.asList(states[0], states[1], states[2])));
        Assert.assertTrue(ta.clocks.containsAll(toJoinWith.clocks));
        Assert.assertTrue(ta.transitions.containsAll(Arrays.asList(trans1, trans2)));
        Assert.assertTrue(ta.alphabet.containsAll(toJoinWith.alphabet));

        Assert.assertFalse(ta.states.contains(states[3]));
        Assert.assertFalse(ta.transitions.contains(trans3));
        Assert.assertFalse(ta.transitions.contains(trans4));
        Assert.assertFalse(ta.acceptingStates.contains(states[3]));
    }

    @Test
    public void setStateAndClockNames_setsNamesOfStatesAndClocks() {
        final State states[] = {ta.addState(false), ta.addState(false), ta.addState(false)};
        final Clock clocks[] = {ta.addClock(), ta.addClock(), ta.addClock()};

        final int amount = ta.setStateNamesAndClockIDs();
        for (int i = 0; i < states.length; i++) {
            Assert.assertEquals(i, clocks[i].id);
            Assert.assertEquals("S" + i, states[i].name);
        }
        Assert.assertEquals("start", ta.getInitialState().name);
        Assert.assertEquals(states.length, amount); // note: excludes initial state
    }

    @Test
    public void toString_forCoverage() {
        ta.getTransitions().add(new Transition(ta.addState(true), ta.addState(false), new Symbol("symbol")));
        ta.getInitialState().addStateSkip(new StateSkip(ta.addState(true)));
        System.out.println(ta.toString());
    }
}