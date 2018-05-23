package transactionsystem;

import larva.*;
import larvaTools.LarvaController;
import org.junit.*;

import java.util.LinkedHashMap;

import static transactionsystem.TA_nondet_1.Actions.*;

public class TA_nondet_1 {

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
    /* GOOD BEHAVIOUR : [p] A.(C.E + C.F) + A.(D.E + D.F) + B;                                                        */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // A.C.E
    public void prop1_scen1() {
        assumeStartState(_cls_script_10.root.currentState);
        invoke(A, C, E);
        assertNotViolated(_cls_script_10.root.currentState);
    }

    @Test // A.C.F
    public void prop1_scen2() {
        assumeStartState(_cls_script_10.root.currentState);
        invoke(A, C, F);
        assertNotViolated(_cls_script_10.root.currentState);
    }

    @Test // A.D.E
    public void prop1_scen3() {
        assumeStartState(_cls_script_10.root.currentState);
        invoke(A, D, E);
        assertNotViolated(_cls_script_10.root.currentState);
    }

    @Test // A.D.F
    public void prop1_scen4() {
        assumeStartState(_cls_script_10.root.currentState);
        invoke(A, D, F);
        assertNotViolated(_cls_script_10.root.currentState);
    }

    @Test // B
    public void prop1_scen5() {
        assumeStartState(_cls_script_10.root.currentState);
        invoke(B);
        assertNotViolated(_cls_script_10.root.currentState);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* BAD BEHAVIOUR : [n] A.(C.E + C.F) + A.(D.E + D.F) + B;                                                         */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // A.C.E
    public void prop2_scen1() {
        assumeStartState(_cls_script_20.root.currentState);
        invoke(A, C);
        assertNotViolated(_cls_script_20.root.currentState);
        invoke(E);
        assertViolated(_cls_script_20.root.currentState);
    }

    @Test // A.C.F
    public void prop2_scen2() {
        assumeStartState(_cls_script_20.root.currentState);
        invoke(A, C);
        assertNotViolated(_cls_script_20.root.currentState);
        invoke(F);
        assertViolated(_cls_script_20.root.currentState);
    }

    @Test // A.D.E
    public void prop2_scen3() {
        assumeStartState(_cls_script_20.root.currentState);
        invoke(A, D);
        assertNotViolated(_cls_script_20.root.currentState);
        invoke(E);
        assertViolated(_cls_script_20.root.currentState);
    }

    @Test // A.D.F
    public void prop2_scen4() {
        assumeStartState(_cls_script_20.root.currentState);
        invoke(A, D);
        assertNotViolated(_cls_script_20.root.currentState);
        invoke(F);
        assertViolated(_cls_script_20.root.currentState);
    }

    @Test // B.C.E
    public void prop2_scen5() {
        assumeStartState(_cls_script_20.root.currentState);
        invoke(B);
        assertViolated(_cls_script_20.root.currentState);
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
