package TA.lib;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.*;

public class StateSkipTest {

    private State TO = new State();
    private List<ClockCondition> GUARD = Collections.singletonList(new ClockCondition(new Clock(), Duration.ofSeconds(5)));
    private LinkedHashSet<Clock> TO_RESET = new LinkedHashSet<>(Collections.singletonList(new Clock()));

    private StateSkip transition_withNothing;
    private StateSkip transition_withGuard;
    private StateSkip transition_withResets;
    private StateSkip transition_withAll;

    @Before
    public void setUp() {
        transition_withNothing = new StateSkip(TO);
        transition_withGuard = new StateSkip(TO, GUARD);
        transition_withResets = new StateSkip(TO, TO_RESET);
        transition_withAll = new StateSkip(TO, GUARD, TO_RESET);
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
        Assert.assertEquals("", new StateSkip(TO).toEdgeString());
    }

    @Test
    public void toEdgeString_withNothing() {
        Assert.assertEquals("", transition_withNothing.toEdgeString());
    }

    @Test
    public void toEdgeString_withGuard() {
        final String expected = String.format("%s", GUARD.toString());
        Assert.assertEquals(expected, transition_withGuard.toEdgeString());
    }

    @Test
    public void toEdgeString_withResets() {
        final String expected = String.format("\\%s", TO_RESET.toString());
        Assert.assertEquals(expected, transition_withResets.toEdgeString());
    }

    @Test
    public void toEdgeStringtoEdgeString_withAll() {
        final String expected = String.format("%s\\%s", GUARD.toString(), TO_RESET.toString());
        Assert.assertEquals(expected, transition_withAll.toEdgeString());
    }

    @Test
    public void equals_trueIfMembersAreTheSame() {

        final List<ClockCondition> guard = new ArrayList<>(GUARD);
        final LinkedHashSet<Clock> toReset = new LinkedHashSet<>(TO_RESET);
        final StateSkip toCompareWith = new StateSkip(TO, guard, toReset);

        Assert.assertEquals(transition_withAll, toCompareWith);
    }

    @Test
    public void hashCode_returnsHashCodeOfAllMembers() {
        final StateSkip t = transition_withAll;
        Assert.assertEquals(Objects.hash(t.guard, t.toReset, t.to), t.hashCode());
    }
}