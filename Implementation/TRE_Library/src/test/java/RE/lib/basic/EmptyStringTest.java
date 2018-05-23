package RE.lib.basic;

import RE.lib.RegExp;
import RE.lib.operators.TimeInterval;
import TA.lib.EmptySymbol;
import TA.lib.State;
import TA.lib.TimedAutomaton;
import TA.lib.Transition;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class EmptyStringTest {

    private static final RegExp EMPTY_STRING = EmptyString.INSTANCE;
    private static final Symbol DUMMY_SYMBOL = new Symbol("DUMMY_SYMBOL");
    private static final Duration DUMMY_DURATION = Duration.ofSeconds(5);

    @Test
    public void getDerivative_wrtAnySymbolAndAnyTimeReturnsEmptySet() {
        final RegExp residual = EMPTY_STRING.getDerivative(DUMMY_SYMBOL, DUMMY_DURATION);
        Assert.assertEquals(EmptySet.INSTANCE, residual);
    }

    @Test
    public void getDerivative_wrtAnyTimeChangesNothing() {
        final RegExp residual = EMPTY_STRING.getDerivative(DUMMY_DURATION);
        Assert.assertEquals(EmptyString.INSTANCE, residual);
    }

    @Test
    public void hasEmptyString_returnsTrue() {
        Assert.assertTrue(EMPTY_STRING.hasEmptyString());
    }

    @Test
    public void isEmpty_returnsFalse() {
        Assert.assertFalse(EMPTY_STRING.isEmpty());
    }

    @Test
    public void removeEmptyString_returnsEmptySet() {
        Assert.assertEquals(EmptySet.INSTANCE, EmptyString.INSTANCE.removeEmptyString());
    }

    @Test
    public void minTimeoutValue_returnsInfinity() {
        Assert.assertEquals(TimeInterval.INFINITY, EMPTY_STRING.minTimeoutValue());
    }

    @Test
    public void getFrontSymbols_returnsAnEmptySet() {
        Assert.assertTrue(EMPTY_STRING.getFrontSymbols(null).isEmpty());
    }

    @Test
    public void toTimedAutomaton_returnsTheExpectedAutomaton() {

        final TimedAutomaton ta = EmptyString.INSTANCE.toTimedAutomaton(null);

        Assert.assertEquals(2, ta.getStates().size());
        Assert.assertEquals(0, ta.getClocks().size());
        Assert.assertEquals(1, ta.getTransitions().size());
        Assert.assertEquals(1, ta.getAlphabet().size());
        Assert.assertEquals(1, ta.getAcceptingStates().size());

        // Obtaining information
        final Iterator<State> stateIterator = ta.getStates().iterator();
        final Iterator<Transition> transIterator = ta.getTransitions().iterator();
        final State s1 = stateIterator.next();
        final State s2 = stateIterator.next();
        final Transition t = transIterator.next();

        // Obtain initial state and other state
        final State init = ta.getInitialState();
        Assert.assertTrue(init.equals(s1) || init.equals(s2)); // s1 or s2 is initial
        final State other = init.equals(s1) ? s2 : s1;
        Assert.assertTrue(ta.isAcceptingState(other)); // other is accepting

        // Check transition
        Assert.assertEquals(init, t.from);
        Assert.assertEquals(other, t.to);
        Assert.assertEquals(t.symbol, EmptySymbol.INSTANCE);
        Assert.assertEquals(0, t.guard.size());
        Assert.assertEquals(0, t.toReset.size());
    }
}