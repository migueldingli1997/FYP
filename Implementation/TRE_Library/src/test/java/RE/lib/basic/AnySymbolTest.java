package RE.lib.basic;

import RE.lib.RegExp;
import RE.lib.operators.TimeInterval;
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

public class AnySymbolTest {

    private static final RegExp ANY_SYMBOL = AnySymbol.INSTANCE;
    private static final Symbol DUMMY_SYMBOL = new Symbol("DUMMY_SYMBOL");
    private static final Duration DUMMY_DURATION = Duration.ofSeconds(5);

    @Test
    public void getDerivative_wrtAnySymbolAndAnyTimeReturnsEmptyString() {
        final RegExp residual = ANY_SYMBOL.getDerivative(DUMMY_SYMBOL, DUMMY_DURATION);
        Assert.assertEquals(EmptyString.INSTANCE, residual);
    }

    @Test
    public void getDerivative_wrtAnyTimeChangesNothing() {
        final RegExp residual = ANY_SYMBOL.getDerivative(DUMMY_DURATION);
        Assert.assertEquals(AnySymbol.INSTANCE, residual);
    }

    @Test
    public void hasEmptyString_returnsFalse() {
        Assert.assertFalse(ANY_SYMBOL.hasEmptyString());
    }

    @Test
    public void isEmpty_returnsFalse() {
        Assert.assertFalse(ANY_SYMBOL.isEmpty());
    }

    @Test
    public void removeEmptyString_changesNothing() {
        Assert.assertEquals(AnySymbol.INSTANCE, AnySymbol.INSTANCE.removeEmptyString());
    }

    @Test
    public void minTimeoutValue_returnsInfinity() {
        Assert.assertEquals(TimeInterval.INFINITY, ANY_SYMBOL.minTimeoutValue());
    }

    @Test
    public void getFrontSymbols_returnsTheAlphabet() {

        final Symbol symbols[] = {new Symbol("sym1"), new Symbol("sym2"), new Symbol("sym3")};
        final LinkedHashSet<Symbol> dummyAlphabet = new LinkedHashSet<>(Arrays.asList(symbols[0], symbols[1], symbols[2]));

        final Set<Symbol> frontSymbols = ANY_SYMBOL.getFrontSymbols(dummyAlphabet);
        Assert.assertEquals(frontSymbols, new LinkedHashSet<>(dummyAlphabet));
    }

    @Test
    public void toTimedAutomaton_returnsTheExpectedAutomaton() {

        final Symbol A = new Symbol("A");
        final Symbol B = new Symbol("B");
        final Symbol C = new Symbol("C");

        final LinkedHashSet<Symbol> alphabet = new LinkedHashSet<>(Arrays.asList(A, B, C));
        final TimedAutomaton ta = AnySymbol.INSTANCE.toTimedAutomaton(alphabet);

        Assert.assertEquals(2, ta.getStates().size());
        Assert.assertEquals(0, ta.getClocks().size());
        Assert.assertEquals(3, ta.getTransitions().size());
        Assert.assertEquals(alphabet, ta.getAlphabet());
        Assert.assertEquals(1, ta.getAcceptingStates().size());

        // Obtaining information
        final Iterator<State> stateIterator = ta.getStates().iterator();
        final State s1 = stateIterator.next();
        final State s2 = stateIterator.next();

        // Obtain initial state and other state
        final State init = ta.getInitialState();
        Assert.assertTrue(init.equals(s1) || init.equals(s2)); // s1 or s2 is initial
        final State other = init.equals(s1) ? s2 : s1;
        Assert.assertTrue(ta.isAcceptingState(other)); // other is accepting

        // Check transitions
        for (final Transition t : ta.getTransitions()) {
            Assert.assertEquals(init, t.from);
            Assert.assertEquals(other, t.to);
            Assert.assertEquals(0, t.guard.size());
            Assert.assertEquals(0, t.toReset.size());
        }
        final LinkedHashSet<Transition> trans = new LinkedHashSet<>(ta.getTransitions());
        Assert.assertTrue(trans.removeIf(t -> t.symbol.equals(A)));
        Assert.assertTrue(trans.removeIf(t -> t.symbol.equals(B)));
        Assert.assertTrue(trans.removeIf(t -> t.symbol.equals(C)));
    }
}