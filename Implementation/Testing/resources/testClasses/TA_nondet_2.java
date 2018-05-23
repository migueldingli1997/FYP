package transactionsystem;

import larva.*;
import larvaTools.LarvaController;
import org.junit.*;

import java.util.LinkedHashMap;

import static transactionsystem.TA_nondet_2.Actions.*;

public class TA_nondet_2 {

    private static final int START = 0;
    private static final int BAD = -1;
    private static final int ACCEPT = 1;

    private static final String USER_NAME = "name";
    private static final String USER_COUNTRY = "country";

    private UserInfo ui;
    private UserSession us;
    private UserAccount ua;

    @Before
    public void setUp() {
        ui = new UserInfo(123, "John", "Malta");
        us = new UserSession(123, 456, ui);
        ua = new UserAccount(123, "123", ui);
        new LarvaController().triggerReset();
    }

    @After
    public void tearDown() {
        new LinkedHashMap<>(_cls_script_11._cls_script_11_instances).forEach((k, v) -> k._killThis());
        new LinkedHashMap<>(_cls_script_21._cls_script_21_instances).forEach((k, v) -> k._killThis());
        synchronized (RunningClock.lock) {
            while (RunningClock.events.getNext() != null) {
                RunningClock.events.remove();
            }
        }
        new LarvaController().triggerStop();
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* CLOCK VALUE PROPAGATION : [p] (A.(C.E + C.F))[2,4] + (A.(D.E + D.F))[5,7];                                     */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // A.C.E
    public void prop3_scen1() throws InterruptedException {
        assumeStartState(_cls_script_10.root.currentState);
        invoke(1000, A); // occurs at time = 1
        invoke(1000, C); // occurs at time = 2
        invoke(1000, E); // occurs at time = 3
        assertNotViolated(_cls_script_10.root.currentState);
        Thread.sleep(1100); // sleep until timeout exceeded to be sure
        assertNotViolated(_cls_script_10.root.currentState);
    }

    @Test // A.C.F
    public void prop3_scen2() throws InterruptedException {
        assumeStartState(_cls_script_10.root.currentState);
        invoke(1000, A); // occurs at time = 1
        invoke(1000, C); // occurs at time = 2
        invoke(1000, F); // occurs at time = 3
        assertNotViolated(_cls_script_10.root.currentState);
        Thread.sleep(1100); // sleep until timeout exceeded to be sure
        assertNotViolated(_cls_script_10.root.currentState);
    }

    @Test // A.D.E
    public void prop3_scen3() throws InterruptedException {
        assumeStartState(_cls_script_10.root.currentState);
        invoke(2000, A); // occurs at time = 2
        invoke(2000, D); // occurs at time = 4
        invoke(2000, E); // occurs at time = 6
        assertNotViolated(_cls_script_10.root.currentState);
        Thread.sleep(1100); // sleep until timeout exceeded to be sure
        assertNotViolated(_cls_script_10.root.currentState);
    }

    @Test // A.D.F
    public void prop3_scen4() throws InterruptedException {
        assumeStartState(_cls_script_10.root.currentState);
        invoke(2000, A); // occurs at time = 2
        invoke(2000, D); // occurs at time = 4
        invoke(2000, F); // occurs at time = 6
        assertNotViolated(_cls_script_10.root.currentState);
        Thread.sleep(1100); // sleep until timeout exceeded to be sure
        assertNotViolated(_cls_script_10.root.currentState);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* NON-DETERMINISTIC CLOCK RESET : [p] A.(C[2,4]) + A.(D[5,7]);                                                   */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // (8).A
    public void prop4_scen1() throws InterruptedException {
        assumeStartState(_cls_script_20.root.currentState);
        Thread.sleep(8000); // sleep until timeout exceeded
        assertNotViolated(_cls_script_20.root.currentState);
        // Not violated since clocks hadn't started yet
    }

    @Test // (2).A.C
    public void prop4_scen2() throws InterruptedException {
        assumeStartState(_cls_script_20.root.currentState);
        invoke(2000, A);
        assertNotViolated(_cls_script_20.root.currentState);
        invoke(C); // clock still at zero at this point
        assertViolated(_cls_script_20.root.currentState);
    }

    @Test // (5).A.D
    public void prop4_scen3() throws InterruptedException {
        assumeStartState(_cls_script_20.root.currentState);
        invoke(5000, A);
        assertNotViolated(_cls_script_20.root.currentState);
        invoke(D); // clock still at zero at this point
        assertViolated(_cls_script_20.root.currentState);
    }

    @Test // A.(2).C
    public void prop4_scen4() throws InterruptedException {
        assumeStartState(_cls_script_20.root.currentState);
        invoke(A);
        invoke(2000, C); // clock still at zero at this point
        assertNotViolated(_cls_script_20.root.currentState);
    }

    @Test // A.(5).D
    public void prop4_scen5() throws InterruptedException {
        assumeStartState(_cls_script_20.root.currentState);
        invoke(A);
        invoke(5000, D); // clock still at zero at this point
        assertNotViolated(_cls_script_20.root.currentState);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* HELPER METHOD                                                                                                  */
    /* -------------------------------------------------------------------------------------------------------------- */

    enum Actions {
        A, B, C, D, E, F
    }

    private void invoke(final long preDelay, final Actions toDo) throws InterruptedException {
        Thread.sleep(preDelay);
        invoke(toDo);
    }

    private void invoke(final Actions... toDo) {
        for (final Actions a : toDo) {
            switch (a) {
                case A:
                    ui.makeGoldUser();      // A = ui.makeGoldUser();
                    break;
                case B:
                    ui.makeSilverUser();    // B = ui.makeSilverUser();
                    break;
                case C:
                    us.openSession();       // C = us.openSession();
                    break;
                case D:
                    us.closeSession();      // D = us.closeSession();
                    break;
                case E:
                    ua.deposit(1000);       // E = ua.deposit(1000);
                    break;
                case F:
                    ua.withdraw(1000);      // F = ua.withdraw(1000);
                    break;
            }
        }
    }

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
