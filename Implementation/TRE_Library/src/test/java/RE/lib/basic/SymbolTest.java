package RE.lib.basic;

import RE.lib.RegExp;
import RE.lib.exceptions.NullParamsException;
import RE.lib.exceptions.NullSymbolException;
import RE.lib.operators.TimeInterval;
import TA.lib.State;
import TA.lib.TimedAutomaton;
import TA.lib.Transition;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.util.*;

public class SymbolTest {

    private static final Symbol theSymbol = new Symbol("testSymbol");
    private static final RegExp symbol = theSymbol;

    private static final List<String> theParams = Arrays.asList("u", "v", "w");
    private static final Symbol theParamsSymbol = new Symbol("testParamSymbol", theParams);
    private static final RegExp paramsSymbol = theParamsSymbol;

    private static final Duration DUMMY_DURATION = Duration.ofSeconds(5);
    private static final Symbol SAME_SYMBOL_ZERO_PARAMETERS = new Symbol("testSymbol");
    private static final Symbol SAME_SYMBOL_SAME_PARAMETERS = new Symbol("testParamSymbol", Arrays.asList("u", "v", "w"));
    private static final Symbol SAME_SYMBOL_DIFF_PARAMETERS = new Symbol("testParamSymbol", Arrays.asList("u", "x", "w"));
    private static final Symbol DIFF_SYMBOL_ZERO_PARAMETERS = new Symbol("diffSymbol");
    private static final Symbol DIFF_SYMBOL_SAME_PARAMETERS = new Symbol("diffParamSymbol", Arrays.asList("u", "v", "w"));
    private static final Symbol DIFF_SYMBOL_DIFF_PARAMETERS = new Symbol("diffParamSymbol", Arrays.asList("u", "x", "w"));

    @Test(expected = NullSymbolException.class)
    public void newNullSymbolThrowsError() {
        new Symbol(null);
    }

    @Test(expected = NullParamsException.class)
    public void parameterized_newNullParamsThrowsError() {
        new Symbol("testParamSymbol", null);
    }

    @Test
    public void newNonNullSymbolSetsSymbolCorrectly() {
        final String actualSymbol = "symbol";
        Assert.assertEquals(actualSymbol, new Symbol(actualSymbol).symbol);
    }

    @Test
    public void getDerivative_wrtDifferentSymbolAndAnyTimeReturnsEmptySet() {
        final RegExp residual = symbol.getDerivative(DIFF_SYMBOL_ZERO_PARAMETERS, DUMMY_DURATION);
        Assert.assertEquals(EmptySet.INSTANCE, residual);
    }

    @Test
    public void getDerivative_wrtIdenticalSymbolAndAnyTimeReturnsEmptyString() {
        final RegExp residual = symbol.getDerivative(SAME_SYMBOL_ZERO_PARAMETERS, DUMMY_DURATION);
        Assert.assertEquals(EmptyString.INSTANCE, residual);
    }

    @Test
    public void getDerivative_wrtAnyTimeChangesNothing() {
        final RegExp residual = symbol.getDerivative(DUMMY_DURATION);
        Assert.assertEquals(symbol, residual);
    }

    @Test
    public void hasEmptyString_returnsFalse() {
        Assert.assertFalse(symbol.hasEmptyString());
    }

    @Test
    public void isEmpty_returnsFalse() {
        Assert.assertFalse(symbol.isEmpty());
    }

    @Test
    public void removeEmptyString_changesNothing() {
        Assert.assertEquals(symbol, symbol.removeEmptyString());
    }

    @Test
    public void minTimeoutValue_returnsInfinity() {
        Assert.assertEquals(TimeInterval.INFINITY, symbol.minTimeoutValue());
    }

    @Test
    public void getFrontSymbols_returnsOnlyTheSymbol() {
        final Set<Symbol> frontSymbols = theSymbol.getFrontSymbols(null);
        Assert.assertEquals(1, frontSymbols.size());
        Assert.assertTrue(frontSymbols.contains(theSymbol));
    }

