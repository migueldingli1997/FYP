package RE.lib.operators;

import RE.lib.RegExp;
import RE.lib.basic.BasicRE;
import RE.lib.basic.EmptyString;
import RE.lib.basic.Symbol;
import TA.lib.Clock;
import TA.lib.EmptySymbol;
import TA.lib.TimedAutomaton;
import TA.lib.Transition;

import java.time.Duration;
import java.util.LinkedHashSet;

/**
 * Used when a trace must match the regular expression repeatedly.
 */
public class Star extends UnaryOp {

    Star(final RegExp re) {
        super(re);
    }

    /**
     * Attempts to apply some reduction rules instead of just constructing a Star regular expression.
     *
     * @param re Unary operator's operand.
     * @return Resultant regular expression.
     */
    public static RegExp build(final RegExp re) {

        if (re.isEmpty()) { // e* = 1 (if e is empty)
            return EmptyString.INSTANCE;
        } else if (re.equals(EmptyString.INSTANCE)) { // 1* = 1 (since 1e=e1=e)
            return EmptyString.INSTANCE;
        } else if (re instanceof Star) { // e** = e*
            return re;
        } else {
            return new Star(re);
        }
    }

    @Override
    public RegExp getDerivative(final Symbol sym, final Duration dt) {
        return Concat.build(re.getDerivative(sym, dt), this);
    }

    @Override
    public RegExp getDerivative(final Duration dt) {
        return Or.build(Concat.build(re.getDerivative(dt), this), EmptyString.INSTANCE);
    }

    @Override
    public boolean hasEmptyString() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public RegExp removeEmptyString() {
        return Concat.build(re.removeEmptyString(), this);
    }

    @Override
    public TimedAutomaton toTimedAutomaton(final LinkedHashSet<Symbol> alphabet) {

        final TimedAutomaton ta = re.toTimedAutomaton(alphabet);

        // Determine clocks to reset (remove any that are already being reset, except if towards sub1 initial state)
        final LinkedHashSet<Clock> toReset = new LinkedHashSet<>(ta.getClocks());
        for (final Transition t : ta.getTransitions()) {
            if (!t.to.equals(ta.getInitialState())) {
                toReset.removeAll(t.toReset);
            }
        }

        // Create the looping transitions
        for (final Transition t : ta.getTransitionsLeadingToAcceptingStates()) {
            ta.addTransition(new Transition(t.from, ta.getInitialState(), t.symbol, t.guard, toReset));
        }

        // Create the empty string transition
        ta.addTransition(new Transition(ta.getInitialState(), ta.addState(true), EmptySymbol.INSTANCE));

        return ta;
    }

    @Override
    public String toString() {
        if (re instanceof BasicRE) {
            return re.toString() + "*";
        } else {
            return "(" + re.toString() + ")*";
        }
    }
}
