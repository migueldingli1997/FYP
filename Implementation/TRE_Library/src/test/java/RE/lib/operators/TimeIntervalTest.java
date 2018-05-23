package RE.lib.operators;

import RE.lib.RegExp;
import RE.lib.basic.EmptySet;
import RE.lib.basic.EmptyString;
import RE.lib.basic.Symbol;
import RE.lib.exceptions.NegativeTimeException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;
import java.util.*;

import static RE.lib.operators.TimeInterval.INFINITY;

@RunWith(MockitoJUnitRunner.class)
public class TimeIntervalTest {

    @Mock
    private RegExp op; // operand
    @Mock
    private RegExp opNoEs; // operand with empty string removed
    @Mock
    private RegExp op_; // derivative of operand
    @Mock
    private RegExp op_NoEs; // derivative of operand with empty string removed

    private static final Symbol DUMMY_SYMBOL = new Symbol("DUMMY_SYMBOL");
    private static final Duration MIN = Duration.ofSeconds(5);
    private static final Duration MAX = Duration.ofSeconds(10);

    @Test(expected = NegativeTimeException.class)
    public void build_throwsExceptionIfLowerBoundNegative() { // negative not allowed
        TimeInterval.build(op, Duration.ofSeconds(-1), MAX);
    }

    @Test(expected = NegativeTimeException.class)
    public void build_throwsExceptionIfUpperBoundNegative() { // negative not allowed
        TimeInterval.build(op, MIN, Duration.ofSeconds(-1));
    }

    @Test
    public void build_cancelsOutTimeIntervalIfLowerboundIsZeroAndOperandIsEmptyString() { // <1> = 1
        op = EmptyString.INSTANCE;
        Assert.assertEquals(EmptyString.INSTANCE, TimeInterval.build(op, Duration.ZERO, MAX));
    }

    @Test
    public void build_returnsEmptySetIfOperandIsEmptySet() { // <0> = 0
        op = EmptySet.INSTANCE;
        Assert.assertEquals(EmptySet.INSTANCE, TimeInterval.build(op, MIN, MAX));
    }

    @Test
    public void build_returnsEmptySetIfLowerBoundIsInfinite() { // <e>[∞,t'] = 0
        Assert.assertEquals(EmptySet.INSTANCE, TimeInterval.build(op, INFINITY, MAX));
    }

    @Test
    public void build_returnsEmptySetIfLowerBoundGreaterThanUpperBound() { // <e>[t,t'] = 0 (if t>=t')
        Assert.assertEquals(EmptySet.INSTANCE, TimeInterval.build(op, MAX, MIN));
    }

    @Test
    public void build_returnsEmptySetIfLowerBoundEqualToUpperBound() { // <e>[t,t'] = 0 (if t>=t')
        Assert.assertEquals(EmptySet.INSTANCE, TimeInterval.build(op, MIN, MIN));
        Assert.assertEquals(EmptySet.INSTANCE, TimeInterval.build(op, MAX, MAX));
    }

    @Test
    public void build_cancelsOutTimeIntervalIfLowerBoundZeroAndUpperBoundInfinite() { // <e>[0,∞] = e
        Assert.assertEquals(op, TimeInterval.build(op, Duration.ZERO, INFINITY));
    }

    @Test
    public void build_takesCommonIntervalIfOperandIsTimeInterval() { // <<e>[a,b]>[c,d] = <e>[max(a,c),min(b,d)]
        final Duration lessThanMin = MIN.plus(Duration.ofSeconds(1));
        final Duration lessThanMax = MAX.minus(Duration.ofSeconds(1));
        final Duration moreThanMin = MIN.plus(Duration.ofSeconds(1));
        final Duration moreThanMax = MAX.minus(Duration.ofSeconds(1));
        RegExp expected;

        // Inner interval wider
        op = new TimeInterval(DUMMY_SYMBOL, moreThanMin, lessThanMax);
        expected = new TimeInterval(DUMMY_SYMBOL, lessThanMin, moreThanMax);
        Assert.assertEquals(expected, TimeInterval.build(op, MIN, MAX));

        // Inner interval narrower
        op = new TimeInterval(DUMMY_SYMBOL, moreThanMin, lessThanMax);
        expected = new TimeInterval(DUMMY_SYMBOL, moreThanMin, lessThanMax);
        Assert.assertEquals(expected, TimeInterval.build(op, moreThanMin, lessThanMax));
    }

