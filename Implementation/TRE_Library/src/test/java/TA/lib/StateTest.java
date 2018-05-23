package TA.lib;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class StateTest {

    private State state;

    @Before
    public void setUp() {
        state = new State();
    }

    @After
    public void tearDown() {
        state = null;
    }

    @Test
    public void defaultName() {
        Assert.assertEquals("", state.name);
    }

    @Test
    public void addStateSkip_addsStateSkip() {
        final StateSkip dummyTrans = new StateSkip(new State(), null, null);
        state.addStateSkip(dummyTrans);
        Assert.assertTrue(state.stateSkips.contains(dummyTrans));
    }

    @Test
    public void getStateSkips_returnsStateSkips() {
        final StateSkip dummyTrans1 = new StateSkip(new State(), null, null);
        final StateSkip dummyTrans2 = new StateSkip(new State(), null, null);
        state.stateSkips.add(dummyTrans1);
        state.stateSkips.add(dummyTrans2);
        Assert.assertEquals(new LinkedHashSet<>(Arrays.asList(dummyTrans1, dummyTrans2)), state.getStateSkips());
    }

    @Test
    public void toString_doesNotReturnNameIfDefaultName() {
        Assert.assertNotEquals(state.name, state.toString());
    }

    @Test
    public void toString_returnsNameIfNotDefaultName() {
        state.name = "StateName";
        Assert.assertEquals("StateName", state.toString());
    }

    /**
     * (Only for testing purposes!)
     */
    public static State newState() {
        return new State();
    }
}