package RE.lib.basic;

import RE.lib.RegExp;
import RE.lib.operators.TimeInterval;
import TA.lib.TimedAutomaton;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class EmptySetTest {

    private static final RegExp EMPTY_SET = EmptySet.INSTANCE;
    private static final Symbol DUMMY_SYMBOL = new Symbol("DUMMY_SYMBOL");
    private static final Duration DUMMY_DURATION = Duration.ofSeconds(5);

    @Test
    public void getDerivative_wrtAnySymbolAndAnyTimeChangesNothing() {
        final RegExp residual = EMPTY_SET.getDerivative(DUMMY_SYMBOL, DUMMY_DURATION);
        Assert.assertEquals(EmptySet.INSTANCE, residual);
    }

    @Test
    public void getDerivative_wrtAnyTimeChangesNothing() {
        final RegExp residual = EMPTY_SET.getDerivative(DUMMY_DURATION);
        Assert.assertEquals(EmptySet.INSTANCE, residual);
    }

    @Test
    public void hasEmptyString_returnsFalse() {
        Assert.assertFalse(EMPTY_SET.hasEmptyString());
    }

    @Test
    public void isEmpty_returnsTrue() {
        Assert.assertTrue(EMPTY_SET.isEmpty());
    }

    @Test
    public void removeEmptyString_changesNothing() {
        Assert.assertEquals(EmptySet.INSTANCE, EmptySet.INSTANCE.removeEmptyString());
    }

    @Test
    public void minTimeoutValue_returnsInfinity() {
        Assert.assertEquals(TimeInterval.INFINITY, EMPTY_SET.minTimeoutValue());
    }

    @Test
    public void getFrontSymbols_returnsAnEmptySet() {
        Assert.assertTrue(EMPTY_SET.getFrontSymbols(null).isEmpty());
    }

    @Test
    public void toTimedAutomaton_returnsTheExpectedAutomaton() {

        final TimedAutomaton ta = EmptySet.INSTANCE.toTimedAutomaton(null);

        Assert.assertEquals(1, ta.getStates().size());
        Assert.assertEquals(0, ta.getClocks().size());
        Assert.assertEquals(0, ta.getTransitions().size());
        Assert.assertEquals(0, ta.getAlphabet().size());
        Assert.assertEquals(0, ta.getAcceptingStates().size());
    }
}