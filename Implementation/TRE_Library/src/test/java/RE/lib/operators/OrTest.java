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
public class OrTest {

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
    public void build_returnsOtherOperandIfOnlyOneOperandEmpty() { // e + 0 = 0 + e = e
        Mockito.when(op1.isEmpty()).thenReturn(true);
        Mockito.when(op2.isEmpty()).thenReturn(false);

        Assert.assertEquals(op2, Or.build(op1, op2));
        Assert.assertEquals(op2, Or.build(op2, op1));
    }

    @Test
    public void build_returnsEmptySetIfBothOperandsEmpty() { // 0 + 0 = 0
        Mockito.when(op1.isEmpty()).thenReturn(true);
        Mockito.when(op2.isEmpty()).thenReturn(true);

        Assert.assertEquals(EmptySet.INSTANCE, Or.build(op1, op2));
    }

    @Test
    public void build_returnsOtherOperandIfOneOperandIsEmptyStringAndOtherHasEmptyString() { // e + 1 = 1 + e = e (if 1 in e)
        op1 = EmptyString.INSTANCE;
        Mockito.when(op2.isEmpty()).thenReturn(false);
        Mockito.when(op2.hasEmptyString()).thenReturn(true);

        Assert.assertEquals(op2, Or.build(op1, op2));
        Assert.assertEquals(op2, Or.build(op2, op1));
    }

    @Test
    public void build_doesNotRemoveAnythingIfOneOperandIsEmptyStringButOtherDoesNotHaveEmptyString() { // e + 1 != e (if 1 not in e)
        op1 = EmptyString.INSTANCE;
        Mockito.when(op2.isEmpty()).thenReturn(false);
        Mockito.when(op2.hasEmptyString()).thenReturn(false);
        Assume.assumeFalse(op1.equals(op2));

        Assert.assertEquals(new Or(op1, op2), Or.build(op1, op2));
        Assert.assertEquals(new Or(op2, op1), Or.build(op2, op1));
    }

    @Test
    public void build_returnsEquivalentExpressionIfExpressionsAreEqual() { // e + e = e
        op1 = new Symbol("symbol");
        op2 = new Symbol("symbol");

        final RegExp or = Or.build(op1, op2);
        Assert.assertEquals(op1, or);
        Assert.assertEquals(op2, or);
    }

    @Test
    public void build_returnsOrOfOperands() { // Standard e1 + e2 (assuming e1 != e2)
        Mockito.when(op1.isEmpty()).thenReturn(false);
        Mockito.when(op2.isEmpty()).thenReturn(false);
        Mockito.when(op1.hasEmptyString()).thenReturn(false);
        Mockito.when(op2.hasEmptyString()).thenReturn(false);
        Assume.assumeFalse(op1.equals(op2));

        Assert.assertEquals(new Or(op1, op2), Or.build(op1, op2));
    }

    @Test
    public void getDerivative_wrtAnyTimeReturnsOrOfResidualsOfOperands() { // d(e1 + e2) = e1' + e2'
        Mockito.when(op1.getDerivative(DUMMY_DURATION)).thenReturn(op1_);
        Mockito.when(op2.getDerivative(DUMMY_DURATION)).thenReturn(op2_);

        final RegExp residual = new Or(op1, op2).getDerivative(DUMMY_DURATION);
        Assert.assertEquals(new Or(op1_, op2_), residual);
    }

    @Test
    public void getDerivative_wrtAnySymbolAndAnyTimeReturnsOrOfResidualOfOperands() { // d(e1 + e2) = e1' + e2'
        Mockito.when(op1.getDerivative(DUMMY_SYMBOL, DUMMY_DURATION)).thenReturn(op1_);
        Mockito.when(op2.getDerivative(DUMMY_SYMBOL, DUMMY_DURATION)).thenReturn(op2_);

        final RegExp residual = new Or(op1, op2).getDerivative(DUMMY_SYMBOL, DUMMY_DURATION);
        Assert.assertEquals(new Or(op1_, op2_), residual);
    }

    @Test
    public void hasEmptyString_returnsFalseIfBothOperandsDoNotHaveEmptyString() {
        Mockito.when(op1.hasEmptyString()).thenReturn(false);
        Mockito.when(op2.hasEmptyString()).thenReturn(false);

        Assert.assertFalse(new Or(op1, op2).hasEmptyString());
    }

