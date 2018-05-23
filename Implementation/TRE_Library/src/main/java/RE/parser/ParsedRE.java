package RE.parser;

import RE.lib.RegExp;
import RE.lib.basic.Symbol;
import TA.lib.TimedAutomaton;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Represents a single parsed regular expression, which is positive
 * if it represents good system behaviour or, otherwise, negative.
 * An alphabet is also included with the parsed regular expression.
 */
public class ParsedRE {

    private final String originalInput;
    private final boolean positive;
    private final RegExp regExp;
    private final LinkedHashSet<Symbol> alphabet;
    private final LinkedHashMap<String, String> paramClassMap;

    ParsedRE(final String originalInput, final boolean positive, final RegExp regExp, final LinkedHashSet<Symbol> alphabet, final LinkedHashMap<String, String> paramClassMap) {
        this.originalInput = originalInput;
        this.positive = positive;
        this.regExp = regExp;
        this.alphabet = alphabet;
        this.paramClassMap = paramClassMap;
    }

    public String getOriginalInput() {
        return originalInput;
    }

    public boolean isPositive() {
        return positive;
    }

    public boolean isNegative() {
        return !isPositive();
    }

    public RegExp getRegExp() {
        return regExp;
    }

    public LinkedHashSet<String> getParameters() {
        return new LinkedHashSet<>(paramClassMap.keySet());
    }

    public LinkedHashSet<Symbol> getAlphabet() {
        return alphabet;
    }

    public Map<String, String> getParamClassMap() {
        return paramClassMap;
    }

    public TimedAutomaton toTimedAutomaton() {
        final TimedAutomaton ta = this.regExp.toTimedAutomaton(this.alphabet);
        ta.getAlphabet().addAll(this.alphabet); // to account for possible alphabet extension
        return ta;
    }

    @Override
    public String toString() {
        return "[" + (positive ? "p" : "n") + "] " + regExp + " with alphabet " + alphabet;
    }
}
