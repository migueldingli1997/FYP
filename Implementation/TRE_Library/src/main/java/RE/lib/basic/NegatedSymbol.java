package RE.lib.basic;

import RE.lib.RegExp;
import TA.lib.State;
import TA.lib.TimedAutomaton;
import TA.lib.Transition;

import java.time.Duration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Used to match any single symbol of type T besides the operand
 * symbol. This singleton class is accessed using INSTANCE.
 */
public class NegatedSymbol extends BasicRE {

    final Symbol symbol;

    public NegatedSymbol(final Symbol symbol) {
        this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public RegExp getDerivative(final Symbol sym, final Duration dt) {
        if (symbol.equals(sym)) {
            return EmptySet.INSTANCE;
        } else {
            return EmptyString.INSTANCE;
        }
    }

    @Override
    public boolean hasEmptyString() {
        return false;
    }

    @Override // This assumes that size of alphabet > 1 (Warning is printed out by Parser if size of alphabet = 1)
    public boolean isEmpty() {
        return false;
    }

    @Override
    public RegExp removeEmptyString() {
        return this;
    }

    @Override
    public Set<Symbol> getFrontSymbols(final LinkedHashSet<Symbol> alphabet) {
        final Set<Symbol> frontSymbols = new HashSet<>(alphabet);
        frontSymbols.remove(this.symbol);
        return frontSymbols;
    }

    @Override
    public TimedAutomaton toTimedAutomaton(final LinkedHashSet<Symbol> alphabet) {

        final TimedAutomaton ta = new TimedAutomaton();
        final State init = ta.getInitialState();
        final State acc = ta.addState(true);
        alphabet.stream().filter(a -> !a.equals(symbol)).forEach(a -> ta.addTransition(new Transition(init, acc, a)));
        return ta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final NegatedSymbol that = (NegatedSymbol) o;
        return Objects.equals(symbol, that.symbol);
    }

    @Override
    public String toString() {
        return "~" + symbol;
    }
}
