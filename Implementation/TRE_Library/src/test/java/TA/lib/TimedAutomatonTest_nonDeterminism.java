package TA.lib;

import RE.lib.basic.Symbol;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.*;

public class TimedAutomatonTest_nonDeterminism {

    private TimedAutomaton ta;

    private final static Symbol sym1 = new Symbol("sym1");
    private final static Symbol sym2 = new Symbol("sym2");
    private final static Symbol sym3 = new Symbol("sym3");
    private final static Symbol sym4 = new Symbol("sym4");

    private State states[];
    private LinkedHashSet<Clock> toReset;
    private List<ClockCondition> guard;

    @Before
    public void setUp() {
        ta = new TimedAutomaton();
        states = new State[]{
                ta.addState(false),
                ta.addState(false),
                ta.addState(false),
                ta.addState(true)
        };
        final Clock clock = ta.addClock();
        toReset = new LinkedHashSet<>(Collections.singletonList(clock));
        guard = new ArrayList<>(Collections.singletonList(new ClockCondition(clock, Duration.ofSeconds(5), Duration.ofSeconds(10))));
    }

    @After
    public void tearDown() {
        ta = null;
        states = null;
        toReset = null;
        guard = null;
    }

    @Test
    public void eliminateNonDetAndSilentTrans_changesNothingIfDeterministicAutomatonWithNoEmptyStringTransitions() {
        ta.addTransition(new Transition(states[0], states[1], sym1));
        ta.addTransition(new Transition(states[1], states[2], sym2));
        ta.addTransition(new Transition(states[2], states[3], sym3, toReset));
        ta.addTransition(new Transition(states[3], states[0], sym4, guard));

        // Before
        final LinkedHashSet<State> statesBefore = new LinkedHashSet<>(ta.states);
        final LinkedHashSet<Clock> clocksBefore = new LinkedHashSet<>(ta.clocks);
        final LinkedHashSet<Transition> transBefore = new LinkedHashSet<>(ta.transitions);
        final LinkedHashSet<Symbol> alphabetBefore = new LinkedHashSet<>(ta.alphabet);
        final State initStateBefore = ta.initialState;
        final LinkedHashSet<State> acceptingBefore = new LinkedHashSet<>(ta.acceptingStates);

        // Eliminate non-determinism
        ta.eliminateNonDetAndSilentTrans();

        // Check TimedAutomaton members
        Assert.assertEquals(statesBefore, ta.states);
        Assert.assertEquals(clocksBefore, ta.clocks);
        Assert.assertEquals(transBefore, ta.transitions);
        Assert.assertEquals(alphabetBefore, ta.alphabet);
        Assert.assertEquals(initStateBefore, ta.initialState);
        Assert.assertEquals(acceptingBefore, ta.acceptingStates);

        // Check state-skips of original states
        checkThatNoStateSkipsInOriginalStates();
    }

    @Test
    public void eliminateNonDetAndSilentTrans_swapsEmptyStringTransitionsWithStateSkips() {
        final Transition t1 = ta.addTransition(new Transition(states[0], states[1], sym1));
        final Transition t2 = ta.addTransition(new Transition(states[1], states[2], EmptySymbol.INSTANCE));
        final Transition t3 = ta.addTransition(new Transition(states[2], states[3], sym3, toReset));
        final Transition t4 = ta.addTransition(new Transition(states[3], states[0], EmptySymbol.INSTANCE, guard));

        // Before
        final LinkedHashSet<State> statesBefore = new LinkedHashSet<>(ta.states);
        final LinkedHashSet<Clock> clocksBefore = new LinkedHashSet<>(ta.clocks);
        final State initStateBefore = ta.initialState;
        final LinkedHashSet<State> acceptingBefore = new LinkedHashSet<>(ta.acceptingStates);

        // Eliminate non-determinism
        ta.eliminateNonDetAndSilentTrans();

        // Check TimedAutomaton members
        Assert.assertEquals(statesBefore, ta.states);
        Assert.assertEquals(clocksBefore, ta.clocks);
        Assert.assertEquals(new LinkedHashSet<>(Arrays.asList(t1, t3)), ta.transitions);
        Assert.assertEquals(new LinkedHashSet<>(Arrays.asList(sym1, sym3)), ta.alphabet);
        Assert.assertEquals(initStateBefore, ta.initialState);
        Assert.assertEquals(acceptingBefore, ta.acceptingStates);

        // Check state-skips of original states
        Assert.assertEquals(0, states[0].stateSkips.size());
        Assert.assertEquals(0, states[2].stateSkips.size());
        Assert.assertEquals(new LinkedHashSet<>(Collections.singletonList(new StateSkip(states[2]))), states[1].stateSkips);
        Assert.assertEquals(new LinkedHashSet<>(Collections.singletonList(new StateSkip(states[0], guard))), states[3].stateSkips);
    }