    @Test
    public void build_returns() { // Standard <e>[a,b]
        final RegExp expected = new TimeInterval(DUMMY_SYMBOL, MIN, MAX);
        Assert.assertEquals(expected, TimeInterval.build(DUMMY_SYMBOL, MIN, MAX));
    }

    @Test
    public void getDerivative_wrtTimeGreaterThanMaxReturnsEmptySetIfOperandHasNoEmptyString() {
        final Duration greaterThanMax = MAX.plus(Duration.ofSeconds(2));
        Mockito.when(op.hasEmptyString()).thenReturn(false);

        final RegExp residual = new TimeInterval(op, MIN, MAX).getDerivative(greaterThanMax);
        Assert.assertEquals(EmptySet.INSTANCE, residual);
    }

    @Test
    public void getDerivative_wrtTimeGreaterThanMaxReturnsEmptyStringIfOperandHasEmptyString() {
        final Duration greaterThanMax = MAX.plus(Duration.ofSeconds(2));
        Mockito.when(op.hasEmptyString()).thenReturn(true);

        final RegExp residual = new TimeInterval(op, MIN, MAX).getDerivative(greaterThanMax);
        Assert.assertEquals(EmptyString.INSTANCE, residual);
    }

    @Test
    public void getDerivative_wrtTimeEqualToMaxReturnsEmptySetIfOperandHasNoEmptyString() {
        Mockito.when(op.hasEmptyString()).thenReturn(false);

        final RegExp residual = new TimeInterval(op, MIN, MAX).getDerivative(MAX);
        Assert.assertEquals(EmptySet.INSTANCE, residual);
    }

    @Test
    public void getDerivative_wrtTimeEqualToMaxReturnsEmptyStringIfOperandHasEmptyString() {
        Mockito.when(op.hasEmptyString()).thenReturn(true);

        final RegExp residual = new TimeInterval(op, MIN, MAX).getDerivative(MAX);
        Assert.assertEquals(EmptyString.INSTANCE, residual);
    }

    @Test
    public void getDerivative_wrtTimeMoreThanMinDoesNotCauseNegativeLowerBound() {
        final Duration moreThanMin = MIN.plus(Duration.ofSeconds(1));
        Mockito.when(op.getDerivative(moreThanMin)).thenReturn(op_);

        final RegExp expected = new TimeInterval(op_, Duration.ZERO, MAX.minus(moreThanMin));
        final RegExp residual = new TimeInterval(op, MIN, MAX).getDerivative(moreThanMin);
        Assert.assertEquals(expected, residual);
    }

    @Test
    public void getDerivative_wrtTimeEqualToMinCausesZeroLowerBound() {
        final Duration equalToMin = MIN;
        Mockito.when(op.getDerivative(equalToMin)).thenReturn(op_);

        final RegExp expected = new TimeInterval(op_, Duration.ZERO, MAX.minus(equalToMin));
        final RegExp residual = new TimeInterval(op, MIN, MAX).getDerivative(equalToMin);
        Assert.assertEquals(expected, residual);
    }

    @Test
    public void getDerivative_wrtTimeLessThanMinReturnsUpdatedIntervalWithOperandDerivativeAndRemovedEmptyString() {
        final Duration lessThanMin = MIN.minus(Duration.ofSeconds(1));
        Mockito.when(op.getDerivative(lessThanMin)).thenReturn(op_);
        Mockito.when(op_.removeEmptyString()).thenReturn(op_NoEs);

        final RegExp expected = new TimeInterval(op_NoEs, MIN.minus(lessThanMin), MAX.minus(lessThanMin));
        final RegExp residual = new TimeInterval(op, MIN, MAX).getDerivative(lessThanMin);
        Assert.assertEquals(expected, residual);
    }

    @Test
    public void getDerivative_wrtAnyTimeWithInfiniteUpperBoundDoesNotModifyUpperBound() {
        final Duration timeValue = Duration.ofSeconds(1); // small timeValue
        final Duration infiniteMax = INFINITY; // MAX == infinity
        Mockito.when(op.getDerivative(timeValue)).thenReturn(op_);
        Mockito.when(op_.removeEmptyString()).thenReturn(op_NoEs);

        final RegExp expected = new TimeInterval(op_NoEs, MIN.minus(timeValue), INFINITY);
        final RegExp residual = new TimeInterval(op, MIN, infiniteMax).getDerivative(timeValue);
        Assert.assertEquals(expected, residual);
    }

