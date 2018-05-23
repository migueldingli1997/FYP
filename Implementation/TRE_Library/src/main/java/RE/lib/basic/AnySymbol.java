package RE.lib.basic;

import RE.lib.RegExp;
import TA.lib.State;
import TA.lib.TimedAutomaton;
import TA.lib.Transition;

import java.time.Duration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Used to match any single symbol of the alphabet.
 * This singleton class is accessed using INSTANCE.
 */
public final class AnySymbol extends BasicRE {

    public final static AnySymbol INSTANCE = new AnySymbol();

    private AnySymbol() {
    }

    @Override
    public RegExp getDerivative(final Symbol sym, final Duration dt) {
        return EmptyString.INSTANCE;
    }

    @Override
    public boolean hasEmptyString() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public RegExp removeEmptyString() {
        return this;
    }

    @Override
    public Set<Symbol> getFrontSymbols(final LinkedHashSet<Symbol> alphabet) {
        return new HashSet<>(alphabet);
    }

    @Override
    public TimedAutomaton toTimedAutomaton(final LinkedHashSet<Symbol> alphabet) {

        final TimedAutomaton ta = new TimedAutomaton();
        final State init = ta.getInitialState();
        final State acc = ta.addState(true);
        alphabet.forEach(a -> ta.addTransition(new Transition(init, acc, a)));
        return ta;
    }

    @Override
    public String toString() {
        return "?";
    }
}
