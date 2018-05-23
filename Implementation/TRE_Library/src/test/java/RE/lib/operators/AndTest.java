package RE.lib.operators;

import RE.lib.RegExp;
import RE.lib.basic.EmptySet;
import RE.lib.basic.EmptyString;
import RE.lib.basic.Symbol;
import RE.lib.operators.And;
import RE.lib.operators.Or;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class AndTest {

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
    public void build_returnsEmptySetIfOnlyOneOperandEmpty() { // 0 & e = e & 0 = 0
        Mockito.when(op1.isEmpty()).thenReturn(true);
        Mockito.when(op2.isEmpty()).thenReturn(false);

        Assert.assertEquals(EmptySet.INSTANCE, And.build(op1, op2));
        Assert.assertEquals(EmptySet.INSTANCE, And.build(op2, op1));
    }

    @Test
    public void build_returnsEmptySetIfBothOperandsEmpty() { // 0 & 0 = 0
        Mockito.when(op1.isEmpty()).thenReturn(true);
        Mockito.when(op2.isEmpty()).thenReturn(true);

        Assert.assertEquals(EmptySet.INSTANCE, And.build(op1, op2));
    }

    @Test
    public void build_returnsEmptyStringIfOneOperandIsEmptyStringAndOtherContainsEmptyString() { // e & 1 = 1 (if 1 in e)
        op1 = EmptyString.INSTANCE;
        Mockito.when(op2.isEmpty()).thenReturn(false);
        Mockito.when(op2.hasEmptyString()).thenReturn(true);

        Assert.assertEquals(EmptyString.INSTANCE, And.build(op1, op2));
        Assert.assertEquals(EmptyString.INSTANCE, And.build(op2, op1));
    }

    @Test
    public void build_returnsEmptySetIfOneOperandIsEmptyStringButOtherDoesNotContainEmptyString() { // e & 1 = 0 (if 1 not in e)
        op1 = EmptyString.INSTANCE;
        Mockito.when(op2.isEmpty()).thenReturn(false);
        Mockito.when(op2.hasEmptyString()).thenReturn(false);

        Assert.assertEquals(EmptySet.INSTANCE, And.build(op1, op2));
        Assert.assertEquals(EmptySet.INSTANCE, And.build(op2, op1));
    }

    @Test
    public void build_returnsEquivalentExpressionIfExpressionsAreEqual() { // e & e = e
        op1 = new Symbol("symbol");
        op2 = new Symbol("symbol");

        final RegExp and = And.build(op1, op2);
        Assert.assertEquals(op1, and);
        Assert.assertEquals(op2, and);
    }

    @Test
    public void build_returnsEmptySetIfOperandsAreUnequalSymbols() { // a & b = 0 (if a != b and both symbols)
        op1 = new Symbol("symbol_1");
        op2 = new Symbol("symbol_2");

        Assert.assertEquals(EmptySet.INSTANCE, And.build(op1, op2));
    }

    @Test
    public void build_returnsAndOfOperands() { // Standard e1 & e2 (assuming e1 != e2)
        Mockito.when(op1.isEmpty()).thenReturn(false);
        Mockito.when(op2.isEmpty()).thenReturn(false);
        Mockito.when(op1.hasEmptyString()).thenReturn(false);
        Mockito.when(op2.hasEmptyString()).thenReturn(false);
        Assume.assumeFalse(op1.equals(op2));

        Assert.assertEquals(new And(op1, op2), And.build(op1, op2));
    }

    @Test
    public void getDerivative_wrtAnyTimeReturnsAndOfResidualsOfOperands() { // d(e1 & e2) = e1' & e2'
        Mockito.when(op1.getDerivative(DUMMY_DURATION)).thenReturn(op1_);
        Mockito.when(op2.getDerivative(DUMMY_DURATION)).thenReturn(op2_);

        final RegExp residual = new And(op1, op2).getDerivative(DUMMY_DURATION);
        Assert.assertEquals(new And(op1_, op2_), residual);
    }

    @Test
    public void getDerivative_wrtAnySymbolAndAnyTimeReturnsAndOfResidualOfOperands() { // d(e1 & e2) = e1' & e2'
        Mockito.when(op1.getDerivative(DUMMY_SYMBOL, DUMMY_DURATION)).thenReturn(op1_);
        Mockito.when(op2.getDerivative(DUMMY_SYMBOL, DUMMY_DURATION)).thenReturn(op2_);

        final RegExp residual = new And(op1, op2).getDerivative(DUMMY_SYMBOL, DUMMY_DURATION);
        Assert.assertEquals(new And(op1_, op2_), residual);
    }

    @Test
    public void hasEmptyString_returnsFalseIfNeitherOperandsHaveEmptyString() {
        Mockito.when(op1.hasEmptyString()).thenReturn(false);
        Mockito.when(op2.hasEmptyString()).thenReturn(false);

        Assert.assertFalse(new And(op1, op2).hasEmptyString());
    }

    @Test
    public void hasEmptyString_returnsFalseIfOnlyOneOperandHasEmptyString() {
        Mockito.when(op1.hasEmptyString()).thenReturn(false);
        Mockito.when(op2.hasEmptyString()).thenReturn(true);

        Assert.assertFalse(new And(op1, op2).hasEmptyString());
        Assert.assertFalse(new And(op2, op1).hasEmptyString());
    }

    @Test
    public void hasEmptyString_returnsTrueIfBothOperandsHaveEmptyString() {
        Mockito.when(op1.hasEmptyString()).thenReturn(true);
        Mockito.when(op2.hasEmptyString()).thenReturn(true);

        Assert.assertTrue(new And(op1, op2).hasEmptyString());
    }

    @Test
    public void isEmpty_returnsFalseIfNeitherOperandsEmpty() {
        Mockito.when(op1.isEmpty()).thenReturn(false);
        Mockito.when(op2.isEmpty()).thenReturn(false);

        Assert.assertFalse(new And(op1, op2).isEmpty());
    }

    @Test
    public void isEmpty_returnsTrueIfOnlyOneOperandEmpty() {
        Mockito.when(op1.isEmpty()).thenReturn(false);
        Mockito.when(op2.isEmpty()).thenReturn(true);

        Assert.assertTrue(new And(op1, op2).isEmpty());
        Assert.assertTrue(new And(op2, op1).isEmpty());
    }

    @Test
    public void isEmpty_returnsTrueIfBothOperandsEmpty() {
        Mockito.when(op1.isEmpty()).thenReturn(true);
        Mockito.when(op2.isEmpty()).thenReturn(true);

        Assert.assertTrue(new And(op1, op2).isEmpty());
    }

    @Test
    public void removeEmptyString_removesEmptyStringFromOperands() {
        Mockito.when(op1.removeEmptyString()).thenReturn(op1NoEs);
        Mockito.when(op2.removeEmptyString()).thenReturn(op2NoEs);

        final And before = new And(op1, op2);
        final And after = new And(op1NoEs, op2NoEs);
        Assert.assertEquals(after, before.removeEmptyString());
    }

    @Test
    public void minTimeoutValue_returnsMinimumOfOperandTimeoutValues() {
        Mockito.when(op1.minTimeoutValue()).thenReturn(Duration.ofSeconds(10));
        Mockito.when(op2.minTimeoutValue()).thenReturn(Duration.ofSeconds(20));

        Assert.assertEquals(Duration.ofSeconds(10), new And(op1, op2).minTimeoutValue());
        Assert.assertEquals(Duration.ofSeconds(10), new And(op2, op1).minTimeoutValue());
    }

    @Test
    public void getFrontSymbols_returnsUnionOfFirstSymbolsOfOperands() {

        final Symbol symbols[] = {new Symbol("sym1"), new Symbol("sym2"), new Symbol("sym3")};
        final LinkedHashSet<Symbol> dummyAlphabet = new LinkedHashSet<>(Arrays.asList(symbols[0], symbols[1], symbols[2]));
        Mockito.when(op1.getFrontSymbols(dummyAlphabet)).thenReturn(new HashSet<>(Collections.singletonList(symbols[0])));
        Mockito.when(op2.getFrontSymbols(dummyAlphabet)).thenReturn(new HashSet<>(Collections.singletonList(symbols[2])));

        final Set<Symbol> frontSymbols = new And(op1, op2).getFrontSymbols(dummyAlphabet);
        Assert.assertEquals(2, frontSymbols.size());
        Assert.assertTrue(frontSymbols.contains(symbols[0]));
        Assert.assertFalse(frontSymbols.contains(symbols[1]));
        Assert.assertTrue(frontSymbols.contains(symbols[2]));
    }

    @Test
    public void equals_returnsTrueIfSameOperatorAndEqualOperands() {
        Assert.assertEquals(new And(op1, op2), new And(op1, op2));
    }

    @Test
    public void equals_returnsFalseIfDifferentOperatorOrUnequalOperands() {
        Assert.assertNotEquals(new And(op1, op2), new Or(op1, op2));
        Assert.assertNotEquals(new And(op1, op2), new And(op2, op1));
    }
}