    @Test
    public void getDerivative_wrtTimeEqualToMaxAndAnySymbolReturnsEmptySet() {
        final Duration equalToMin = MAX;

        final RegExp residual = new TimeInterval(op, MIN, MAX).getDerivative(DUMMY_SYMBOL, equalToMin);
        Assert.assertEquals(EmptySet.INSTANCE, residual);
    }

    @Test
    public void getDerivative_wrtTimeMoreThanMaxAndAnySymbolReturnsEmptySet() {
        final Duration moreThanMin = MAX;

        final RegExp residual = new TimeInterval(op, MIN, MAX).getDerivative(DUMMY_SYMBOL, moreThanMin);
        Assert.assertEquals(EmptySet.INSTANCE, residual);
    }

    @Test
    public void getDerivative_wrtTimeLessThanMinAndAnySymbolReturnsUpdatedIntervalWithOperandDerivativeAndRemovedEmptyString() {
        final Duration lessThanMin = MIN.minus(Duration.ofSeconds(1));
        Mockito.when(op.getDerivative(DUMMY_SYMBOL, lessThanMin)).thenReturn(op_);
        Mockito.when(op_.removeEmptyString()).thenReturn(op_NoEs);

        final RegExp expected = new TimeInterval(op_NoEs, MIN.minus(lessThanMin), MAX.minus(lessThanMin));
        final RegExp residual = new TimeInterval(op, MIN, MAX).getDerivative(DUMMY_SYMBOL, lessThanMin);
        Assert.assertEquals(expected, residual);
    }

    @Test
    public void getDerivative_wrtTimeMoreThanMinAndAnySymbolDoesNotCauseNegativeLowerBound() {
        final Duration moreThanMin = MIN.plus(Duration.ofSeconds(1));
        Mockito.when(op.getDerivative(DUMMY_SYMBOL, moreThanMin)).thenReturn(op_);

        final RegExp expected = new TimeInterval(op_, Duration.ZERO, MAX.minus(moreThanMin));
        final RegExp residual = new TimeInterval(op, MIN, MAX).getDerivative(DUMMY_SYMBOL, moreThanMin);
        Assert.assertEquals(expected, residual);
    }

    @Test
    public void getDerivative_wrtTimeEqualToMinAndAnySymbolCausesZeroLowerBound() {
        final Duration equalToMin = MIN;
        Mockito.when(op.getDerivative(DUMMY_SYMBOL, equalToMin)).thenReturn(op_);

        final RegExp expected = new TimeInterval(op_, Duration.ZERO, MAX.minus(equalToMin));
        final RegExp residual = new TimeInterval(op, MIN, MAX).getDerivative(DUMMY_SYMBOL, equalToMin);
        Assert.assertEquals(expected, residual);
    }

    @Test
    public void getDerivative_wrtTimeMoreThanMinAndAnySymbolWithInfiniteUpperBoundCancelsOutInterval() {
        final Duration timeValue = MIN.plus(Duration.ofSeconds(1));
        final Duration infiniteMax = INFINITY; // MAX == infinity
        Mockito.when(op.getDerivative(DUMMY_SYMBOL, timeValue)).thenReturn(op_);

        final RegExp residual = new TimeInterval(op, MIN, infiniteMax).getDerivative(DUMMY_SYMBOL, timeValue);
        Assert.assertEquals(op_, residual);
    }

    @Test
    public void hasEmptyString_returnsFalseIfOperandNonZeroLowerboundEvenIfHasEmptyString() {
        Mockito.when(op.hasEmptyString()).thenReturn(true);
        Assert.assertFalse(new TimeInterval(op, MIN, MAX).hasEmptyString());
    }

    @Test
    public void hasEmptyString_returnsFalseIfOperandHasZeroLowerboundButHasNoEmptyString() {
        Mockito.when(op.hasEmptyString()).thenReturn(false);
        Assert.assertFalse(new TimeInterval(op, Duration.ZERO, MAX).hasEmptyString());
    }

    @Test
    public void hasEmptyString_returnsTrueIfOperandHasZeroLowerboundAndHasEmptyString() {
        Mockito.when(op.hasEmptyString()).thenReturn(true);
        Assert.assertTrue(new TimeInterval(op, Duration.ZERO, MAX).hasEmptyString());
    }

    @Test
    public void isEmpty_returnsTrueIfIntervalLowerBoundIsInfinite() {
        Assert.assertTrue(new TimeInterval(op, INFINITY, MAX).isEmpty());
    }

