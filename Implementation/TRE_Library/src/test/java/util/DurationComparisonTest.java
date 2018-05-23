package util;

import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;

public class DurationComparisonTest {

    private final Duration larger = Duration.ofSeconds(1);
    private final Duration smaller = Duration.ofSeconds(0);

    @Test
    public void max_returnsLargerDuration() {
        Assert.assertEquals(larger, DurationComparison.max(smaller, larger));
        Assert.assertEquals(larger, DurationComparison.max(larger, smaller));
    }

    @Test
    public void min_returnsSmallerDuration() {
        Assert.assertEquals(smaller, DurationComparison.min(smaller, larger));
        Assert.assertEquals(smaller, DurationComparison.min(larger, smaller));
    }

    @Test
    public void grtrEq_returnsTrueIfGreater() {
        Assert.assertTrue(DurationComparison.grtrEq(larger, smaller));
    }

    @Test
    public void grtrEq_returnsTrueIfEqual() {
        Assert.assertTrue(DurationComparison.grtrEq(smaller, smaller));
        Assert.assertTrue(DurationComparison.grtrEq(larger, larger));
    }

    @Test
    public void grtrEq_returnsFalseIfSmaller() {
        Assert.assertFalse(DurationComparison.grtrEq(smaller, larger));
    }

    @Test
    public void lessEq_returnsFalseIfGreater() {
        Assert.assertFalse(DurationComparison.lessEq(larger, smaller));
    }

    @Test
    public void lessEq_returnsTrueIfEqual() {
        Assert.assertTrue(DurationComparison.lessEq(smaller, smaller));
        Assert.assertTrue(DurationComparison.lessEq(larger, larger));
    }

    @Test
    public void lessEq_returnsTrueIfSmaller() {
        Assert.assertTrue(DurationComparison.lessEq(smaller, larger));
    }

    @Test
    public void grtrThan_returnsTrueIfGreater() {
        Assert.assertTrue(DurationComparison.grtrThan(larger, smaller));
    }

    @Test
    public void grtrThan_returnsFalseIfEqual() {
        Assert.assertFalse(DurationComparison.grtrThan(smaller, smaller));
        Assert.assertFalse(DurationComparison.grtrThan(larger, larger));
    }

    @Test
    public void grtrThan_returnsFalseIfSmaller() {
        Assert.assertFalse(DurationComparison.grtrThan(smaller, larger));
    }

    @Test
    public void lessThan_returnsFalseIfGreater() {
        Assert.assertFalse(DurationComparison.lessThan(larger, smaller));
    }

    @Test
    public void lessThan_returnsFalseIfEqual() {
        Assert.assertFalse(DurationComparison.lessThan(smaller, smaller));
        Assert.assertFalse(DurationComparison.lessThan(larger, larger));
    }

    @Test
    public void lessThan_returnsTrueIfSmaller() {
        Assert.assertTrue(DurationComparison.lessThan(smaller, larger));
    }
}