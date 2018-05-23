package RE.lib.basic;

import RE.lib.RegExp;
import RE.lib.operators.TimeInterval;
import TA.lib.State;
import TA.lib.TimedAutomaton;
import TA.lib.Transition;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class NegatedSymbolTest {

    private static final Symbol theSymbol = new Symbol("testSymbol");
    private static final RegExp negatedSym = new NegatedSymbol(theSymbol);

    private static final Duration DUMMY_DURATION = Duration.ofSeconds(5);
    private static final Symbol IDENTICAL_SYMBOL = new Symbol("testSymbol");
    private static final Symbol DIFFERENT_SYMBOL = new Symbol("diffSymbol");

    @Test
    public void getDerivative_wrtDifferentSymbolAndAnyTimeReturnsEmptyString() {
        final RegExp residual = negatedSym.getDerivative(DIFFERENT_SYMBOL, DUMMY_DURATION);
        Assert.assertEquals(EmptyString.INSTANCE, residual);
    }

    @Test
    public void getDerivative_wrtIdenticalSymbolAndAnyTimeReturnsEmptySet() {
        final RegExp residual = negatedSym.getDerivative(IDENTICAL_SYMBOL, DUMMY_DURATION);
        Assert.assertEquals(EmptySet.INSTANCE, residual);
    }

    @Test
    public void getDerivative_wrtAnyTimeChangesNothing() {
        final RegExp residual = negatedSym.getDerivative(DUMMY_DURATION);
        Assert.assertEquals(negatedSym, residual);
    }

    @Test
    public void hasEmptyString_returnsFalse() {
        Assert.assertFalse(negatedSym.hasEmptyString());
    }

    @Test
    public void isEmpty_returnsFalse() {
        Assert.assertFalse(negatedSym.isEmpty());
    }

    @Test
    public void removeEmptyString_changesNothing() {
        Assert.assertEquals(negatedSym, negatedSym.removeEmptyString());
    }

    @Test
    public void minTimeoutValue_returnsInfinity() {
        Assert.assertEquals(TimeInterval.INFINITY, negatedSym.minTimeoutValue());
    }

    @Test
    public void getFrontSymbols_returnsTheAlphabet() {

        final Symbol symbols[] = {new Symbol("sym1"), new Symbol("testSymbol"), new Symbol("sym3")};
        final LinkedHashSet<Symbol> dummyAlphabet = new LinkedHashSet<>(Arrays.asList(symbols[0], symbols[1], symbols[2]));

        final Set<Symbol> frontSymbols = negatedSym.getFrontSymbols(dummyAlphabet);
        Assert.assertEquals(2, frontSymbols.size());
        Assert.assertTrue(frontSymbols.contains(symbols[0]));
        Assert.assertFalse(frontSymbols.contains(symbols[1]));
        Assert.assertTrue(frontSymbols.contains(symbols[2]));
    }
    @Test
    public void toTimedAutomaton_returnsTheExpectedAutomaton() {

        final Symbol A = new Symbol("A");
        final Symbol B = new Symbol("B");
        final Symbol C = new Symbol("C");

        final LinkedHashSet<Symbol> alphabet = new LinkedHashSet<>(Arrays.asList(A, B, C, theSymbol));
        final LinkedHashSet<Symbol> anythingBut = new LinkedHashSet<>(Arrays.asList(A, B, C));
        final TimedAutomaton ta = negatedSym.toTimedAutomaton(alphabet);

        Assert.assertEquals(2, ta.getStates().size());
        Assert.assertEquals(0, ta.getClocks().size());
        Assert.assertEquals(3, ta.getTransitions().size());
        Assert.assertEquals(anythingBut, ta.getAlphabet()); // important
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

    @Test
    public void getSymbol_returnsTheOperand() {
        Assert.assertEquals(theSymbol, ((NegatedSymbol) negatedSym).getSymbol());
    }

    @Test
    public void equals_returnsTrueIfArgumentHasSameSymbol() {
        Assert.assertEquals(negatedSym, new NegatedSymbol(IDENTICAL_SYMBOL));
    }

    @Test
    public void equals_returnsTrueIfArgmentHasDifferentSymbol() {
        Assert.assertNotEquals(negatedSym, new NegatedSymbol(new Symbol("diffSymbol")));
    }
}