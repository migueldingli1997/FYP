package RE.lib;

import RE.lib.basic.EmptySet;
import RE.lib.basic.EmptyString;
import RE.lib.basic.NegatedSymbol;
import RE.lib.basic.Symbol;
import RE.lib.operators.Concat;
import RE.lib.operators.And;
import RE.lib.operators.Or;
import RE.lib.operators.Star;
import RE.lib.operators.TimeInterval;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;

public class IntegrationTests {

    private final static Symbol A = new Symbol("A");
    private final static Symbol B = new Symbol("B");
    private final static Symbol C = new Symbol("C");
    private final static Symbol D = new Symbol("D");

    private final static RegExp nA = new NegatedSymbol(A);
    private final static RegExp nB = new NegatedSymbol(B);
    private final static RegExp nC = new NegatedSymbol(C);
    private final static RegExp nD = new NegatedSymbol(D);

    private final static Duration T0 = Duration.ZERO;
    private final static Duration T1 = Duration.ofSeconds(1);
    private final static Duration T2 = Duration.ofSeconds(2);
    private final static Duration T3 = Duration.ofSeconds(3);
    private final static Duration T4 = Duration.ofSeconds(4);
    private final static Duration T5 = Duration.ofSeconds(5);
    private final static Duration T6 = Duration.ofSeconds(6);

    private final static Symbol SymAny = null;
    private final static Duration TAny = null;

    private final static RegExp EmpStr = EmptyString.INSTANCE;
    private final static RegExp EmpSet = EmptySet.INSTANCE;

    @Test
    public void concat_not_and_1() {

        final RegExp AB = Concat.build(A, B);
        final RegExp nBnA = Concat.build(nB, nA);
        final RegExp AB_nBnA = And.build(AB, nBnA); // A.B & ~B.~A

        // Valid sequence/s
        Assert.assertEquals(And.build(B, nA), AB_nBnA.getDerivative(A, TAny)); // w.r.t A gives (B & ~A)
        Assert.assertEquals(EmpStr, AB_nBnA.getDerivative(A, TAny).getDerivative(B, TAny)); // w.r.t AB gives 1

        // Invalid sequence/s
        Assert.assertEquals(EmpSet, AB_nBnA.getDerivative(A, TAny).getDerivative(A, TAny)); // w.r.t AA gives 0
        Assert.assertEquals(EmpSet, AB_nBnA.getDerivative(B, TAny)); // w.r.t B gives 0
        Assert.assertEquals(EmpSet, AB_nBnA.getDerivative(C, TAny)); // w.r.t C gives 0
    }

    @Test
    public void concat_not_and_2() {

        final RegExp AB = Concat.build(A, B);
        final RegExp BA = Concat.build(B, A);
        final RegExp AB_BA = And.build(AB, BA); // A.B & B.A

        // Invalid sequence/s
        Assert.assertEquals(EmpSet, AB_BA.getDerivative(A, TAny)); // w.r.t A gives 0
        Assert.assertEquals(EmpSet, AB_BA.getDerivative(B, TAny)); // w.r.t B gives 0
        Assert.assertEquals(EmpSet, AB_BA.getDerivative(C, TAny)); // w.r.t B gives 0
    }

    @Test
    public void concat_not_or_1() {

        final RegExp AB = Concat.build(A, B);
        final RegExp nBnA = Concat.build(nB, nA);
        final RegExp AB_nBnA = Or.build(AB, nBnA); // A.B + ~B.~A

        // Valid sequence/s
        Assert.assertEquals(Or.build(B, nA), AB_nBnA.getDerivative(A, TAny)); // w.r.t A gives (B + ~A)
        Assert.assertEquals(EmpStr, AB_nBnA.getDerivative(A, TAny).getDerivative(B, TAny)); // w.r.t AB gives 1
        Assert.assertEquals(nA, AB_nBnA.getDerivative(C, TAny)); // w.r.t C gives ~A
        Assert.assertEquals(EmpStr, AB_nBnA.getDerivative(C, TAny).getDerivative(C, TAny)); // w.r.t CC gives 1

        // Invalid sequence/s
        Assert.assertEquals(EmpSet, AB_nBnA.getDerivative(B, TAny)); // w.r.t B gives 0
    }

