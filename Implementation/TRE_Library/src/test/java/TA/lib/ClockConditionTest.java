package TA.lib;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.function.Function;

public class ClockConditionTest {

    private static final Function<Clock, String> clockToStringFn = clock -> "clock_" + clock.id;

    private final Duration MIN = Duration.ofSeconds(5);
    private final Duration MAX = Duration.ofSeconds(10);
    private final Duration EQUAL = Duration.ofSeconds(7);
    private final Duration ZERO = Duration.ZERO;
    private final Duration INFINITY = ClockCondition.INFINITY;
    private final Clock CLOCK1 = new Clock();
    private final Clock CLOCK2 = new Clock();
    private final Clock CLOCK3 = new Clock();
    private final Clock CLOCK4 = new Clock();

    private ClockCondition cc1_1Arg;
    private ClockCondition cc2_2Args_integers;
    private ClockCondition cc3_zeroMin;
    private ClockCondition cc4_infMax;

    @Before
    public void setUp() {
        cc1_1Arg = new ClockCondition(CLOCK1, EQUAL);
        cc2_2Args_integers = new ClockCondition(CLOCK2, MIN, MAX);
        cc3_zeroMin = new ClockCondition(CLOCK3, ZERO, MAX);
        cc4_infMax = new ClockCondition(CLOCK4, MIN, INFINITY);
    }

    @After
    public void tearDown() {
        cc1_1Arg = null;
        cc2_2Args_integers = null;
        cc3_zeroMin = null;
        cc4_infMax = null;
    }

    @Test
    public void constructor_setsClockCorrectly() {
        Assert.assertEquals(CLOCK1, cc1_1Arg.clock);
        Assert.assertEquals(CLOCK2, cc2_2Args_integers.clock);
        Assert.assertEquals(CLOCK3, cc3_zeroMin.clock);
        Assert.assertEquals(CLOCK4, cc4_infMax.clock);
    }

    @Test
    public void constructor_setsUniqueMinMaxCorrectly() {
        Assert.assertEquals(MIN, cc2_2Args_integers.min);
        Assert.assertEquals(MAX, cc2_2Args_integers.max);
        Assert.assertEquals(ZERO, cc3_zeroMin.min);
        Assert.assertEquals(MAX, cc3_zeroMin.max);
        Assert.assertEquals(MIN, cc4_infMax.min);
        Assert.assertEquals(INFINITY, cc4_infMax.max);
    }

    @Test
    public void constructor_setsMinMaxEquallyIfOnlyOneArg() {
        Assert.assertEquals(EQUAL, cc1_1Arg.min);
        Assert.assertEquals(EQUAL, cc1_1Arg.max);
    }

    @Test
    public void constructor_setsTimeoutOfClockToMaxIfNotInfinity() {
        Assert.assertEquals(EQUAL, cc1_1Arg.clock.timeout);
        Assert.assertEquals(MAX, cc2_2Args_integers.clock.timeout);
        Assert.assertEquals(MAX, cc3_zeroMin.clock.timeout);
    }

    @Test
    public void constructor_doesNotSetTimeoutOfClockIfInfinityMax() {
        Assert.assertNull(cc4_infMax.clock.timeout);
    }

    @Test
    public void getClock_returnsClock() {
        Assert.assertEquals(CLOCK1, cc1_1Arg.getClock());
        Assert.assertEquals(CLOCK2, cc2_2Args_integers.getClock());
        Assert.assertEquals(CLOCK3, cc3_zeroMin.getClock());
        Assert.assertEquals(CLOCK4, cc4_infMax.getClock());
    }

    @Test
    public void toJavaCondition_returnsEmptyStringForEqualMinAndMax() {
        Assert.assertEquals("", cc1_1Arg.toJavaCondition(clockToStringFn));
    }

    @Test
    public void toJavaCondition_returnsTwoInequalitiesForUniqueMinAndMax() {
        final String clkStr = clockToStringFn.apply(CLOCK2);
        final String minStr = cc2_2Args_integers.minString; // assumes minString correct
        final String maxStr = cc2_2Args_integers.maxString; // assumes maxString correct
        final String expect = String.format("%s.current() >= %s && %s.current() < %s", clkStr, minStr, clkStr, maxStr);
        Assert.assertEquals(expect, cc2_2Args_integers.toJavaCondition(clockToStringFn));
    }

    @Test
    public void toJavaCondition_returnsLessThanIfZeroMin() {
        final String clkStr = clockToStringFn.apply(CLOCK3);
        final String maxStr = cc3_zeroMin.maxString; // assumes maxString correct
        final String expect = String.format("%s.current() < %s", clkStr, maxStr);
        Assert.assertEquals(expect, cc3_zeroMin.toJavaCondition(clockToStringFn));
    }

    @Test
    public void toJavaCondition_returnsGreaterOrEqualIfInfinityMax() {
        final String clkStr = clockToStringFn.apply(CLOCK4);
        final String minStr = cc4_infMax.minString; // assumes minString correct
        final String expect = String.format("%s.current() >= %s", clkStr, minStr);
        Assert.assertEquals(expect, cc4_infMax.toJavaCondition(clockToStringFn));
    }

    @Test
    public void toString_returnsEmptyStringForEqualMinAndMax() {
        final String clkStr = CLOCK1.toString();
        final String minStr = cc1_1Arg.min.toString();
        final String maxStr = cc1_1Arg.max.toString();
        Assert.assertEquals(String.format("%s=%s", clkStr, minStr), cc1_1Arg.toString());
        Assert.assertEquals(String.format("%s=%s", clkStr, maxStr), cc1_1Arg.toString());
    }

    @Test
    public void toString_returnsTwoInequalitiesForUniqueMinAndMax() {
        final String clkStr = CLOCK2.toString();
        final String minStr = cc2_2Args_integers.min.toString();
        final String maxStr = cc2_2Args_integers.max.toString();
        Assert.assertEquals(String.format("%s<=%s<%s", minStr, clkStr, maxStr), cc2_2Args_integers.toString());
    }

    @Test
    public void toString_returnsLessThanIfZeroMin() {
        final String clkStr = CLOCK3.toString();
        final String maxStr = cc3_zeroMin.max.toString();
        Assert.assertEquals(String.format("%s<%s", clkStr, maxStr), cc3_zeroMin.toString());
    }

    @Test
    public void toString_returnsGreaterOrEqualIfInfinityMax() {
        final String clkStr = CLOCK4.toString();
        final String minStr = cc4_infMax.min.toString();
        Assert.assertEquals(String.format("%s>=%s", clkStr, minStr), cc4_infMax.toString());
    }

    @Test
    public void minMaxString_fractionalRemovedIfIntegerNumberOfSeconds() {
        final ClockCondition cc = new ClockCondition(new Clock(), Duration.ofSeconds(1));
        Assert.assertEquals("1", cc.minString);
        Assert.assertEquals("1", cc.maxString);
    }

    @Test
    public void minMaxString_fractionalLeftIfNonIntegerNumberOfSeconds() {
        final ClockCondition cc = new ClockCondition(new Clock(), Duration.ofSeconds(1).plus(Duration.ofMillis(1)));
        Assert.assertEquals("1.001", cc.minString);
        Assert.assertEquals("1.001", cc.maxString);
    }
}