package RE.lib.operators;

import RE.lib.RegExp;
import RE.lib.basic.Symbol;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Abstract unary operator.
 */
public abstract class UnaryOp extends RegExp {

    // Single regular expression on which operator is applied
    protected final RegExp re;

    /**
     * General constructor with one regular expression
     * on which the unary operator is being applied.
     *
     * @param re The unary operator's operand.
     */
    UnaryOp(final RegExp re) {
        this.re = re;
    }

    public RegExp getRe() {
        return re;
    }

    @Override
    public Duration minTimeoutValue() {
        // By default, returns minimum timeout value of the operand
        return re.minTimeoutValue();
    }

    @Override
    public Set<Symbol> getFrontSymbols(final LinkedHashSet<Symbol> alphabet) {
        // By default, returns front symbols of the operand
        return re.getFrontSymbols(alphabet);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UnaryOp that = (UnaryOp) o;
        return Objects.equals(re, that.re);
    }
}
