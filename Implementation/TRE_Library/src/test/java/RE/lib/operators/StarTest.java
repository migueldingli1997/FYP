package RE.lib.operators;

import RE.lib.RegExp;
import RE.lib.basic.EmptySet;
import RE.lib.basic.EmptyString;
import RE.lib.basic.Symbol;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class StarTest {

    @Mock
    private RegExp op; // operand
    @Mock
    private RegExp opNoEs; // operand with empty string removed
    @Mock
    private RegExp op_; // derivative of operand

    private static final Symbol DUMMY_SYMBOL = new Symbol("DUMMY_SYMBOL");
    private static final Duration DUMMY_DURATION = Duration.ofSeconds(5);

    @Test
    public void build_returnsEmptySetIfOperandIsEmptySet() { // 0* = 1
        Assert.assertEquals(EmptyString.INSTANCE, Star.build(EmptySet.INSTANCE));
    }

    @Test
    public void build_returnsEmptyStringIfOperandIsEmptyString() { // 1* = 1
        Assert.assertEquals(EmptyString.INSTANCE, Star.build(EmptyString.INSTANCE));
    }

    @Test
    public void build_cancelsExtraStarIfOperandIsStarOperator() {
        Assert.assertEquals(Star.build(op), Star.build(Star.build(op)));
    }

    @Test
    public void build_returnsStarOfOperand() { // Standard e*
        Assert.assertEquals(new Star(op), Star.build(op));
    }

    @Test
    public void getDerivative_wrtAnyTimeReturnsOrOfConcatenationAndEmptyString() { // d(e*) = e'e* + 1
        Mockito.when(op.getDerivative(DUMMY_DURATION)).thenReturn(op_);

        final RegExp expected = new Or(new Concat(op_, new Star(op)), EmptyString.INSTANCE);
        final RegExp residual = new Star(op).getDerivative(DUMMY_DURATION);
        Assert.assertEquals(expected, residual);
    }

    @Test
    public void getDerivative_wrtAnyTimeAndAnySymbolReturnsJustConcatenation() { // d(e*) = e'e*
        Mockito.when(op.getDerivative(DUMMY_SYMBOL, DUMMY_DURATION)).thenReturn(op_);

        final RegExp expected = new Concat(op_, new Star(op));
        final RegExp residual = new Star(op).getDerivative(DUMMY_SYMBOL, DUMMY_DURATION);
        Assert.assertEquals(expected, residual);
    }

    @Test
    public void hasEmptyString_returnsTrue() {
        Assert.assertTrue(new Star(op).hasEmptyString());
    }

    @Test
    public void isEmpty_returnsFalse() {
        Assert.assertFalse(new Star(op).isEmpty());
    }

    @Test
    public void removeEmptyString_returnsConcatenationOfOperandWithRemovedEmptyStringAndItself() {
        Mockito.when(op.removeEmptyString()).thenReturn(opNoEs);

        final Star before = new Star(op);
        final Concat after = new Concat(opNoEs, before);
        Assert.assertEquals(after, before.removeEmptyString());
    }

    @Test
    public void minTimeoutValue_returnsOperandTimeoutValue() {
        Mockito.when(op.minTimeoutValue()).thenReturn(Duration.ofSeconds(10));
        Assert.assertEquals(Duration.ofSeconds(10), new Star(op).minTimeoutValue());
    }

    @Test
    public void getFrontSymbols_returnsFirstSymbolsOfOperand() {

        final Symbol symbols[] = {new Symbol("sym1"), new Symbol("sym2"), new Symbol("sym3")};
        final LinkedHashSet<Symbol> dummyAlphabet = new LinkedHashSet<>(Arrays.asList(symbols[0], symbols[1], symbols[2]));
        Mockito.when(op.getFrontSymbols(dummyAlphabet)).thenReturn(new HashSet<>(Collections.singletonList(symbols[1])));

        final Set<Symbol> frontSymbols = new Star(op).getFrontSymbols(dummyAlphabet);
        Assert.assertEquals(1, frontSymbols.size());
        Assert.assertFalse(frontSymbols.contains(symbols[0]));
        Assert.assertTrue(frontSymbols.contains(symbols[1]));
        Assert.assertFalse(frontSymbols.contains(symbols[2]));
    }

    @Test
    public void equals_returnsTrueIfSameOperatorAndEqualOperand() {
        Assert.assertEquals(new Star(op), new Star(op));
    }

    @Test
    public void equals_returnsFalseIfDifferentOperatorOrUnequalOperand() {
        Assert.assertNotEquals(new Star(op), new Star(new Star(op)));
        Assert.assertNotEquals(new Star(op), new TimeInterval(op, Duration.ZERO, Duration.ofSeconds(1)));
    }
}