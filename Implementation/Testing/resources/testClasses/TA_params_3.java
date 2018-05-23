package transactionsystem;

import larva.RunningClock;
import larva._cls_script_11;
import larva._cls_script_12;
import larvaTools.LarvaController;
import org.junit.*;

public class TA_params_3 {

    private static final int START = 0;
    private static final int BAD = -1;
    private static final int ACCEPT = 1;

    private static final String USER_NAME = "name";
    private static final String USER_COUNTRY = "country";

    private static final boolean RE_TESTS = false; // should not be changed
    private static final boolean TA_TESTS = true;  // should not be changed

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
        new LarvaController().triggerReset();
    }

    @After
    public void tearDown() {
        _cls_script_11._cls_script_11_instances.clear();
        if (TA_TESTS) {
            _cls_script_12._cls_script_12_instances.clear();
        }
        synchronized (RunningClock.lock) {
            while (RunningClock.events.getNext() != null) {
                RunningClock.events.remove();
            }
        }
        new LarvaController().triggerStop();
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* PARAMETERIZED (WITH TIME INTERVAL)                                                                             */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // [p] deposit(ua).withdraw(ua)[2,4];
    public void prop5_scen1() {
        final UserAccount userAccount = new UserAccount(UID1, ANO1, new UserInfo(UID1, ANO1, CTR1));
        userAccount.deposit(AMT1);

        // <no wait>
        userAccount.withdraw(AMT1);
        assertOneInstance(_cls_script_11._cls_script_11_instances.size());
        assertViolated(_cls_script_11._cls_script_11_instances.keySet().iterator().next().currentState);
    }

    @Test // [p] deposit(ua).withdraw(ua)[2,4];
    public void prop5_scen2() throws InterruptedException {
        final UserAccount userAccount = new UserAccount(UID1, ANO1, new UserInfo(UID1, ANO1, CTR1));
        userAccount.deposit(AMT1);

        Thread.sleep(1000); // wait not enough
        userAccount.withdraw(AMT1);
        assertOneInstance(_cls_script_11._cls_script_11_instances.size());
        assertViolated(_cls_script_11._cls_script_11_instances.keySet().iterator().next().currentState);
    }

    @Test // [p] deposit(ua).withdraw(ua)[2,4];
    public void prop5_scen3() throws InterruptedException {
        final UserAccount userAccount = new UserAccount(UID1, ANO1, new UserInfo(UID1, ANO1, CTR1));
        userAccount.deposit(AMT1);

        Thread.sleep(4100); // wait too much
        assertOneInstance(_cls_script_11._cls_script_11_instances.size());
        assertViolated(_cls_script_11._cls_script_11_instances.keySet().iterator().next().currentState);
    }

    @Test // [p] deposit(ua).withdraw(ua)[2,4];
    public void prop5_scen4() throws InterruptedException {
        final UserAccount userAccount = new UserAccount(UID1, ANO1, new UserInfo(UID1, ANO1, CTR1));
        userAccount.deposit(AMT1);

        Thread.sleep(2000); // wait enough
        userAccount.withdraw(AMT1);
        assertOneInstance(_cls_script_11._cls_script_11_instances.size());
        assertNotViolated(_cls_script_11._cls_script_11_instances.keySet().iterator().next().currentState);
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
