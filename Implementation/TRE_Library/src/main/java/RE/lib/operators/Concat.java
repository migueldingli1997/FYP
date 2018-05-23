package RE.lib.operators;

import RE.lib.RegExp;
import RE.lib.basic.EmptySet;
import RE.lib.basic.EmptyString;
import RE.lib.basic.Symbol;
import TA.lib.Clock;
import TA.lib.TimedAutomaton;
import TA.lib.Transition;

import java.time.Duration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static util.DurationComparison.min;

public class Concat extends BinaryOp {

    Concat(final RegExp re1, final RegExp re2) {
        super(re1, re2);
    }

    /**
     * Attempts to apply some reduction rules instead of just constructing a Concat regular expression.
     *
     * @param re1 Binary operator's first operand.
     * @param re2 Binary operator's second operand.
     * @return Resultant regular expression.
     */
    public static RegExp build(final RegExp re1, final RegExp re2) {

        if (re1.isEmpty() || re2.isEmpty()) { // e.f = 0 (if e or f empty)
            return EmptySet.INSTANCE;
        } else if (re1.equals(EmptyString.INSTANCE)) { // 1.e = e
            return re2;
        } else if (re2.equals(EmptyString.INSTANCE)) { // e.1 = e
            return re1;
        } else {
            return new Concat(re1, re2);
        }
    }

    @Override
    public RegExp getDerivative(final Symbol sym, final Duration dt) {

        final RegExp temp = Concat.build(re1.getDerivative(sym, dt), re2);
        if (re1.hasEmptyString()) {
            return Or.build(temp, re2.getDerivative(sym, dt));
        } else {
            return temp;
        }
    }

    @Override
    public RegExp getDerivative(final Duration dt) {

        final RegExp temp = Concat.build(re1.getDerivative(dt), re2);
        if (re1.hasEmptyString()) {
            return Or.build(temp, re2.getDerivative(dt));
        } else {
            return temp;
        }
    }

    @Override
    public boolean hasEmptyString() {
        return re1.hasEmptyString() && re2.hasEmptyString();
    }

    @Override
    public boolean isEmpty() {
        return re1.isEmpty() || re2.isEmpty();
    }

    @Override
    public RegExp removeEmptyString() {
        if (re1.hasEmptyString()) {
            final RegExp temp = Concat.build(re1.removeEmptyString(), re2);
            return Or.build(temp, re2.removeEmptyString());
        } else {
            return this;
        }
    }

    @Override
    public Duration minTimeoutValue() {
        final Duration re1MinTime = re1.minTimeoutValue();
        if (re1.hasEmptyString()) {
            final Duration re2MinTime = re2.minTimeoutValue();
            return min(re1MinTime, re2MinTime);
        } else {
            return re1MinTime;
        }
    }

    @Override
    public Set<Symbol> getFrontSymbols(final LinkedHashSet<Symbol> alphabet) {
        final Set<Symbol> frontSymbols = new HashSet<>(re1.getFrontSymbols(alphabet));
        if (re1.hasEmptyString()) {
            frontSymbols.addAll(re2.getFrontSymbols(alphabet));
        }
        return frontSymbols;
    }

    @Override
    public TimedAutomaton toTimedAutomaton(final LinkedHashSet<Symbol> alphabet) {

        final TimedAutomaton sub1 = re1.toTimedAutomaton(alphabet);
        final TimedAutomaton sub2 = re2.toTimedAutomaton(alphabet);
        final TimedAutomaton ta = new TimedAutomaton(sub1.getInitialState()).joinWith(sub1, false).joinWith(sub2, true);

        // Determine clocks to reset (remove any that are already being reset, except if towards sub2 initial state)
        final LinkedHashSet<Clock> toReset = new LinkedHashSet<>(sub2.getClocks());
        for (final Transition t : sub2.getTransitions()) {
            if (!t.to.equals(sub2.getInitialState())) {
                toReset.removeAll(t.toReset);
            }
        }

        // Create the concatenation transitions
        for (final Transition t : sub1.getTransitionsLeadingToAcceptingStates()) {
            ta.addTransition(new Transition(t.from, sub2.getInitialState(), t.symbol, t.guard, toReset));
        }
        return ta;
    }

    @Override
    public String toString() {
        return re1.toString() + "." + re2.toString();
    }
}
