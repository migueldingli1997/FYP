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
public class ConcatTest {

    @Mock
    private RegExp op1; // first operand
    @Mock
    private RegExp op2; // second operand
    @Mock
    private RegExp op1NoEs; // first operand with empty string removed
    @Mock
    private RegExp op2NoEs; // second operand with empty string removed
    @Mock
    private RegExp op1_; // derivative of first operand
    @Mock
    private RegExp op2_; // derivative of second operand

    private static final Symbol DUMMY_SYMBOL = new Symbol("DUMMY_SYMBOL");
    private static final Duration DUMMY_DURATION = Duration.ofSeconds(5);

    @Test
    public void build_returnsEmptySetIfOnlyOneOperandEmpty() { // e0 = 0e = 0
        Mockito.when(op1.isEmpty()).thenReturn(true);
        Mockito.when(op2.isEmpty()).thenReturn(false);

        Assert.assertEquals(EmptySet.INSTANCE, Concat.build(op1, op2));
        Assert.assertEquals(EmptySet.INSTANCE, Concat.build(op2, op1));
    }

    @Test
    public void build_returnsEmptySetIfBothOperandsEmpty() { // 00 = 0
        Mockito.when(op1.isEmpty()).thenReturn(true);
        Mockito.when(op2.isEmpty()).thenReturn(false);

        Assert.assertEquals(EmptySet.INSTANCE, Concat.build(op1, op2));
    }

    @Test
    public void build_returnsOtherOperandIfOnlyOneOperandIsEmptyString() { // e1 = 1e = e
        op1 = EmptyString.INSTANCE;
        Mockito.when(op2.isEmpty()).thenReturn(false);

        Assert.assertEquals(op2, Concat.build(op1, op2));
        Assert.assertEquals(op2, Concat.build(op2, op1));
    }

    @Test
    public void build_returnsEmptyStringIfBothOperandsAreEmptyString() { // 11 = 1
        op1 = EmptyString.INSTANCE;
        op2 = EmptyString.INSTANCE;

        Assert.assertEquals(EmptyString.INSTANCE, Concat.build(op1, op2));
    }

    @Test
    public void build_returnsConcatenationOfOperands() { // Standard e1e2
        Mockito.when(op1.isEmpty()).thenReturn(false);
        Mockito.when(op2.isEmpty()).thenReturn(false);

        Assert.assertEquals(new Concat(op1, op2), Concat.build(op1, op2));
    }

    @Test
    public void getDerivative_wrtAnyTimeReturnsOrIfFirstOperandContainsEmptyString() { // d(e1e2) = e1'e2 + e2' (if 1 in e1)
        Mockito.when(op1.hasEmptyString()).thenReturn(true);
        Mockito.when(op1.getDerivative(DUMMY_DURATION)).thenReturn(op1_);
        Mockito.when(op2.getDerivative(DUMMY_DURATION)).thenReturn(op2_);

        final RegExp expected = new Or(new Concat(op1_, op2), op2_);
        final RegExp residual = new Concat(op1, op2).getDerivative(DUMMY_DURATION);
        Assert.assertEquals(expected, residual);
    }

    @Test
    public void getDerivative_wrtAnyTimeAppliesOnlyToFirstOperandIfItHasNoEmptyString() { // d(e1e2) = e1'e2 (if 1 not in e1)
        Mockito.when(op1.hasEmptyString()).thenReturn(false);
        Mockito.when(op1.getDerivative(DUMMY_DURATION)).thenReturn(op1_);
        Mockito.when(op2.getDerivative(DUMMY_DURATION)).thenReturn(op2_);

        final RegExp expected = new Concat(op1_, op2);
        final RegExp residual = new Concat(op1, op2).getDerivative(DUMMY_DURATION);
        Assert.assertEquals(expected, residual);
    }

    @Test
    public void getDerivative_wrtAnyTimeAndAnySymbolReturnsOrIfFirstOperandContainsEmptyString() { // d(e1e2) = e1'e2 + e2' (if 1 in e1)
        Mockito.when(op1.hasEmptyString()).thenReturn(true);
        Mockito.when(op1.getDerivative(DUMMY_SYMBOL, DUMMY_DURATION)).thenReturn(op1_);
        Mockito.when(op2.getDerivative(DUMMY_SYMBOL, DUMMY_DURATION)).thenReturn(op2_);

        final RegExp expected = new Or(new Concat(op1_, op2), op2_);
        Assert.assertEquals(expected, new Concat(op1, op2).getDerivative(DUMMY_SYMBOL, DUMMY_DURATION));
    }

