package TA.lib;

import RE.lib.basic.Symbol;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.*;

public class TransitionTest {

    private State FROM = new State();
    private State TO = new State();
    private Symbol SYMBOL = new Symbol("TheSymbol");
    private List<ClockCondition> GUARD = Collections.singletonList(new ClockCondition(new Clock(), Duration.ofSeconds(5)));
    private LinkedHashSet<Clock> TO_RESET = new LinkedHashSet<>(Collections.singletonList(new Clock()));

    private Transition transition_withNothing;
    private Transition transition_withGuard;
    private Transition transition_withResets;
    private Transition transition_withAll;

    @Before
    public void setUp() {
        transition_withNothing = new Transition(FROM, TO, SYMBOL);
        transition_withGuard = new Transition(FROM, TO, SYMBOL, GUARD);
        transition_withResets = new Transition(FROM, TO, SYMBOL, TO_RESET);
        transition_withAll = new Transition(FROM, TO, SYMBOL, GUARD, TO_RESET);
    }

    @After
    public void tearDown() {
        transition_withNothing = null;
        transition_withGuard = null;
        transition_withResets = null;
        transition_withAll = null;
    }

    @Test
    public void constructor_ifGuardNotSpecifiedItIsEmptyList() {
        Assert.assertEquals(new ArrayList<>(), transition_withNothing.guard);
        Assert.assertEquals(new ArrayList<>(), transition_withResets.guard);
    }

    @Test
    public void constructor_ifResetsNotSpecifiedItIsEmptySet() {
        Assert.assertEquals(new LinkedHashSet<>(), transition_withNothing.toReset);
        Assert.assertEquals(new LinkedHashSet<>(), transition_withGuard.toReset);
    }

    @Test
    public void constructor_withBothGuardAndResets() {
        Assert.assertEquals(GUARD, transition_withAll.guard);
        Assert.assertEquals(TO_RESET, transition_withAll.toReset);
    }

    @Test
    public void toEdgeString_withEmptyString() {
        Assert.assertEquals("\'\'", new Transition(FROM, TO, EmptySymbol.INSTANCE).toEdgeString());
    }

    @Test
    public void toEdgeString_withNothing() {
        Assert.assertEquals(SYMBOL.getSymbol(), transition_withNothing.toEdgeString());
    }

    @Test
    public void toEdgeString_withGuard() {
        final String expected = String.format("%s\\%s", SYMBOL, GUARD.toString());
        Assert.assertEquals(expected, transition_withGuard.toEdgeString());
    }

    @Test
    public void toEdgeString_withResets() {
        final String expected = String.format("%s\\\\%s", SYMBOL, TO_RESET.toString());
        Assert.assertEquals(expected, transition_withResets.toEdgeString());
    }

    @Test
    public void toEdgeStringtoEdgeString_withAll() {
        final String expected = String.format("%s\\%s\\%s", SYMBOL, GUARD.toString(), TO_RESET.toString());
        Assert.assertEquals(expected, transition_withAll.toEdgeString());
    }

    @Test
    public void hasGuardOrResets_falseIfNoGuardsAndNoResets() {
        Assert.assertFalse(transition_withNothing.hasGuardOrResets());
    }

    @Test
    public void hasGuardOrResets_trueIfGuardOrResets() {
        Assert.assertTrue(transition_withGuard.hasGuardOrResets());
        Assert.assertTrue(transition_withResets.hasGuardOrResets());
        Assert.assertTrue(transition_withAll.hasGuardOrResets());
    }

    @Test
    public void hasNoGuardAndNoResets_trueIfNoGuardsOrNoReset() {
        Assert.assertTrue(transition_withNothing.hasNoGuardAndNoResets());
    }

    @Test
    public void hasNoGuardAndNoResets_falseIfGuardOrResets() {
        Assert.assertFalse(transition_withGuard.hasNoGuardAndNoResets());
        Assert.assertFalse(transition_withResets.hasNoGuardAndNoResets());
        Assert.assertFalse(transition_withAll.hasNoGuardAndNoResets());
    }

    @Test
    public void isEmptySymbolTransition_trueIfEmptySymbolTransition() {
        Assert.assertTrue(new Transition(FROM, TO, EmptySymbol.INSTANCE).isEmptySymbolTransition());
    }

    @Test
    public void isEmptySymbolTransition_falseIfNotEmptySymbolTransition() {
        Assert.assertFalse(new Transition(FROM, TO, SYMBOL).isEmptySymbolTransition());
    }

    @Test
    public void equals_trueIfMembersAreTheSame() {

        final Symbol symbol = new Symbol("TheSymbol");
        final List<ClockCondition> guard = new ArrayList<>(GUARD);
        final LinkedHashSet<Clock> toReset = new LinkedHashSet<>(TO_RESET);
        final Transition toCompareWith = new Transition(FROM, TO, symbol, guard, toReset);

        Assert.assertEquals(transition_withAll, toCompareWith);
    }

    @Test
    public void hashCode_returnsHashCodeOfAllMembers() {
        final Transition t = transition_withAll;
        Assert.assertEquals(Objects.hash(t.from, t.guard, t.toReset, t.symbol, t.to), t.hashCode());
    }
}