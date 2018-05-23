package TA.lib;

import RE.lib.basic.Symbol;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

public class Transition {

    public final State from;
    public final List<ClockCondition> guard;
    public final LinkedHashSet<Clock> toReset;
    public final Symbol symbol;
    public final State to;

    public Transition(final State from, final State to, final Symbol symbol) {
        this(from, to, symbol, new ArrayList<>(), new LinkedHashSet<>());
    }

    public Transition(final State from, final State to, final Symbol symbol, final LinkedHashSet<Clock> toReset) {
        this(from, to, symbol, new ArrayList<>(), toReset);
    }

    public Transition(final State from, final State to, final Symbol symbol, final List<ClockCondition> guard) {
        this(from, to, symbol, guard, new LinkedHashSet<>());
    }

    public Transition(final State from, final State to, final Symbol symbol, final List<ClockCondition> guard, final LinkedHashSet<Clock> toReset) {
        this.from = from;
        this.to = to;
        this.guard = guard;
        this.toReset = toReset;
        this.symbol = symbol;
    }

    public boolean hasGuardOrResets() {
        return !hasNoGuardAndNoResets();
    }

    public boolean hasNoGuardAndNoResets() {
        return guard.isEmpty() && toReset.isEmpty();
    }

    public boolean isEmptySymbolTransition() {
        return symbol.equals(EmptySymbol.INSTANCE);
    }

    public String toEdgeString() {
        final String symbolStr = (symbol instanceof EmptySymbol) ? "\'\'" : symbol.toString();
        final String slash1 = !guard.isEmpty() | !toReset.isEmpty() ? "\\" : "";
        final String guardStr = !guard.isEmpty() ? guard.toString() : "";
        final String slash2 = !toReset.isEmpty() ? "\\" : "";
        final String toResetStr = !toReset.isEmpty() ? toReset.toString() : "";
        return String.format("%s%s%s%s%s", symbolStr, slash1, guardStr, slash2, toResetStr);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final Transition that = (Transition) o;
        return Objects.equals(from, that.from) &&
                Objects.equals(guard, that.guard) &&
                Objects.equals(toReset, that.toReset) &&
                Objects.equals(symbol, that.symbol) &&
                Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, guard, toReset, symbol, to);
    }
}