    @Test
    public void getDerivative_wrtAnyTimeAndAnySymbolAppliesOnlyToFirstOperandIfItHasNoEmptyString() { // d(e1e2) = e1'e2 (if 1 not in e1)
        Mockito.when(op1.hasEmptyString()).thenReturn(false);
        Mockito.when(op1.getDerivative(DUMMY_SYMBOL, DUMMY_DURATION)).thenReturn(op1_);
        Mockito.when(op2.getDerivative(DUMMY_SYMBOL, DUMMY_DURATION)).thenReturn(op2_);

        final RegExp expected = new Concat(op1_, op2);
        Assert.assertEquals(expected, new Concat(op1, op2).getDerivative(DUMMY_SYMBOL, DUMMY_DURATION));
    }

    @Test
    public void hasEmptyString_returnsFalseIfNeitherOperandsHaveEmptyString() {
        Mockito.when(op1.hasEmptyString()).thenReturn(false);
        Mockito.when(op2.hasEmptyString()).thenReturn(false);

        Assert.assertFalse(new Concat(op1, op2).hasEmptyString());
    }

    @Test
    public void hasEmptyString_returnsFalseIfOnlyOneOperandHasEmptyString() {
        Mockito.when(op1.hasEmptyString()).thenReturn(false);
        Mockito.when(op2.hasEmptyString()).thenReturn(true);

        Assert.assertFalse(new Concat(op1, op2).hasEmptyString());
        Assert.assertFalse(new Concat(op2, op1).hasEmptyString());
    }

    @Test
    public void hasEmptyString_returnsTrueIfBothOperandsHaveEmptyString() {
        Mockito.when(op1.hasEmptyString()).thenReturn(true);
        Mockito.when(op2.hasEmptyString()).thenReturn(true);

        Assert.assertTrue(new Concat(op1, op2).hasEmptyString());
    }

    @Test
    public void isEmpty_returnsFalseIfNeitherOperandsEmpty() {
        Mockito.when(op1.isEmpty()).thenReturn(false);
        Mockito.when(op2.isEmpty()).thenReturn(false);

        Assert.assertFalse(new Concat(op1, op2).isEmpty());
    }

    @Test
    public void isEmpty_returnsTrueIfOnlyOneOperandIsEmpty() {
        Mockito.when(op1.isEmpty()).thenReturn(false);
        Mockito.when(op2.isEmpty()).thenReturn(true);

        Assert.assertTrue(new Concat(op1, op2).isEmpty());
        Assert.assertTrue(new Concat(op2, op1).isEmpty());
    }

    @Test
    public void isEmpty_returnsTrueIfBothOperandsEmpty() {
        Mockito.when(op1.isEmpty()).thenReturn(true);
        Mockito.when(op2.isEmpty()).thenReturn(true);

        Assert.assertTrue(new Concat(op1, op2).isEmpty());
    }

    @Test
    public void removeEmptyString_changesNothingIfFirstOperandDoesNotHaveEmptyString() {
        Mockito.when(op1.hasEmptyString()).thenReturn(false);

        final Concat before = new Concat(op1, op2);
        Assert.assertEquals(before, before.removeEmptyString());
    }

    @Test
    public void removeEmptyString_ReturnsOrIfFirstOperandContainsEmptyString() {
        Mockito.when(op1.removeEmptyString()).thenReturn(op1NoEs);
        Mockito.when(op2.removeEmptyString()).thenReturn(op2NoEs);
        Mockito.when(op1.hasEmptyString()).thenReturn(true);

        final Concat before = new Concat(op1, op2);
        final Or after = new Or(new Concat(op1NoEs, op2), op2NoEs);
        Assert.assertEquals(after, before.removeEmptyString());
    }

