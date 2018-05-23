package transactionsystem;

import larva.*;
import larvaTools.LarvaController;
import org.junit.*;

public class RE_operators_b {

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

    @Test // [n] initialise.createUser; {reconcile}
    public void prop1_scen1() {
        assumeStartState(_cls_script_10.root.currentState);

        tr.ADMIN_initialise();
        assertNotViolated(_cls_script_10.root.currentState);
        tr.ADMIN_createUser(USER_NAME, USER_COUNTRY);
        assertViolated(_cls_script_10.root.currentState);
    }

    @Test // [n] initialise.createUser; {reconcile}
    public void prop1_scen2() {
        assumeStartState(_cls_script_10.root.currentState);

        tr.ADMIN_createUser(USER_NAME, USER_COUNTRY);
        assertAccepted(_cls_script_10.root.currentState);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* OR OPERATOR                                                                                                    */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // Violation of: [n] initialise + reconcile; {createUser}
    public void prop2_scen1() {
        assumeStartState(_cls_script_20.root.currentState);

        tr.ADMIN_initialise();
        assertViolated(_cls_script_20.root.currentState);
    }

    @Test // Violation of: [n] initialise + reconcile; {createUser}
    public void prop2_scen2() {
        assumeStartState(_cls_script_20.root.currentState);

        tr.ADMIN_reconcile();
        assertViolated(_cls_script_20.root.currentState);
    }

    @Test // ___: [n] initialise + reconcile; {createUser}
    public void prop2_scen3() {
        assumeStartState(_cls_script_20.root.currentState);

        tr.ADMIN_createUser(USER_NAME, USER_COUNTRY);
        assertAccepted(_cls_script_20.root.currentState);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* AND OPERATOR                                                                                                   */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // Violation of: [n] initialise & ~reconcile; {createUser}
    public void prop3_scen1() {
        assumeStartState(_cls_script_30.root.currentState);

        tr.ADMIN_initialise();
        assertViolated(_cls_script_30.root.currentState);
    }

    @Test // Acceptance of: [n] initialise & ~reconcile; {createUser}
    public void prop3_scen2() {
        assumeStartState(_cls_script_30.root.currentState);

        tr.ADMIN_reconcile();
        assertAccepted(_cls_script_30.root.currentState);
    }

    @Test // Acceptance of: [n] initialise & ~reconcile; {createUser}
    public void prop3_scen3() {
        assumeStartState(_cls_script_30.root.currentState);

        tr.ADMIN_createUser(USER_NAME, USER_COUNTRY);
        assertAccepted(_cls_script_30.root.currentState);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* STAR OPERATOR                                                                                                  */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // Violation of: [n] createUser*; {reconcile}
    public void prop4_scen1() {
        assertViolated(_cls_script_40.root.currentState); // Note: as expected, this fails
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* TIME INTERVAL OPERATOR                                                                                         */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // Violation of: [n] initialise[2,4];
    public void prop5_scen1() throws InterruptedException {
        assumeStartState(_cls_script_50.root.currentState);

        Thread.sleep(2100); // wait enough
        tr.ADMIN_initialise();
        assertViolated(_cls_script_50.root.currentState);
    }

    @Test // Acceptance of: [n] initialise[2,4];
    public void prop5_scen2() {
        assumeStartState(_cls_script_50.root.currentState);

        // <no wait>
        tr.ADMIN_initialise();
        assertAccepted(_cls_script_50.root.currentState);
    }

    @Test // Acceptance of: [n] initialise[2,4];
    public void prop5_scen3() throws InterruptedException {
        assumeStartState(_cls_script_50.root.currentState);

        Thread.sleep(1000); // wait not enough
        tr.ADMIN_initialise();
        assertAccepted(_cls_script_50.root.currentState);
    }

    @Test // Acceptance of: [n] initialise[2,4];
    public void prop5_scen4() throws InterruptedException {
        assumeStartState(_cls_script_50.root.currentState);

        Thread.sleep(4100); // wait too much
        assertAccepted(_cls_script_50.root.currentState);
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
