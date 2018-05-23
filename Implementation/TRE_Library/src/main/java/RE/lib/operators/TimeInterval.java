package RE.lib.operators;

import RE.lib.RegExp;
import RE.lib.basic.EmptySet;
import RE.lib.basic.EmptyString;
import RE.lib.basic.Symbol;
import RE.lib.exceptions.NegativeTimeException;
import TA.lib.ClockCondition;
import TA.lib.State;
import TA.lib.TimedAutomaton;
import TA.lib.Transition;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.Objects;

import static util.DurationComparison.*;

/**
 * Used when the time length of a trace must be
 * within the interval specified by the limits.
 */
public class TimeInterval extends UnaryOp {

    public final static Duration INFINITY = Duration.ofSeconds(Long.MAX_VALUE); // Represents infinity
    private final Duration min, max; // Lower-bound and upper-bound of time interval

    TimeInterval(final RegExp re, final Duration min, final Duration max) {
        super(re);
        this.min = min;
        this.max = max;
    }

    public Duration getMin() {
        return min;
    }

    public Duration getMax() {
        return max;
    }

    /**
     * Attempts to apply some reduction rules instead of just constructing a TimeInterval regular expression.
     *
     * @param re  Unary operator's operand.
     * @param min Lower-bound of time interval.
     * @param max Upper-bound of time interval.
     * @return Resultant regular expression.
     */
    public static RegExp build(final RegExp re, final Duration min, final Duration max) {

        if (min.isNegative() || max.isNegative()) { // negative not allowed
            throw new NegativeTimeException();
        } else if (min.isZero() && re.equals(EmptyString.INSTANCE)) { // <1>[0,t'] = 1
            return EmptyString.INSTANCE;
        } else if (re.isEmpty() || min.equals(INFINITY) || grtrEq(min, max)) { // <0> = <e>[∞,t'] = <e>[s,s'] = 0 (for s>=s')
            return EmptySet.INSTANCE;
        } else if (min.isZero() && max.equals(INFINITY)) { // <e>[0,∞] = e
            return re;
        } else if (re instanceof TimeInterval) { // <<e>[a,b]>[c,d] = <e>[max(a,c),min(b,d)]
            final TimeInterval re2 = (TimeInterval) re;
            return TimeInterval.build(re2.re, max(min, re2.min), min(max, re2.max));
        } else {
            return new TimeInterval(re, min, max);
        }
    }

    @Override
    public RegExp getDerivative(final Symbol sym, final Duration dt) {

        if (grtrEq(dt, max)) { // Max reached/exceeded
            return EmptySet.INSTANCE;
        } else {
            final RegExp opDeriv = re.getDerivative(sym, dt);
            if (lessThan(dt, min)) { // Min not reached
                return TimeInterval.build(opDeriv.removeEmptyString(), diffZero(min, dt), diffInfty(max, dt));
            } else { // Time passed is within range
                return TimeInterval.build(opDeriv, diffZero(min, dt), diffInfty(max, dt));
            }
        }
    }

    @Override
    public RegExp getDerivative(final Duration dt) {

        if (grtrEq(dt, max)) { // Max reached/exceeded
            return re.hasEmptyString() ? EmptyString.INSTANCE : EmptySet.INSTANCE;
        } else { // Time passed is below or within range (Note: INFINITY unaffected)
            final RegExp opDeriv = re.getDerivative(dt);
            if (lessThan(dt, min)) { // Min not reached
                return TimeInterval.build(opDeriv.removeEmptyString(), diffZero(min, dt), diffInfty(max, dt));
            } else { // Time passed is within range
                return TimeInterval.build(opDeriv, diffZero(min, dt), diffInfty(max, dt));
            }
        }
    }

    @Override
    public boolean hasEmptyString() {
        return min.isZero() && re.hasEmptyString();
    }

    @Override
    public boolean isEmpty() {
        if (min.equals(INFINITY) || min.equals(max)) {
            return true;
        } else if(min.isZero()) {
            return re.isEmpty();
        } else {
            return re.removeEmptyString().isEmpty();
        }
    }

    @Override
    public RegExp removeEmptyString() {
        return TimeInterval.build(re.removeEmptyString(), min, max);
    }

    @Override
    public Duration minTimeoutValue() {
        // Minimum of upper limit and timeout value of operand
        return min(max, re.minTimeoutValue());
    }

    @Override
    public TimedAutomaton toTimedAutomaton(final LinkedHashSet<Symbol> alphabet) {

        final TimedAutomaton sub = re.toTimedAutomaton(alphabet);
        final TimedAutomaton ta = new TimedAutomaton(sub.getInitialState()).joinWith(sub, false);

        final State newAccState = ta.addState(true);
        final ClockCondition cond = new ClockCondition(ta.addClock(), min, max);

        for (final Transition t : sub.getTransitionsLeadingToAcceptingStates()) {
            t.guard.add(cond); // previous guard with added condition
            ta.addTransition(new Transition(t.from, newAccState, t.symbol, t.guard));
        }
        return ta;
    }

    @Override
    public String toString() {
        // Note: infinity should never get printed because of reductions in build(...)
        return "<" + re.toString() + ">[" + min + "," + (max.equals(INFINITY) ? "inf" : max) + "]";
    }

    /**
     * Computes n1-n2 and rounds negative results to zero.
     */
    private Duration diffZero(final Duration n1, final Duration n2) {
        return max(Duration.ZERO, n1.minus(n2)); // negative converted to zero
    }

    /**
     * Computes n1-n2 where if n1 is infinity, the result is infinity.
     */
    private Duration diffInfty(final Duration n1, final Duration n2) {
        return n1.equals(INFINITY) ? INFINITY : (n1.minus(n2)); // ∞ - x = ∞
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TimeInterval that = (TimeInterval) o;
        return Objects.equals(min, that.min) &&
                Objects.equals(max, that.max) &&
                Objects.equals(re, that.re);
    }
}