    @Test
    public void concat_not_or_2() {

        final RegExp AB = Concat.build(A, B);
        final RegExp BA = Concat.build(B, A);
        final RegExp AB_BA = Or.build(AB, BA); // A.B + B.A

        // Valid sequence/s
        Assert.assertEquals(B, AB_BA.getDerivative(A, TAny)); // w.r.t A gives B
        Assert.assertEquals(EmpStr, AB_BA.getDerivative(A, TAny).getDerivative(B, TAny)); // w.r.t AB gives 1
        Assert.assertEquals(A, AB_BA.getDerivative(B, TAny)); // w.r.t B gives A
        Assert.assertEquals(EmpStr, AB_BA.getDerivative(B, TAny).getDerivative(A, TAny)); // w.r.t BA gives 1

        // Invalid sequence/s
        Assert.assertEquals(EmpSet, AB_BA.getDerivative(C, TAny)); // w.r.t C gives 0
        Assert.assertEquals(EmpSet, AB_BA.getDerivative(A, TAny).getDerivative(C, TAny)); // w.r.t AC gives 0
        Assert.assertEquals(EmpSet, AB_BA.getDerivative(B, TAny).getDerivative(C, TAny)); // w.r.t BC gives 0
    }

    @Test
    public void more_concat_not_and() {

        // These should give identical results
        final RegExp re1 = Concat.build(A, Or.build(B, C)); // A.(B + C)
        final RegExp re2 = Or.build(Concat.build(A, B), Concat.build(A, C)); // A.B + A.C

        final RegExp re1_A = re1.getDerivative(A, TAny);
        final RegExp re2_A = re2.getDerivative(A, TAny);
        final RegExp re1_B = re1.getDerivative(B, TAny);
        final RegExp re2_B = re2.getDerivative(B, TAny);

        Assert.assertEquals(re1_A, re2_A); // w.r.t A
        Assert.assertEquals(re1_B, re2_B); // w.r.t B
        Assert.assertEquals(re1_A.getDerivative(B, TAny), re2_A.getDerivative(B, TAny)); // w.r.t AB
        Assert.assertEquals(re1_B.getDerivative(A, TAny), re2_B.getDerivative(A, TAny)); // w.r.t BA
    }

    @Test
    public void more_concat_not_or() {

        // These should give identical results
        final RegExp re1 = Concat.build(A, And.build(B, nC)); // A.(B & ~C)
        final RegExp re2 = And.build(Concat.build(A, B), Concat.build(A, nC)); // A.B & A.~C

        final RegExp re1_A = re1.getDerivative(A, TAny);
        final RegExp re2_A = re2.getDerivative(A, TAny);
        final RegExp re1_B = re1.getDerivative(B, TAny);
        final RegExp re2_B = re2.getDerivative(B, TAny);

        Assert.assertEquals(re1_A, re2_A); // w.r.t A
        Assert.assertEquals(re1_B, re2_B); // w.r.t B
        Assert.assertEquals(re1_A.getDerivative(B, TAny), re2_A.getDerivative(B, TAny)); // w.r.t AB
        Assert.assertEquals(re1_B.getDerivative(A, TAny), re2_B.getDerivative(A, TAny)); // w.r.t BA
    }

    @Test
    public void star_not() {

        final RegExp re = Star.build(nA); // (~A)*

        // Valid sequence/s
        Assert.assertEquals(re, re
                .getDerivative(B, TAny).getDerivative(C, TAny)
                .getDerivative(B, TAny).getDerivative(C, TAny)
                .getDerivative(B, TAny).getDerivative(C, TAny));

        // Invalid sequence/s
        Assert.assertEquals(EmpSet, re.getDerivative(A, TAny));
    }

