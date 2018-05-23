package RE.lib;

import RE.lib.basic.Symbol;
import TA.lib.TimedAutomaton;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Abstract Regular Expression which serves as a template.
 */
public abstract class RegExp {

    /**
     * Computes the derivative of the regular expression with respect to a symbol
     * and the time units that elapsed since the start or the last derivative.
     *
     * @param sym The symbol with respect to which derivation will be performed.
     * @param dt  Time elapsed since the start or the last derivative.
     * @return The derivative of the regular expression.
     */
    public abstract RegExp getDerivative(final Symbol sym, final Duration dt);

    /**
     * Computes the derivative of the regular expression with respect to the time
     * units that elapsed since the start or the last derivative. This derivative
     * assumes that no symbol occurred in the time period.
     *
     * @param dt Time elapsed since the start or the last derivative.
     * @return The derivative of the regular expression.
     */
    public abstract RegExp getDerivative(final Duration dt);

    /**
     * Checks whether a trace of length zero matches with the regular expression.
     * In other words, this checks if the empty string is an element of the RE.
     *
     * @return True if regular expression contains the empty string.
     */
    public abstract boolean hasEmptyString();

    /**
     * Checks whether no trace can ever match with the regular expression.
     * In other words, this checks if the language accepted by the RE is {}.
     *
     * @return True if regular expression matches no trace.
     */
    public abstract boolean isEmpty();

    /**
     * If the empty string is currently an element of the regular expression, it
     * is removed, s.t. calling hasEmptyString() on the result will return false.
     *
     * @return {@code this} but with empty string removed from its language.
     */
    public abstract RegExp removeEmptyString();

    /**
     * Computes the minimum timeout value to be applied to setTimeout.
     * This value is equivalent to the minimum time by which the regular
     * expression is expecting a particular symbol.
     *
     * @return Minimum timeout value.
     */
    public abstract Duration minTimeoutValue();

    /**
     * Collects the set of symbols that are at the front of the regular
     * expression, i.e. those which can be affected by the absolute first
     * event of the alphabet that occurs.
     *
     * @param alphabet The full alphabet of the original parsed RE.
     * @return The set of symbols.
     */
    public abstract Set<Symbol> getFrontSymbols(final LinkedHashSet<Symbol> alphabet);

    /**
     * Recursively constructs and returns the Timed Automaton equivalent
     * to the regular expression represented by {@code this}.
     *
     * @param alphabet The full alphabet of the original parsed RE.
     * @return The constructed Timed Automaton.
     */
    public abstract TimedAutomaton toTimedAutomaton(final LinkedHashSet<Symbol> alphabet);

    @Override
    public abstract boolean equals(final Object obj);
}
