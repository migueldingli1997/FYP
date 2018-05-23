package transactionsystem;

import larva.*;
import larvaTools.LarvaController;
import org.junit.*;

public class TA_operators_g {

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
        _cls_script_51._cls_script_51_instances.clear();
        synchronized (RunningClock.lock) {
            while (RunningClock.events.getNext() != null) {
                RunningClock.events.remove();
            }
        }
        new LarvaController().triggerStop();
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* CONCAT OPERATOR                                                                                                */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // [p] initialise.createUser; {reconcile}
    public void prop1_scen1() {
        assumeStartState(_cls_script_10.root.currentState);

        tr.ADMIN_initialise();
        assertNotViolated(_cls_script_10.root.currentState);
        tr.ADMIN_createUser(USER_NAME, USER_COUNTRY);
        assertNotViolated(_cls_script_10.root.currentState);
        tr.ADMIN_reconcile();
        assertViolated(_cls_script_10.root.currentState);
    }

    @Test // [p] initialise.createUser; {reconcile}
    public void prop1_scen2() {
        assumeStartState(_cls_script_10.root.currentState);

        tr.ADMIN_initialise();
        assertNotViolated(_cls_script_10.root.currentState);
        tr.ADMIN_reconcile();
        assertViolated(_cls_script_10.root.currentState);
    }

    @Test // [p] initialise.createUser; {reconcile}
    public void prop1_scen3() {
        assumeStartState(_cls_script_10.root.currentState);

        tr.ADMIN_reconcile();
        assertViolated(_cls_script_10.root.currentState);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* OR OPERATOR                                                                                                    */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // Violation of: [p] initialise + reconcile; {createUser}
    public void prop2_scen1() {
        assumeStartState(_cls_script_20.root.currentState);

        tr.ADMIN_createUser(USER_NAME, USER_COUNTRY);
        assertViolated(_cls_script_20.root.currentState);
    }

    @Test // Non-Violation of: [p] initialise + reconcile; {createUser}
    public void prop2_scen2() {
        assumeStartState(_cls_script_20.root.currentState);

        tr.ADMIN_initialise();
        assertNotViolated(_cls_script_20.root.currentState);
    }

    @Test // Non-Violation of: [p] initialise + reconcile; {createUser}
    public void prop2_scen3() {
        assumeStartState(_cls_script_20.root.currentState);

        tr.ADMIN_reconcile();
        assertNotViolated(_cls_script_20.root.currentState);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* AND OPERATOR                                                                                                   */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // Violation of: [p] initialise & ~reconcile; {createUser}
    public void prop3_scen1() {
        assumeStartState(_cls_script_30.root.currentState);

        tr.ADMIN_reconcile();
        assertViolated(_cls_script_30.root.currentState);
    }

    @Test // Violation of: [p] initialise & ~reconcile; {createUser}
    public void prop3_scen2() {
        assumeStartState(_cls_script_30.root.currentState);

        tr.ADMIN_createUser(USER_NAME, USER_COUNTRY);
        assertViolated(_cls_script_30.root.currentState);
    }

    @Test // Non-Violation of: [p] initialise & ~reconcile; {createUser}
    public void prop3_scen3() {
        assumeStartState(_cls_script_30.root.currentState);

        tr.ADMIN_initialise();
        assertNotViolated(_cls_script_30.root.currentState);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* STAR OPERATOR                                                                                                  */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // Violation of: [p] createUser*; {reconcile}
    public void prop4_scen1() {
        assumeStartState(_cls_script_40.root.currentState);

        tr.ADMIN_reconcile();
        assertViolated(_cls_script_40.root.currentState);
    }

    @Test // Violation of: [p] createUser*; {reconcile}
    public void prop4_scen2() {
        assumeStartState(_cls_script_40.root.currentState);

        for (int i = 0; i < 100; i++) {
            tr.ADMIN_createUser(USER_NAME, USER_COUNTRY);
            assertNotViolated(_cls_script_40.root.currentState);
        }
        tr.ADMIN_reconcile();
        assertViolated(_cls_script_40.root.currentState);
    }

    @Test // Non-Violation of: [p] createUser*; {reconcile}
    public void prop4_scen3() {
        assumeStartState(_cls_script_40.root.currentState);

        // <do nothing>
        assertNotViolated(_cls_script_40.root.currentState);
    }

    @Test // Non-Violation of: [p] createUser*; {reconcile}
    public void prop4_scen4() {
        assumeStartState(_cls_script_40.root.currentState);

        for (int i = 0; i < 100; i++) {
            tr.ADMIN_createUser(USER_NAME, USER_COUNTRY);
            assertNotViolated(_cls_script_40.root.currentState);
        }
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* TIME INTERVAL OPERATOR                                                                                         */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // Violation of: [p] initialise[2,4];
    public void prop5_scen1() {
        assumeStartState(_cls_script_50.root.currentState);

        // <no wait>
        tr.ADMIN_initialise();
        assertViolated(_cls_script_50.root.currentState);
    }

    @Test // Violation of: [p] initialise[2,4];
    public void prop5_scen2() throws InterruptedException {
        assumeStartState(_cls_script_50.root.currentState);

        Thread.sleep(1000); // wait not enough
        tr.ADMIN_initialise();
        assertViolated(_cls_script_50.root.currentState);
    }

    @Test // Violation of: [p] initialise[2,4];
    public void prop5_scen3() throws InterruptedException {
        assumeStartState(_cls_script_50.root.currentState);

        Thread.sleep(4100); // wait too much
        assertViolated(_cls_script_50.root.currentState);
    }

    @Test // Non-Violation of: [p] initialise[2,4];
    public void prop5_scen4() throws InterruptedException {
        assumeStartState(_cls_script_50.root.currentState);

        Thread.sleep(2100); // wait enough
        tr.ADMIN_initialise();
        assertNotViolated(_cls_script_50.root.currentState);
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