    @Test
    public void timeInterval_not() {

        final RegExp re = TimeInterval.build(nA, T2, T4); // <~A>[2,4]

        // Valid sequence/s
        Assert.assertEquals(EmpStr, re.getDerivative(B, T3)); // w.r.t B and on time
        Assert.assertEquals(EmpStr, re.getDerivative(C, T3)); // w.r.t C and on time

        // Invalid sequence/s (using A)
        Assert.assertEquals(EmpSet, re.getDerivative(A, T1)); // w.r.t A and early
        Assert.assertEquals(EmpSet, re.getDerivative(A, T3)); // w.r.t A and on time
        Assert.assertEquals(EmpSet, re.getDerivative(A, T5)); // w.r.t A and late

        // Invalid sequence/s (using ~A)
        Assert.assertEquals(EmpSet, re.getDerivative(B, T1)); // w.r.t B and early
        Assert.assertEquals(EmpSet, re.getDerivative(C, T1)); // w.r.t C and early
        Assert.assertEquals(EmpSet, re.getDerivative(B, T5)); // w.r.t B and late
        Assert.assertEquals(EmpSet, re.getDerivative(C, T5)); // w.r.t C and late

        // Time-only derivatives
        Assert.assertEquals(TimeInterval.build(nA, T0, T2), re.getDerivative(T2)); // w.r.t T2
        Assert.assertEquals(EmpSet, re.getDerivative(T5)); // w.r.t T5
    }

    @Test
    public void timeInterval_or() {

        final RegExp int1 = TimeInterval.build(A, T0, T2);
        final RegExp int2 = TimeInterval.build(B, T3, T5);
        final RegExp re = Or.build(int1, int2); // <A>[0,2] + <B>[3,5]

        // Valid sequence/s
        Assert.assertEquals(EmpStr, re.getDerivative(A, T1)); // w.r.t A and on time
        Assert.assertEquals(EmpStr, re.getDerivative(B, T4)); // w.r.t B and on time

        // Invalid sequence/s
        Assert.assertEquals(EmpSet, re.getDerivative(B, T1)); // w.r.t B and early
        Assert.assertEquals(EmpSet, re.getDerivative(A, T4)); // w.r.t A and late

        // Time-only derivatives
        final RegExp reMinusOne = Or.build(TimeInterval.build(A, T0, T1), TimeInterval.build(B, T2, T4));
        Assert.assertEquals(reMinusOne, re.getDerivative(T1)); // w.r.t T1
        Assert.assertEquals(TimeInterval.build(B, T1, T3), re.getDerivative(T2)); // w.r.t T2
        Assert.assertEquals(EmpSet, re.getDerivative(T5)); // w.r.t T5
    }

    @Test
    public void timeInterval_and() {

        final RegExp int1 = TimeInterval.build(A, T0, T4);
        final RegExp int2 = TimeInterval.build(A, T2, T6);
        final RegExp re = And.build(int1, int2); // <A>[0,4] & <A>[2,6]

        // Valid sequence/s
        Assert.assertEquals(EmpStr, re.getDerivative(A, T2)); // w.r.t A and exactly on time
        Assert.assertEquals(EmpStr, re.getDerivative(A, T3)); // w.r.t A and on time
        Assert.assertEquals(EmpStr, re.getDerivative(A, T4.minus(Duration.ofMillis(1)))); // w.r.t A and exactly on time

        // Invalid sequence/s (using A)
        Assert.assertEquals(EmpSet, re.getDerivative(A, T1)); // w.r.t A and early
        Assert.assertEquals(EmpSet, re.getDerivative(A, T2.minus(Duration.ofMillis(1)))); // w.r.t A and exactly early
        Assert.assertEquals(EmpSet, re.getDerivative(A, T4)); // w.r.t A and exactly late
        Assert.assertEquals(EmpSet, re.getDerivative(A, T5)); // w.r.t A and late

        // Invalid sequence/s (using ~A)
        Assert.assertEquals(EmpSet, re.getDerivative(B, T3)); // w.r.t B and on time
        Assert.assertEquals(EmpSet, re.getDerivative(C, T3)); // w.r.t C and on time

        // Time-only derivatives
        final RegExp reMinusTwo = And.build(TimeInterval.build(A, T0, T2), TimeInterval.build(A, T0, T4));
        Assert.assertEquals(reMinusTwo, re.getDerivative(T2)); // w.r.t T2
        Assert.assertEquals(EmpSet, re.getDerivative(T4)); // w.r.t T4
    }