    @Test
    public void hasEmptyString_returnsTrueIfOnlyOneOperandHasEmptyString() {
        Mockito.when(op1.hasEmptyString()).thenReturn(false);
        Mockito.when(op2.hasEmptyString()).thenReturn(true);

        Assert.assertTrue(new Or(op1, op2).hasEmptyString());
        Assert.assertTrue(new Or(op2, op1).hasEmptyString());
    }

    @Test
    public void hasEmptyString_returnsTrueIfBothOperandsHaveEmptyString() {
        Mockito.when(op1.hasEmptyString()).thenReturn(true);
        Mockito.when(op2.hasEmptyString()).thenReturn(true);

        Assert.assertTrue(new Or(op1, op2).hasEmptyString());
    }

    @Test
    public void isEmpty_returnsFalseIfNeitherOperandsEmpty() {
        Mockito.when(op1.isEmpty()).thenReturn(false);
        Mockito.when(op2.isEmpty()).thenReturn(false);

        Assert.assertFalse(new Or(op1, op2).isEmpty());
    }

    @Test
    public void isEmpty_returnsFalseIfOnlyOneOperandEmpty() {
        Mockito.when(op1.isEmpty()).thenReturn(false);
        Mockito.when(op2.isEmpty()).thenReturn(true);

        Assert.assertFalse(new Or(op1, op2).isEmpty());
        Assert.assertFalse(new Or(op2, op1).isEmpty());
    }

    @Test
    public void isEmpty_returnsTrueIfBothOperandsEmpty() {
        Mockito.when(op1.isEmpty()).thenReturn(true);
        Mockito.when(op2.isEmpty()).thenReturn(true);

        Assert.assertTrue(new Or(op1, op2).isEmpty());
    }

    @Test
    public void removeEmptyString_removesEmptyStringFromOperands() {
        Mockito.when(op1.removeEmptyString()).thenReturn(op1NoEs);
        Mockito.when(op2.removeEmptyString()).thenReturn(op2NoEs);

        final Or before = new Or(op1, op2);
        final Or after = new Or(op1NoEs, op2NoEs);
        Assert.assertEquals(after, before.removeEmptyString());
    }

    @Test
    public void minTimeoutValue_returnsMinimumOfOperandTimeoutValues() {
        Mockito.when(op1.minTimeoutValue()).thenReturn(Duration.ofSeconds(10));
        Mockito.when(op2.minTimeoutValue()).thenReturn(Duration.ofSeconds(20));

        Assert.assertEquals(Duration.ofSeconds(10), new Or(op1, op2).minTimeoutValue());
        Assert.assertEquals(Duration.ofSeconds(10), new Or(op2, op1).minTimeoutValue());
    }

    @Test
    public void getFrontSymbols_returnsUnionOfFirstSymbolsOfOperands() {

        final Symbol symbols[] = {new Symbol("sym1"), new Symbol("sym2"), new Symbol("sym3")};
        final LinkedHashSet<Symbol> dummyAlphabet = new LinkedHashSet<>(Arrays.asList(symbols[0], symbols[1], symbols[2]));
        Mockito.when(op1.getFrontSymbols(dummyAlphabet)).thenReturn(new HashSet<>(Collections.singletonList(symbols[0])));
        Mockito.when(op2.getFrontSymbols(dummyAlphabet)).thenReturn(new HashSet<>(Collections.singletonList(symbols[2])));

        final Set<Symbol> frontSymbols = new Or(op1, op2).getFrontSymbols(dummyAlphabet);
        Assert.assertEquals(2, frontSymbols.size());
        Assert.assertTrue(frontSymbols.contains(symbols[0]));
        Assert.assertFalse(frontSymbols.contains(symbols[1]));
        Assert.assertTrue(frontSymbols.contains(symbols[2]));
    }

    @Test
    public void equals_returnsTrueIfSameOperatorAndEqualOperands() {
        Assert.assertEquals(new Or(op1, op2), new Or(op1, op2));
    }

    @Test
    public void equals_returnsFalseIfDifferentOperatorOrUnequalOperands() {
        Assert.assertNotEquals(new Or(op1, op2), new And(op1, op2));
        Assert.assertNotEquals(new Or(op1, op2), new Or(op2, op1));
    }
}