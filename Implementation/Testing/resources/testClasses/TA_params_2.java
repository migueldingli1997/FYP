package transactionsystem;

import larva.RunningClock;
import larva._cls_script_11;
import larva._cls_script_12;
import larva._cls_script_13;
import larvaTools.LarvaController;
import org.junit.*;

public class TA_params_2 {

    private static final int START = 0;
    private static final int BAD = -1;
    private static final int ACCEPT = 1;

    private static final String USER_NAME = "name";
    private static final String USER_COUNTRY = "country";

    private Interface tr;
    private static final boolean RE_TESTS = false; // should not be changed
    private static final boolean TA_TESTS = true; // should not be changed

    private static final int UID1 = 1;
    private static final int UID2 = 2;
    private static final String ANO1 = "1";
    private static final String ANO2 = "2";
    private static final String CTR1 = "x";
    private static final String CTR2 = "y";
    private static final int AMT1 = 1;
    private static final int AMT2 = 2;

    @Before
    public void setUp() {
        tr = new Interface();
        new LarvaController().triggerReset();
    }

    @After
    public void tearDown() {
        _cls_script_11._cls_script_11_instances.clear();
        _cls_script_12._cls_script_12_instances.clear();
        if (TA_TESTS) {
            _cls_script_13._cls_script_13_instances.clear();
        }
        synchronized (RunningClock.lock) {
            while (RunningClock.events.getNext() != null) {
                RunningClock.events.remove();
            }
        }
        new LarvaController().triggerStop();
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* PARAMETERIZED (MIXED PARAMETERS)                                                                               */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // [p] deposit(ui,ua).initialise.openSession(ui).withdraw(ui,ua);
    public void prop4_scen1() {
        final UserAccount userAccount = new UserAccount(UID1, ANO1, new UserInfo(UID1, ANO1, CTR1));

        // No violation after a deposit
        userAccount.deposit(AMT1);
        assertOneInstance(_cls_script_11._cls_script_11_instances.size());
        assertOneInstance(_cls_script_12._cls_script_12_instances.size());
        assertNotViolated(_cls_script_12._cls_script_12_instances.keySet().iterator().next().currentState);

        // No violation after an initialise
        tr.ADMIN_initialise();
        assertOneInstance(_cls_script_11._cls_script_11_instances.size());
        assertOneInstance(_cls_script_12._cls_script_12_instances.size());
        assertNotViolated(_cls_script_12._cls_script_12_instances.keySet().iterator().next().currentState);

        // No violation after a session open
        userAccount.getOwnerInfo().openSession();
        assertOneInstance(_cls_script_11._cls_script_11_instances.size());
        assertOneInstance(_cls_script_12._cls_script_12_instances.size());
        assertNotViolated(_cls_script_12._cls_script_12_instances.keySet().iterator().next().currentState);

        // No violation after a withdrawal
        userAccount.withdraw(AMT1);
        assertOneInstance(_cls_script_11._cls_script_11_instances.size());
        assertOneInstance(_cls_script_12._cls_script_12_instances.size());
        assertNotViolated(_cls_script_12._cls_script_12_instances.keySet().iterator().next().currentState);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* HELPER METHODS                                                                                                 */
    /* -------------------------------------------------------------------------------------------------------------- */

    private void assertOneInstance(final int numberOfInstances) {
        Assert.assertEquals(1, numberOfInstances);
    }

    private void assertTwoInstance(final int numberOfInstances) {
        Assert.assertEquals(2, numberOfInstances);
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