    @Test
    public void timeInterval_concat_1() {

        final RegExp int1 = TimeInterval.build(A, T1, T3);
        final RegExp int2 = TimeInterval.build(B, T1, T3);
        final RegExp re = Concat.build(int1, int2); // <A>[1,3].<B>[1,3]

        // Valid sequence/s
        Assert.assertEquals(int2, re.getDerivative(A, T2)); // w.r.t A and on time
        Assert.assertEquals(EmpStr, re.getDerivative(A, T2).getDerivative(B, T2)); // w.r.t AB and on time

        // Invalid sequence/s
        Assert.assertEquals(EmpSet, re.getDerivative(A, T0)); // w.r.t A and early
        Assert.assertEquals(EmpSet, re.getDerivative(A, T3)); // w.r.t A and late
        Assert.assertEquals(EmpSet, re.getDerivative(A, T2).getDerivative(B, T0)); // w.r.t AB and early
        Assert.assertEquals(EmpSet, re.getDerivative(A, T2).getDerivative(B, T3)); // w.r.t AB and late

        // Time-only derivatives
        Assert.assertEquals(Concat.build(TimeInterval.build(A, T0, T2), int2), re.getDerivative(T1)); // w.r.t T1
        Assert.assertEquals(Concat.build(TimeInterval.build(A, T0, T1), int2), re.getDerivative(T2)); // w.r.t T2
        Assert.assertEquals(EmpSet, re.getDerivative(T3)); // w.r.t T3
    }

    @Test
    public void timeInterval_concat_2() {

        final RegExp re = TimeInterval.build(Concat.build(A, B), T2, T4); // <A.B>[2,4]

        // Valid sequence/s
        Assert.assertEquals(TimeInterval.build(B, T2, T4), re.getDerivative(A, T0)); // w.r.t A and on time
        Assert.assertEquals(TimeInterval.build(B, T1, T3), re.getDerivative(A, T1)); // w.r.t A and on time
        Assert.assertEquals(TimeInterval.build(B, T0, T2), re.getDerivative(A, T2)); // w.r.t A and on time
        Assert.assertEquals(TimeInterval.build(B, T0, T1), re.getDerivative(A, T3)); // w.r.t A and on time
        Assert.assertEquals(EmpStr, re.getDerivative(A, T0).getDerivative(B, T3)); // w.r.t AB and on time
        Assert.assertEquals(EmpStr, re.getDerivative(A, T0).getDerivative(B, T2)); // w.r.t AB and on time
        Assert.assertEquals(EmpStr, re.getDerivative(A, T1).getDerivative(B, T2)); // w.r.t AB and on time
        Assert.assertEquals(EmpStr, re.getDerivative(A, T1).getDerivative(B, T1)); // w.r.t AB and on time
        Assert.assertEquals(EmpStr, re.getDerivative(A, T2).getDerivative(B, T1)); // w.r.t AB and on time
        Assert.assertEquals(EmpStr, re.getDerivative(A, T2).getDerivative(B, T0)); // w.r.t AB and on time
        Assert.assertEquals(EmpStr, re.getDerivative(A, T3).getDerivative(B, T0)); // w.r.t AB and on time

        // Invalid sequence/s
        Assert.assertEquals(EmpSet, re.getDerivative(A, T4)); // w.r.t A and late
        Assert.assertEquals(EmpSet, re.getDerivative(A, T0).getDerivative(B, T4)); // w.r.t AB and early
        Assert.assertEquals(EmpSet, re.getDerivative(A, T1).getDerivative(B, T3)); // w.r.t AB and early
        Assert.assertEquals(EmpSet, re.getDerivative(A, T2).getDerivative(B, T2)); // w.r.t AB and early
        Assert.assertEquals(EmpSet, re.getDerivative(A, T3).getDerivative(B, T1)); // w.r.t AB and early

        // Time-only derivatives
        final RegExp reMinusOne = TimeInterval.build(Concat.build(A, B), T1, T3);
        Assert.assertEquals(reMinusOne, re.getDerivative(T1)); // w.r.t T1
        Assert.assertEquals(EmpSet, re.getDerivative(T4)); // w.r.t T2
    }
}