    @Test
    public void toTimedAutomaton_1_returnsTheExpectedAutomaton() {
        toTimedAutomaton_returnsTheExpectedAutomaton(theSymbol);
    }

    @Test
    public void toTimedAutomaton_2_returnsTheExpectedAutomaton() {
        toTimedAutomaton_returnsTheExpectedAutomaton(theParamsSymbol);
    }

    private void toTimedAutomaton_returnsTheExpectedAutomaton(final Symbol symbol) {

        final Symbol A = new Symbol("A");
        final Symbol B = new Symbol("B");
        final Symbol C = new Symbol("C");

        final LinkedHashSet<Symbol> alphabet = new LinkedHashSet<>(Arrays.asList(A, B, C, symbol));
        final LinkedHashSet<Symbol> justSymbol = new LinkedHashSet<>(Collections.singletonList(symbol));
        final TimedAutomaton ta = symbol.toTimedAutomaton(alphabet);

        Assert.assertEquals(2, ta.getStates().size());
        Assert.assertEquals(0, ta.getClocks().size());
        Assert.assertEquals(1, ta.getTransitions().size());
        Assert.assertEquals(justSymbol, ta.getAlphabet()); // important
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
        Assert.assertEquals(symbol, t.symbol);
        Assert.assertEquals(0, t.guard.size());
        Assert.assertEquals(0, t.toReset.size());
    }

    @Test
    public void getSymbol_returnsTheSymbol() {
        Assert.assertEquals("testSymbol", theSymbol.getSymbol());
    }

    @Test
    public void equals_returnsTrueIfArgumentHasSameSymbol() {
        Assert.assertEquals(symbol, SAME_SYMBOL_ZERO_PARAMETERS);
    }

    @Test
    public void equals_returnsFalseIfArgmentHasDifferentSymbol() {
        Assert.assertNotEquals(symbol, new Symbol("diffSymbol"));
    }

    @Test
    public void equals_returnsTrueIfArgumentHasSameSymbolAndSameParameters() {
        Assert.assertEquals(paramsSymbol, SAME_SYMBOL_SAME_PARAMETERS);
    }

    @Test
    public void equals_returnsFalseIfArgumentHasDifferentSymbolOrDifferentParameters() {
        Assert.assertNotEquals(paramsSymbol, SAME_SYMBOL_DIFF_PARAMETERS);
        Assert.assertNotEquals(paramsSymbol, DIFF_SYMBOL_SAME_PARAMETERS);
        Assert.assertNotEquals(paramsSymbol, DIFF_SYMBOL_DIFF_PARAMETERS);
    }

    @Test
    public void hashcode_equalIfIdenticalSymbolsWithSameParameters() {
        final Symbol sym1 = new Symbol("sym", new ArrayList<>(Arrays.asList("u", "v", "w")));
        final Symbol sym2 = new Symbol("sym", new ArrayList<>(Arrays.asList("u", "v", "w")));
        Assert.assertEquals(sym1.hashCode(), sym2.hashCode());
    }

    @Test
    public void hashcode_unequalIfDifferentSymbols() {
        final Symbol sym1 = new Symbol("sym1", new ArrayList<>(Arrays.asList("u", "v", "w")));
        final Symbol sym2 = new Symbol("sym2", new ArrayList<>(Arrays.asList("u", "v", "w")));
        Assert.assertNotEquals(sym1.hashCode(), sym2.hashCode());
    }

    @Test
    public void hashcode_unequalIfDifferentParameters() {
        final Symbol sym1 = new Symbol("sym", new ArrayList<>(Arrays.asList("u", "v", "w")));
        final Symbol sym2 = new Symbol("sym", new ArrayList<>(Arrays.asList("u", "w", "v")));
        Assert.assertNotEquals(sym1.hashCode(), sym2.hashCode());
    }
}