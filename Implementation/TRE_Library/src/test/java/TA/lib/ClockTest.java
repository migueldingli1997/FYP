package TA.lib;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;

public class ClockTest {

    private Clock clock;

    @Before
    public void setUp() {
        clock = new Clock();
    }

    @After
    public void tearDown() {
        clock = null;
    }

    @Test
    public void defaultIdAndTimeout() {
        Assert.assertEquals(-1, clock.id);
        Assert.assertEquals(null, clock.timeout);
    }

    @Test
    public void setTimeout_setsTimeoutCorrectly() {
        clock.setTimeout(Duration.ofSeconds(5));
        Assert.assertEquals(Duration.ofSeconds(5), clock.timeout);
    }

    @Test
    public void setTimeout_onlyAllowsSettingTimeoutOnce() {
        clock.setTimeout(Duration.ofSeconds(5));
        clock.setTimeout(Duration.ofSeconds(3));
        clock.setTimeout(Duration.ofSeconds(7));
        Assert.assertEquals(Duration.ofSeconds(5), clock.timeout);
    }

    @Test
    public void hasTimeout_falseByDefault() {
        Assert.assertFalse(clock.hasTimeout());
    }

    @Test
    public void hasTimeout_trueIfTimeoutSet() {
        clock.timeout = Duration.ofSeconds(5);
        Assert.assertTrue(clock.hasTimeout());
    }

    @Test(expected = NullPointerException.class)
    public void getTimeoutInMillis_throwsExceptionIfTimeoutNotSet() {
        clock.getTimeoutInMillis();
    }

    @Test
    public void getTimeoutInMillis_returnsCorrectValue() {
        clock.timeout = Duration.ofSeconds(5);
        Assert.assertEquals(5 * 1000, clock.getTimeoutInMillis());
    }

    @Test
    public void toString_doesNotAddClockPrefixIfDefaultId() {
        Assert.assertNotEquals("clock" + clock.id, clock.toString());
    }

    @Test
    public void toString_addsClockPrefixIfNotDefaultId() {
        clock.id = 1;
        Assert.assertEquals("clock" + clock.id, clock.toString());
    }
}