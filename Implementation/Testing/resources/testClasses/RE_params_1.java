package transactionsystem;

import RE.lib.basic.EmptyString;
import larva.RunningClock;
import larvaTools.LarvaController;
import org.junit.*;
import larva.*;

import java.util.Iterator;

public class RE_params_1 {

    private static final int START = 0;
    private static final int BAD = -1;
    private static final int ACCEPT = 1;

    private static final String USER_NAME = "name";
    private static final String USER_COUNTRY = "country";

    private Interface tr;
    private static final boolean RE_TESTS = true;  // should not be changed
    private static final boolean TA_TESTS = false; // should not be changed

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
        _cls_script_21._cls_script_21_instances.clear();
        _cls_script_22._cls_script_22_instances.clear();
        _cls_script_31._cls_script_31_instances.clear();
        synchronized (RunningClock.lock) {
            while (RunningClock.events.getNext() != null) {
                RunningClock.events.remove();
            }
        }
        new LarvaController().triggerStop();
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* PARAMETERIZED (SINGLE PARAMETER)                                                                               */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // [p] deposit(ua); {withdraw(ua)}
    public void prop1_scen1() {
        final UserAccount userAccount = new UserAccount(UID1, ANO1, new UserInfo(UID1, ANO1, CTR1));

        userAccount.deposit(AMT1);
        assertOneInstance(_cls_script_11._cls_script_11_instances.size());
        assertNotViolated(_cls_script_11._cls_script_11_instances.keySet().iterator().next().currentState);
    }

    @Test // [p] deposit(ua); {withdraw(ua)}
    public void prop1_scen2() {
        final UserAccount userAccount = new UserAccount(UID1, ANO1, new UserInfo(UID1, ANO1, CTR1));

        userAccount.withdraw(AMT1);
        assertOneInstance(_cls_script_11._cls_script_11_instances.size());
        assertViolated(_cls_script_11._cls_script_11_instances.keySet().iterator().next().currentState);
    }

    @Test // [p] deposit(ua); {withdraw(ua)}
    public void prop1_scen3() {
        final UserAccount userAccount1 = new UserAccount(UID1, ANO1, new UserInfo(UID1, ANO1, CTR1));
        final UserAccount userAccount2 = new UserAccount(UID2, ANO2, new UserInfo(UID2, ANO2, CTR2));
        Iterator<_cls_script_11> iter;

        // User 1 deposit
        userAccount1.deposit(AMT1);
        assertOneInstance(_cls_script_11._cls_script_11_instances.size());
        iter = _cls_script_11._cls_script_11_instances.keySet().iterator();
        assertNotViolated(iter.next().currentState);

        // User 2 deposit
        userAccount2.deposit(AMT2);
        assertTwoInstance(_cls_script_11._cls_script_11_instances.size());
        iter = _cls_script_11._cls_script_11_instances.keySet().iterator();
        assertNotViolated(iter.next().currentState);
        assertNotViolated(iter.next().currentState);

        // Assert that expressions are now "1"
        if (RE_TESTS) {
            iter = _cls_script_11._cls_script_11_instances.keySet().iterator();
            Assert.assertEquals(EmptyString.INSTANCE, iter.next().reh.getRegExp());
            Assert.assertEquals(EmptyString.INSTANCE, iter.next().reh.getRegExp());
        }

        // User 1 withdraw
        userAccount1.withdraw(AMT1);
        assertTwoInstance(_cls_script_11._cls_script_11_instances.size());
        iter = _cls_script_11._cls_script_11_instances.keySet().iterator();
        assertViolated(iter.next().currentState);
        assertNotViolated(iter.next().currentState);

        // User 2 withdraw
        userAccount2.withdraw(AMT1);
        assertTwoInstance(_cls_script_11._cls_script_11_instances.size());
        iter = _cls_script_11._cls_script_11_instances.keySet().iterator();
        assertViolated(iter.next().currentState);
        assertViolated(iter.next().currentState);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* PARAMETERIZED (TWO PARAMETERS)                                                                                 */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // [p] deposit(ui,ua); {withdraw(ui,ua)}
    public void prop2_scen1() {
        final UserAccount userAccount = new UserAccount(UID1, ANO1, new UserInfo(UID1, ANO1, CTR1));

        userAccount.deposit(AMT1);
        assertOneInstance(_cls_script_21._cls_script_21_instances.size());
        assertOneInstance(_cls_script_22._cls_script_22_instances.size());
        assertNotViolated(_cls_script_22._cls_script_22_instances.keySet().iterator().next().currentState);
    }

    @Test // [p] deposit(ui,ua); {withdraw(ui,ua)}
    public void prop2_scen2() {
        final UserAccount userAccount = new UserAccount(UID1, ANO1, new UserInfo(UID1, ANO1, CTR1));

        userAccount.withdraw(AMT1);
        assertOneInstance(_cls_script_21._cls_script_21_instances.size());
        assertOneInstance(_cls_script_22._cls_script_22_instances.size());
        assertViolated(_cls_script_22._cls_script_22_instances.keySet().iterator().next().currentState);
    }

    /* -------------------------------------------------------------------------------------------------------------- */
    /* PARAMETERIZED (CONCATENATION)                                                                                  */
    /* -------------------------------------------------------------------------------------------------------------- */

    @Test // [p] deposit(ua).withdraw(ua);
    public void prop3_scen1() {
        final UserAccount userAccount = new UserAccount(UID1, ANO1, new UserInfo(UID1, ANO1, CTR1));

        // No violation after deposit
        userAccount.deposit(AMT1);
        assertOneInstance(_cls_script_31._cls_script_31_instances.size());
        assertNotViolated(_cls_script_31._cls_script_31_instances.keySet().iterator().next().currentState);

        // No violation after withdrawal
        userAccount.withdraw(AMT1);
        assertOneInstance(_cls_script_31._cls_script_31_instances.size());
        assertNotViolated(_cls_script_31._cls_script_31_instances.keySet().iterator().next().currentState);

        // Assert that expression is now "1"
        if (RE_TESTS) {
            Assert.assertEquals(EmptyString.INSTANCE,
                    _cls_script_31._cls_script_31_instances.keySet().iterator().next().reh.getRegExp());
        }
    }

    @Test // [p] deposit(ua).withdraw(ua);
    public void prop3_scen2() {
        final UserAccount userAccount = new UserAccount(UID1, ANO1, new UserInfo(UID1, ANO1, CTR1));

        userAccount.withdraw(AMT1);
        assertOneInstance(_cls_script_31._cls_script_31_instances.size());
        assertViolated(_cls_script_31._cls_script_31_instances.keySet().iterator().next().currentState);
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
