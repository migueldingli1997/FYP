package RE.lib.basic;

import RE.lib.RegExp;
import TA.lib.TimedAutomaton;

import java.time.Duration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Used to match a null trace of symbols.
 * This singleton class is accessed using INSTANCE.
 */
public final class EmptySet extends BasicRE {

    public final static EmptySet INSTANCE = new EmptySet();

    private EmptySet() {
    }

    @Override
    public RegExp getDerivative(final Symbol sym, final Duration dt) {
        return INSTANCE;
    }

    @Override
    public boolean hasEmptyString() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public RegExp removeEmptyString() {
        return INSTANCE;
    }

    @Override
    public Set<Symbol> getFrontSymbols(final LinkedHashSet<Symbol> alphabet) {
        return new HashSet<>();
    }

    @Override
    public TimedAutomaton toTimedAutomaton(final LinkedHashSet<Symbol> alphabet) {
        return new TimedAutomaton();
    }

    @Override
    public String toString() {
        return "0";
    }
}