    @Test
    public void eliminateNonDetAndSilentTrans_createsNewTransitionsStatesAndStateSkipsForNonDeterminism() {
        ta.addTransition(new Transition(states[0], states[1], sym1));
        ta.addTransition(new Transition(states[0], states[2], sym1));
        ta.addTransition(new Transition(states[2], states[3], sym2, toReset));
        ta.addTransition(new Transition(states[2], states[0], sym2, guard));

        // Before
        final LinkedHashSet<State> statesBefore = new LinkedHashSet<>(ta.states);
        final LinkedHashSet<Clock> clocksBefore = new LinkedHashSet<>(ta.clocks);
        final LinkedHashSet<Transition> transBefore = new LinkedHashSet<>(ta.transitions);
        final LinkedHashSet<Symbol> alphabetBefore = new LinkedHashSet<>(ta.alphabet);
        final State initStateBefore = ta.initialState;
        final LinkedHashSet<State> acceptingBefore = new LinkedHashSet<>(ta.acceptingStates);

        // Eliminate non-determinism
        ta.eliminateNonDetAndSilentTrans();

        // Check TimedAutomaton members
        Assert.assertEquals(clocksBefore, ta.clocks);
        Assert.assertEquals(alphabetBefore, ta.alphabet);
        Assert.assertEquals(initStateBefore, ta.initialState);
        Assert.assertEquals(acceptingBefore, ta.acceptingStates);
        Assert.assertTrue(ta.states.containsAll(statesBefore));
        for (final Transition t : transBefore) {
            Assert.assertFalse(ta.transitions.contains(t));
        }

        // Check new states and new transitions
        Assert.assertEquals(2, ta.states.size() - statesBefore.size());
        Assert.assertEquals(2, transBefore.size() - ta.transitions.size());
        for (final State s : ta.states) {
            if (!statesBefore.contains(s)) {
                Assert.assertEquals(2, s.stateSkips.size());
            }
        }

        // Check state-skips of newStates
        boolean done1 = false;
        boolean done2 = false;
        for (final Transition t : ta.transitions) {
            if (!transBefore.contains(t)) {
                if (t.symbol.equals(sym1)) {
                    Assert.assertEquals(new LinkedHashSet<>(Arrays.asList(
                            new StateSkip(states[1]),
                            new StateSkip(states[2])
                    )), t.to.stateSkips);
                    done1 = true;
                } else if (t.symbol.equals(sym2)) {
                    Assert.assertEquals(new LinkedHashSet<>(Arrays.asList(
                            new StateSkip(states[3], toReset),
                            new StateSkip(states[0], guard)
                    )), t.to.stateSkips);
                    done2 = true;
                }
            }
        }
        Assert.assertTrue(done1 && done2);

        // Check state-skips of original states
        checkThatNoStateSkipsInOriginalStates();
    }

    private void checkThatNoStateSkipsInOriginalStates() {
        for (final State s : states) {
            Assert.assertEquals(0, s.stateSkips.size());
        }
    }
}