    @Test
    public void minTimeoutValue_returnsMinimumOfOperandTimeoutValuesIfFirstOperandHasEmptyString() {
        Mockito.when(op1.minTimeoutValue()).thenReturn(Duration.ofSeconds(10));
        Mockito.when(op2.minTimeoutValue()).thenReturn(Duration.ofSeconds(20));

        Mockito.when(op1.hasEmptyString()).thenReturn(true);
        Mockito.when(op2.hasEmptyString()).thenReturn(false);
        Assert.assertEquals(Duration.ofSeconds(10), new Concat(op1, op2).minTimeoutValue());

        Mockito.when(op1.hasEmptyString()).thenReturn(false);
        Mockito.when(op2.hasEmptyString()).thenReturn(true);
        Assert.assertEquals(Duration.ofSeconds(10), new Concat(op2, op1).minTimeoutValue());
    }

    @Test
    public void minTimeoutValue_returnsFirstOperandTimeoutValueIfItHasNoEmptyString() {
        Mockito.when(op1.minTimeoutValue()).thenReturn(Duration.ofSeconds(10));
        Mockito.when(op2.minTimeoutValue()).thenReturn(Duration.ofSeconds(20));

        Mockito.when(op1.hasEmptyString()).thenReturn(false);
        Mockito.when(op2.hasEmptyString()).thenReturn(true);
        Assert.assertEquals(Duration.ofSeconds(10), new Concat(op1, op2).minTimeoutValue());

        Mockito.when(op1.hasEmptyString()).thenReturn(true);
        Mockito.when(op2.hasEmptyString()).thenReturn(false);
        Assert.assertEquals(Duration.ofSeconds(20), new Concat(op2, op1).minTimeoutValue());
    }

    @Test
    public void getFrontSymbols_returnsUnionOfFirstSymbolsOfOperands_ifFirstOperandHasEmptyString() {

        final Symbol symbols[] = {new Symbol("sym1"), new Symbol("sym2"), new Symbol("sym3")};
        final LinkedHashSet<Symbol> dummyAlphabet = new LinkedHashSet<>(Arrays.asList(symbols[0], symbols[1], symbols[2]));
        Mockito.when(op1.getFrontSymbols(dummyAlphabet)).thenReturn(new HashSet<>(Collections.singletonList(symbols[0])));
        Mockito.when(op2.getFrontSymbols(dummyAlphabet)).thenReturn(new HashSet<>(Collections.singletonList(symbols[2])));

        Mockito.when(op1.hasEmptyString()).thenReturn(true);

        final Set<Symbol> frontSymbols = new Concat(op1, op2).getFrontSymbols(dummyAlphabet);
        Assert.assertEquals(2, frontSymbols.size());
        Assert.assertTrue(frontSymbols.contains(symbols[0]));
        Assert.assertFalse(frontSymbols.contains(symbols[1]));
        Assert.assertTrue(frontSymbols.contains(symbols[2]));
    }

    @Test
    public void getFrontSymbols_returnsFirstSymbolsOfFirstOperand_ifFirstOperandDoesNotHaveEmptyString() {

        final Symbol symbols[] = {new Symbol("sym1"), new Symbol("sym2"), new Symbol("sym3")};
        final LinkedHashSet<Symbol> dummyAlphabet = new LinkedHashSet<>(Arrays.asList(symbols[0], symbols[1], symbols[2]));
        Mockito.when(op1.getFrontSymbols(dummyAlphabet)).thenReturn(new HashSet<>(Collections.singletonList(symbols[0])));
        Mockito.when(op2.getFrontSymbols(dummyAlphabet)).thenReturn(new HashSet<>(Collections.singletonList(symbols[2])));

        Mockito.when(op1.hasEmptyString()).thenReturn(false);

        final Set<Symbol> frontSymbols = new Concat(op1, op2).getFrontSymbols(dummyAlphabet);
        Assert.assertEquals(1, frontSymbols.size());
        Assert.assertTrue(frontSymbols.contains(symbols[0]));
        Assert.assertFalse(frontSymbols.contains(symbols[1]));
        Assert.assertFalse(frontSymbols.contains(symbols[2]));
    }

    @Test
    public void equals_returnsTrueIfSameOperatorAndEqualOperands() {
        Assert.assertEquals(new Concat(op1, op2), new Concat(op1, op2));
    }

    @Test
    public void equals_returnsFalseIfDifferentOperatorOrUnequalOperands() {
        Assert.assertNotEquals(new Concat(op1, op2), new Or(op1, op2));
        Assert.assertNotEquals(new Concat(op1, op2), new Concat(op2, op1));
    }
}