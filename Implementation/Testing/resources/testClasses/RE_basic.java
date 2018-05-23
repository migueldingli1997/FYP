package transactionsystem;

import larva.*;
import larvaTools.LarvaController;
import org.junit.*;

public class RE_basic {

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
    /* BASIC EXPRESSIONS                                                                                              */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // Violation of: [p] 0; {initialise}
    public void prop1_scen1() {
        assertViolated(_cls_script_10.root.currentState); // Note: as expected, this fails
    }

    @Test // Violation of: [p] 1; {initialise}
    public void prop2_scen1() {
        assumeStartState(_cls_script_20.root.currentState);

        // <do nothing>
        assertNotViolated(_cls_script_20.root.currentState);
    }

    @Test // Non-Violation of: [p] 1; {initialise}
    public void prop2_scen2() {
        assumeStartState(_cls_script_20.root.currentState);

        tr.ADMIN_initialise();
        assertViolated(_cls_script_20.root.currentState);
    }

    @Test // Violation of: [p] ?; {initialise, reconcile}
    public void prop3_scen1() {
        assumeStartState(_cls_script_30.root.currentState);

        tr.ADMIN_initialise();
        assertNotViolated(_cls_script_30.root.currentState);
        tr.ADMIN_initialise();
        assertViolated(_cls_script_30.root.currentState);
    }

    @Test // Non-Violation of: [p] ?; {initialise, reconcile}
    public void prop3_scen2() {
        assumeStartState(_cls_script_30.root.currentState);

        tr.ADMIN_initialise();
        assertNotViolated(_cls_script_30.root.currentState);
    }

    @Test // Non-Violation of: [p] ?; {initialise, reconcile}
    public void prop3_scen3() {
        assumeStartState(_cls_script_30.root.currentState);

        tr.ADMIN_reconcile();
        assertNotViolated(_cls_script_30.root.currentState);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* SINGLE PLAIN SYMBOL                                                                                            */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // Violation of: [p] initialise; {reconcile}
    public void prop4_scen1() {
        assumeStartState(_cls_script_40.root.currentState);

        tr.ADMIN_reconcile();
        assertViolated(_cls_script_40.root.currentState);
    }

    @Test // Non-Violation of: [p] initialise; {reconcile}
    public void prop4_scen2() {
        assumeStartState(_cls_script_40.root.currentState);

        tr.ADMIN_initialise();
        assertNotViolated(_cls_script_40.root.currentState);
    }

    @Test // Acceptance of: [n] initialise; {reconcile}
    public void prop5_scen1() {
        assumeStartState(_cls_script_50.root.currentState);

        tr.ADMIN_reconcile();
        assertAccepted(_cls_script_50.root.currentState);
    }

    @Test // Violation of: [n] initialise; {reconcile}
    public void prop5_scen2() {
        assumeStartState(_cls_script_50.root.currentState);

        tr.ADMIN_initialise();
        assertViolated(_cls_script_50.root.currentState);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* SINGLE NEGATED SYMBOL                                                                                          */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // Non-Violation of: [p] ~initialise; {reconcile}
    public void prop6_scen1() {
        assumeStartState(_cls_script_60.root.currentState);

        tr.ADMIN_reconcile();
        assertNotViolated(_cls_script_60.root.currentState);
    }

    @Test // Violation of: [p] ~initialise; {reconcile}
    public void prop6_scen2() {
        assumeStartState(_cls_script_60.root.currentState);

        tr.ADMIN_initialise();
        assertViolated(_cls_script_60.root.currentState);
    }

    @Test // Violation of: [n] ~initialise; {reconcile}
    public void prop7_scen1() {
        assumeStartState(_cls_script_70.root.currentState);

        tr.ADMIN_reconcile();
        assertViolated(_cls_script_70.root.currentState);
    }

    @Test // Acceptance of: [n] ~initialise; {reconcile}
    public void prop7_scen2() {
        assumeStartState(_cls_script_70.root.currentState);

        tr.ADMIN_initialise();
        assertAccepted(_cls_script_70.root.currentState);
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
