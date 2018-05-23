package RE.lib.operators;

import RE.lib.RegExp;
import RE.lib.basic.Symbol;

import java.time.Duration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static util.DurationComparison.min;

/**
 * Abstract binary operator.
 */
public abstract class BinaryOp extends RegExp {

    // Two regular expression on which operator is applied
    protected final RegExp re1, re2;

    /**
     * General constructor with two regular expressions on which the binary operator is being applied.
     *
     * @param re1 The binary operator's first operand.
     * @param re2 The binary operator's second operand.
     */
    protected BinaryOp(final RegExp re1, final RegExp re2) {
        this.re1 = re1;
        this.re2 = re2;
    }

    public final RegExp getRe1() {
        return re1;
    }

    public final RegExp getRe2() {
        return re2;
    }

    @Override
    public Duration minTimeoutValue() {
        // By default, returns minimum of minimum timeout value of the operands
        return min(re1.minTimeoutValue(), re2.minTimeoutValue());
    }

    @Override
    public Set<Symbol> getFrontSymbols(final LinkedHashSet<Symbol> alphabet) {
        // By default, returns union of the front symbols of the operands
        final Set<Symbol> frontSymbols = new HashSet<>(re1.getFrontSymbols(alphabet));
        frontSymbols.addAll(re2.getFrontSymbols(alphabet));
        return frontSymbols;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BinaryOp that = (BinaryOp) o;
        return Objects.equals(re1, that.re1) &&
                Objects.equals(re2, that.re2);
    }
}
