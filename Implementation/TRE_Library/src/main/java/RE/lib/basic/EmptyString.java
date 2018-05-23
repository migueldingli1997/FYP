package RE.lib.basic;

import RE.lib.RegExp;
import TA.lib.EmptySymbol;
import TA.lib.State;
import TA.lib.TimedAutomaton;
import TA.lib.Transition;

import java.time.Duration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Used to match a trace of length zero (or indicates end of a trace).
 * This singleton class is accessed using INSTANCE.
 */
public final class EmptyString extends BasicRE {

    public final static EmptyString INSTANCE = new EmptyString();

    private EmptyString() {
    }

    @Override
    public RegExp getDerivative(final Symbol sym, final Duration dt) {
        return EmptySet.INSTANCE;
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
        return EmptySet.INSTANCE;
    }

    @Override
    public Set<Symbol> getFrontSymbols(final LinkedHashSet<Symbol> alphabet) {
        return new HashSet<>();
    }

    @Override
    public TimedAutomaton toTimedAutomaton(final LinkedHashSet<Symbol> alphabet) {

        final TimedAutomaton ta = new TimedAutomaton();
        final State init = ta.getInitialState();
        final State acc = ta.addState(true);

        //final List<ClockCondition> guard = new ArrayList<>();
        //final Clock clock = ta.addClock();
        //final ClockCondition cond = new ClockCondition(clock, Duration.ZERO);
        //guard.add(cond);

        ta.addTransition(new Transition(init, acc, EmptySymbol.INSTANCE));//, guard));
        return ta;
    }

    @Override
    public String toString() {
        return "1";
    }
}
