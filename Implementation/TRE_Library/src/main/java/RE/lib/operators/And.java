package RE.lib.operators;

import RE.lib.RegExp;
import RE.lib.basic.EmptySet;
import RE.lib.basic.EmptyString;
import RE.lib.basic.Symbol;
import RE.util.TupleStatesMap;
import TA.lib.EmptySymbol;
import TA.lib.State;
import TA.lib.TimedAutomaton;
import TA.lib.Transition;

import java.time.Duration;
import java.util.LinkedHashSet;

/**
 * Used when a trace must match both regular expressions.
 */
public class And extends BinaryOp {

    And(final RegExp re1, final RegExp re2) {
        super(re1, re2);
    }

    /**
     * Attempts to apply some reduction rules instead of just constructing an And regular expression.
     *
     * @param re1 Binary operator's first operand.
     * @param re2 Binary operator's second operand.
     * @return Resultant regular expression.
     */
    public static RegExp build(final RegExp re1, final RegExp re2) {

        if (re1.isEmpty() || re2.isEmpty()) { // e & f = 0 (if e or f empty)
            return EmptySet.INSTANCE;
        } else if (re1.equals(EmptyString.INSTANCE)) { // 1 & e = 1 (if 1 in e) otherwise 0
            return re2.hasEmptyString() ? EmptyString.INSTANCE : EmptySet.INSTANCE;
        } else if (re2.equals(EmptyString.INSTANCE)) { // e & 1 = 1 (if 1 in e) otherwise 0
            return re1.hasEmptyString() ? EmptyString.INSTANCE : EmptySet.INSTANCE;
        } else if (re1.equals(re2)) { // e & e = e
            return re1;
        } else if (re1 instanceof Symbol && re2 instanceof Symbol) { // a & b = 0 (a!=b since e&e=e)
            return EmptySet.INSTANCE;
        } else {
            return new And(re1, re2);
        }
    }

    @Override
    public RegExp getDerivative(final Symbol sym, final Duration dt) {
        return And.build(re1.getDerivative(sym, dt), re2.getDerivative(sym, dt));
    }

    @Override
    public RegExp getDerivative(final Duration dt) {
        return And.build(re1.getDerivative(dt), re2.getDerivative(dt));
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
        return And.build(re1.removeEmptyString(), re2.removeEmptyString());
    }

    @Override
    public TimedAutomaton toTimedAutomaton(final LinkedHashSet<Symbol> alphabet) {

        final TimedAutomaton sub1 = re1.toTimedAutomaton(alphabet);
        final TimedAutomaton sub2 = re2.toTimedAutomaton(alphabet);

        final TimedAutomaton ta = new TimedAutomaton();
        ta.getClocks().addAll(sub1.getClocks());
        ta.getClocks().addAll(sub2.getClocks());
        final State accState = ta.addState(true);

        final LinkedHashSet<Transition> tranSet1 = sub1.getTransitions();
        final LinkedHashSet<Transition> tranSet2 = sub2.getTransitions();
        final TupleStatesMap tsm = new TupleStatesMap(ta);
        tsm.setState(sub1.getInitialState(), sub2.getInitialState(), ta.getInitialState());

        for (final Transition t1 : tranSet1) {
            for (final Transition t2 : tranSet2) {
                final State from = tsm.getState(t1.from, t2.from);

                // Non-empty-symbol transitions
                if (!t1.isEmptySymbolTransition() & !t2.isEmptySymbolTransition() && t1.symbol.equals(t2.symbol)) {
                    final boolean toIsAcc1 = sub1.isAcceptingState(t1.to);
                    final boolean toIsAcc2 = sub2.isAcceptingState(t2.to);
                    t1.guard.addAll(t2.guard); // join guards
                    t1.toReset.addAll(t2.toReset); // join toResets
                    State to;
                    if (toIsAcc1 && toIsAcc2) {
                        to = accState; // both destination states are final
                    } else {
                        to = tsm.getState(t1.to, t2.to); // at least one destination state not final
                    }
                    ta.addTransition(new Transition(from, to, t1.symbol, t1.guard, t1.toReset));
                }
                // Empty-symbol transitions (from automaton 1)
                if (t1.isEmptySymbolTransition()) {
                    final State to = tsm.getState(t1.to, t2.from);
                    ta.addTransition(new Transition(from, to, EmptySymbol.INSTANCE, t1.guard, t1.toReset));
                }
                // Empty-symbol transitions (from automaton 2)
                if (t2.isEmptySymbolTransition()) {
                    final State to = tsm.getState(t1.from, t2.to);
                    ta.addTransition(new Transition(from, to, EmptySymbol.INSTANCE, t2.guard, t2.toReset));
                }
            }
        }
        return ta;
    }

    @Override
    public String toString() {
        return "(" + re1.toString() + " & " + re2.toString() + ")";
    }
}
