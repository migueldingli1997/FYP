package RE.lib.operators;

import RE.lib.RegExp;
import RE.lib.basic.EmptySet;
import RE.lib.basic.EmptyString;
import RE.lib.basic.Symbol;
import TA.lib.Clock;
import TA.lib.EmptySymbol;
import TA.lib.TimedAutomaton;
import TA.lib.Transition;

import java.time.Duration;
import java.util.LinkedHashSet;

/**
 * Used when a trace must match at least one of the regular expressions.
 */
public class Or extends BinaryOp {

    Or(final RegExp re1, final RegExp re2) {
        super(re1, re2);
    }

    /**
     * Attempts to apply some reduction rules instead of just constructing an Or regular expression.
     *
     * @param re1 Binary operator's first operand.
     * @param re2 Binary operator's second operand.
     * @return Resultant regular expression.
     */
    public static RegExp build(final RegExp re1, final RegExp re2) {

        final boolean re1IsEmpty = re1.isEmpty();
        final boolean re2IsEmpty = re2.isEmpty();
        if (re1IsEmpty && re2IsEmpty) { // e + f = 0 (if e and f empty)
            return EmptySet.INSTANCE;
        } else if (re1IsEmpty) { // f + e = e (if f empty)
            return re2;
        } else if (re2IsEmpty) { // e + f = e (if f empty)
            return re1;
        } else if (re1.equals(EmptyString.INSTANCE) && re2.hasEmptyString()) { // 1 + e = e (if 1 in e)
            return re2;
        } else if (re2.equals(EmptyString.INSTANCE) && re1.hasEmptyString()) { // e + 1 = e (if 1 in e)
            return re1;
        } else if (re1.equals(re2)) { // e + e = e
            return re1;
        } else {
            return new Or(re1, re2);
        }
    }

    @Override
    public RegExp getDerivative(final Symbol sym, final Duration dt) {
        return Or.build(re1.getDerivative(sym, dt), re2.getDerivative(sym, dt));
    }

    @Override
    public RegExp getDerivative(final Duration dt) {
        return Or.build(re1.getDerivative(dt), re2.getDerivative(dt));
    }

    @Override
    public boolean hasEmptyString() {
        return re1.hasEmptyString() || re2.hasEmptyString();
    }

    @Override
    public boolean isEmpty() {
        return re1.isEmpty() && re2.isEmpty();
    }

    @Override
    public RegExp removeEmptyString() {
        return Or.build(re1.removeEmptyString(), re2.removeEmptyString());
    }

    @Override
    public TimedAutomaton toTimedAutomaton(final LinkedHashSet<Symbol> alphabet) {

        final TimedAutomaton sub1 = re1.toTimedAutomaton(alphabet);
        final TimedAutomaton sub2 = re2.toTimedAutomaton(alphabet);
        final TimedAutomaton ta = new TimedAutomaton().joinWith(sub1, true).joinWith(sub2, true);

        // Determine clocks to reset in 1st empty symbol transition
        // (remove any that are already being reset, except if towards sub1 initial state)
        final LinkedHashSet<Clock> toReset1 = new LinkedHashSet<>(sub1.getClocks());
        for (final Transition t : sub1.getTransitions()) {
            if (!t.to.equals(sub1.getInitialState())) {
                toReset1.removeAll(t.toReset);
            }
        }

        // Determine clocks to reset in 2nd empty symbol transition
        // (remove any that are already being reset, except if towards sub2 initial state)
        final LinkedHashSet<Clock> toReset2 = new LinkedHashSet<>(sub2.getClocks());
        for (final Transition t : sub2.getTransitions()) {
            if (!t.to.equals(sub2.getInitialState())) {
                toReset2.removeAll(t.toReset);
            }
        }

        ta.addTransition(new Transition(ta.getInitialState(), sub1.getInitialState(), EmptySymbol.INSTANCE, toReset1));
        ta.addTransition(new Transition(ta.getInitialState(), sub2.getInitialState(), EmptySymbol.INSTANCE, toReset2));
        return ta;
    }

    @Override
    public String toString() {
        return "(" + re1.toString() + " + " + re2.toString() + ")";
    }
}