    @Test
    public void isEmpty_returnsTrueIfIntervalBoundsAreEqual() {
        Assert.assertTrue(new TimeInterval(op, MIN, MIN).isEmpty());
        Assert.assertTrue(new TimeInterval(op, MAX, MAX).isEmpty());
    }

    @Test
    public void isEmpty_returnsTrueIfMinNotZeroAndOperandWithoutEmptyStringIsEmpty() {
        Mockito.when(op.removeEmptyString()).thenReturn(opNoEs);
        Mockito.when(opNoEs.isEmpty()).thenReturn(true);
        Assert.assertTrue(new TimeInterval(op, MIN, MAX).isEmpty());
    }

    @Test
    public void isEmpty_returnsTrueIfMinZeroAndOperandIsEmpty() {
        Mockito.when(op.isEmpty()).thenReturn(true);
        Assert.assertTrue(new TimeInterval(op, Duration.ZERO, MAX).isEmpty());
    }

    @Test
    public void isEmpty_returnsFalseIfMinNotZeroAndOperandWithoutEmptyStringIsNotEmpty() {
        Mockito.when(op.removeEmptyString()).thenReturn(opNoEs);
        Mockito.when(opNoEs.isEmpty()).thenReturn(false);
        Assert.assertFalse(new TimeInterval(op, MIN, MAX).isEmpty());
    }

    @Test
    public void isEmpty_returnsFalseIfMinZeroAndOperandIsNotEmpty() {
        Mockito.when(op.isEmpty()).thenReturn(false);
        Assert.assertFalse(new TimeInterval(op, Duration.ZERO, MAX).isEmpty());
    }

    @Test
    public void removeEmptyString_removesEmptyStringFromOperand() {
        Mockito.when(op.removeEmptyString()).thenReturn(opNoEs);

        final TimeInterval before = new TimeInterval(op, MIN, MAX);
        final TimeInterval after = new TimeInterval(opNoEs, MIN, MAX);
        Assert.assertEquals(after, before.removeEmptyString());
    }

    @Test
    public void minTimeoutValue_returnsMinimumBetweenUpperBoundAndTimeoutValueOfOperand() {
        final Duration greaterDuration = MAX.plus(Duration.ofSeconds(2));
        final Duration smallerDuration = MAX.minus(Duration.ofSeconds(2));

        Mockito.when(op.minTimeoutValue()).thenReturn(greaterDuration);
        Assert.assertEquals(MAX, new TimeInterval(op, MIN, MAX).minTimeoutValue());

        Mockito.when(op.minTimeoutValue()).thenReturn(smallerDuration);
        Assert.assertEquals(smallerDuration, new TimeInterval(op, MIN, MAX).minTimeoutValue());
    }

    @Test
    public void getFrontSymbols_returnsFirstSymbolsOfOperand() {

        final Symbol symbols[] = {new Symbol("sym1"), new Symbol("sym2"), new Symbol("sym3")};
        final LinkedHashSet<Symbol> dummyAlphabet = new LinkedHashSet<>(Arrays.asList(symbols[0], symbols[1], symbols[2]));
        Mockito.when(op.getFrontSymbols(dummyAlphabet)).thenReturn(new HashSet<>(Collections.singletonList(symbols[1])));

        final Set<Symbol> frontSymbols = new TimeInterval(op, MIN, MAX).getFrontSymbols(dummyAlphabet);
        Assert.assertEquals(1, frontSymbols.size());
        Assert.assertFalse(frontSymbols.contains(symbols[0]));
        Assert.assertTrue(frontSymbols.contains(symbols[1]));
        Assert.assertFalse(frontSymbols.contains(symbols[2]));
    }

    @Test
    public void equals_returnsTrueIfSameOperatorAndEqualOperandsAndLimits() {
        final Duration minCopy = Duration.ofMillis(MIN.toMillis());
        final Duration maxCopy = Duration.ofMillis(MAX.toMillis());
        Assert.assertEquals(new TimeInterval(op, MIN, MAX), new TimeInterval(op, minCopy, maxCopy));
    }

    @Test
    public void equals_returnsFalseIfDifferentOperatorOrUnequalOperandsOrUnequalLimits() {
        Assert.assertNotEquals(new TimeInterval(op, MIN, MAX), new Star(op));
        Assert.assertNotEquals(new TimeInterval(op, MIN, MAX), new TimeInterval(op, MAX, MIN));
        Assert.assertNotEquals(new TimeInterval(op, MIN, MAX), new TimeInterval(new Star(op), MIN, MAX));
    }
}