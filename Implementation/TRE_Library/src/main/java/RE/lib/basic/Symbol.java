package RE.lib.basic;

import RE.lib.RegExp;
import RE.lib.exceptions.NullParamsException;
import RE.lib.exceptions.NullSymbolException;
import TA.lib.State;
import TA.lib.TimedAutomaton;
import TA.lib.Transition;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Used to match a single specific String symbol.
 * This singleton class is accessed using INSTANCE.
 */
public class Symbol extends BasicRE {

    final String symbol; // The symbol which will be matched
    final List<String> params; // The list of parameters (possibly empty)

    public Symbol(final String symbol) {
        this(symbol, new ArrayList<>());
    }

    public Symbol(final String symbol, final List<String> params) {

        if (symbol == null) {
            throw new NullSymbolException();
        } else if (params == null) {
            throw new NullParamsException();
        } else {
            this.symbol = symbol;
            this.params = params;
        }
    }

    @Override
    public RegExp getDerivative(final Symbol sym, final Duration dt) {
        return this.equals(sym) ? EmptyString.INSTANCE : EmptySet.INSTANCE;
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
    public Set<Symbol> getFrontSymbols(LinkedHashSet<Symbol> alphabet) {
        return new HashSet<>(Collections.singletonList(this));
    }

    @Override
    public TimedAutomaton toTimedAutomaton(final LinkedHashSet<Symbol> alphabet) {

        final TimedAutomaton ta = new TimedAutomaton();
        final State init = ta.getInitialState();
        final State acc = ta.addState(true);
        ta.addTransition(new Transition(init, acc, this));
        return ta;
    }

    public String getSymbol() {
        return symbol;
    }

    public List<String> getParams() {
        return params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Symbol symbol1 = (Symbol) o;
        return Objects.equals(symbol, symbol1.symbol) &&
                Objects.equals(params, symbol1.params);
    }

    @Override
    public String toString() {
        if (params.isEmpty()) {
            return symbol;
        } else {
            return symbol + "(" + params.stream().collect(Collectors.joining(", ")) + ")";
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, params.toString());
    }
}
