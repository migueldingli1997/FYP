package transactionsystem;

import larva.*;
import larvaTools.LarvaController;
import org.junit.*;

public class TA_mix {

    private static final int START = 0;
    private static final int BAD = -1;
    private static final int ACCEPT = 1;

    private static final String USER_NAME = "name";
    private static final String USER_COUNTRY = "country";

    private Interface tr;

    @Before
    public void setUp() {
        tr = new Interface();
        new LarvaController().triggerReset();
    }

    @After
    public void tearDown() {
        _cls_script_11._cls_script_11_instances.clear();
        _cls_script_21._cls_script_21_instances.clear();
        _cls_script_31._cls_script_31_instances.clear();
        _cls_script_41._cls_script_41_instances.clear();
        synchronized (RunningClock.lock) {
            while (RunningClock.events.getNext() != null) {
                RunningClock.events.remove();
            }
        }
        new LarvaController().triggerStop();
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* CONCAT AND TIME INTERVAL OPERATORS                                                                             */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // Violation of: [p] initialise.createUser[PT2S,PT4S]; {reconcile}
    public void prop1_scen1() throws InterruptedException {
        assumeStartState(_cls_script_10.root.currentState);

        tr.ADMIN_initialise();
        Thread.sleep(1900);
        assertNotViolated(_cls_script_10.root.currentState);
        tr.ADMIN_createUser(USER_NAME, USER_COUNTRY);
        assertViolated(_cls_script_10.root.currentState);
    }

    @Test // Non-Violation of: [p] initialise.createUser[PT2S,PT4S]; {reconcile}
    public void prop1_scen2() throws InterruptedException {
        assumeStartState(_cls_script_10.root.currentState);

        tr.ADMIN_initialise();
        Thread.sleep(2100);
        assertNotViolated(_cls_script_10.root.currentState);
        tr.ADMIN_createUser(USER_NAME, USER_COUNTRY);
        assertNotViolated(_cls_script_10.root.currentState);
    }

    @Test // Non-Violation of: [p] initialise.createUser[PT2S,PT4S]; {reconcile}
    public void prop1_scen3() throws InterruptedException {
        assumeStartState(_cls_script_10.root.currentState);

        tr.ADMIN_initialise();
        Thread.sleep(3000);
        assertNotViolated(_cls_script_10.root.currentState);
        tr.ADMIN_createUser(USER_NAME, USER_COUNTRY);
        assertNotViolated(_cls_script_10.root.currentState);
    }

    @Test // Violation of: [p] initialise.createUser[PT2S,PT4S]; {reconcile}
    public void prop1_scen4() throws InterruptedException {
        assumeStartState(_cls_script_10.root.currentState);

        tr.ADMIN_initialise();
        Thread.sleep(4100);
        assertViolated(_cls_script_10.root.currentState);
    }

    @Test // Violation of: [p] initialise.createUser[PT2S,PT4S]; {reconcile}
    public void prop1_scen5() throws InterruptedException {
        assumeStartState(_cls_script_10.root.currentState);

        tr.ADMIN_initialise();
        Thread.sleep(2000);
        assertNotViolated(_cls_script_10.root.currentState);
        tr.ADMIN_reconcile();
        assertViolated(_cls_script_10.root.currentState);
    }

    @Test // Violation of: [p] initialise.createUser[PT2S,PT4S]; {reconcile}
    public void prop1_scen6() throws InterruptedException {
        assumeStartState(_cls_script_10.root.currentState);

        tr.ADMIN_initialise();
        Thread.sleep(3000);
        assertNotViolated(_cls_script_10.root.currentState);
        tr.ADMIN_reconcile();
        assertViolated(_cls_script_10.root.currentState);
    }

    @Test // Violation of: [n] initialise.createUser[PT2S,PT4S]; {reconcile}
    public void prop2_scen1() throws InterruptedException {
        assumeStartState(_cls_script_20.root.currentState);

        tr.ADMIN_initialise();
        Thread.sleep(1900);
        assertNotViolated(_cls_script_20.root.currentState);
        tr.ADMIN_createUser(USER_NAME, USER_COUNTRY);
        assertAccepted(_cls_script_20.root.currentState);
    }

    @Test // Non-Violation of: [n] initialise.createUser[PT2S,PT4S]; {reconcile}
    public void prop2_scen2() throws InterruptedException {
        assumeStartState(_cls_script_20.root.currentState);

        tr.ADMIN_initialise();
        Thread.sleep(2000);
        assertNotViolated(_cls_script_20.root.currentState);
        tr.ADMIN_createUser(USER_NAME, USER_COUNTRY);
        assertViolated(_cls_script_20.root.currentState);
    }

    @Test // Non-Violation of: [n] initialise.createUser[PT2S,PT4S]; {reconcile}
    public void prop2_scen3() throws InterruptedException {
        assumeStartState(_cls_script_20.root.currentState);

        tr.ADMIN_initialise();
        Thread.sleep(3000);
        assertNotViolated(_cls_script_20.root.currentState);
        tr.ADMIN_createUser(USER_NAME, USER_COUNTRY);
        assertViolated(_cls_script_20.root.currentState);
    }

    @Test // Violation of: [n] initialise.createUser[PT2S,PT4S]; {reconcile}
    public void prop2_scen4() throws InterruptedException {
        assumeStartState(_cls_script_20.root.currentState);

        tr.ADMIN_initialise();
        Thread.sleep(4100);
        assertAccepted(_cls_script_20.root.currentState);
    }

    @Test // Violation of: [n] initialise.createUser[PT2S,PT4S]; {reconcile}
    public void prop2_scen5() throws InterruptedException {
        assumeStartState(_cls_script_20.root.currentState);

        tr.ADMIN_initialise();
        Thread.sleep(2000);
        assertNotViolated(_cls_script_20.root.currentState);
        tr.ADMIN_reconcile();
        assertAccepted(_cls_script_20.root.currentState);
    }

    @Test // Violation of: [n] initialise.createUser[PT2S,PT4S]; {reconcile}
    public void prop2_scen6() throws InterruptedException {
        assumeStartState(_cls_script_20.root.currentState);

        tr.ADMIN_initialise();
        Thread.sleep(3000);
        assertNotViolated(_cls_script_20.root.currentState);
        tr.ADMIN_reconcile();
        assertAccepted(_cls_script_20.root.currentState);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* FAST LOOPING                                                                                                   */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // [p] (reconcile[0,1])*;
    public void prop3_scen1() throws InterruptedException {
        assumeStartState(_cls_script_30.root.currentState);

        for (int i = 0; i < 100; i++) {
            tr.ADMIN_reconcile();
            assertNotViolated(_cls_script_30.root.currentState);
        }
        Thread.sleep(1100);
        assertNotViolated(_cls_script_30.root.currentState); // since 0* = 1
        tr.ADMIN_reconcile();
        assertViolated(_cls_script_30.root.currentState);
    }

    @Test // [p] (reconcile[0,0.5])*;
    public void prop4_scen1() throws InterruptedException {
        assumeStartState(_cls_script_40.root.currentState);

        for (int i = 0; i < 100; i++) {
            tr.ADMIN_reconcile();
            assertNotViolated(_cls_script_40.root.currentState);
        }
        Thread.sleep(600);
        assertNotViolated(_cls_script_40.root.currentState); // since 0* = 1
        tr.ADMIN_reconcile();
        assertViolated(_cls_script_40.root.currentState);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* HELPER METHODS                                                                                                 */
    /* -------------------------------------------------------------------------------------------------------------- */

    private void assumeStartState(final int state) {
        Assume.assumeTrue(START == state);
    }

    private void assertNotViolated(final int state) {
        Assert.assertEquals(START, state);
    }

    private void assertViolated(final int state) {
        Assert.assertEquals(BAD, state);
    }

    private void assertAccepted(final int state) {
        Assert.assertEquals(ACCEPT, state);
    }
}
