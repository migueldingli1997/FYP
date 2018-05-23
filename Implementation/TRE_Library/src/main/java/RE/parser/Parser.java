package RE.parser;

import RE.lib.RegExp;
import RE.lib.basic.*;
import RE.lib.operators.Concat;
import RE.lib.operators.And;
import RE.lib.operators.Or;
import RE.lib.operators.Star;
import RE.lib.operators.TimeInterval;
import RE.parser.exceptions.*;
import RE.parser.tokens.Token;
import RE.parser.tokens.TokenType;
import javafx.util.Pair;

import java.util.*;

import static RE.parser.tokens.TokenType.*;

public class Parser {

    private Lexer lexer; // the tokenizer for the string
    private Token lookahead; // the latest token from the lexer
    private LinkedHashSet<Symbol> alphabet; // set of unique events in the current expression
    private boolean containsNegation; // true if current expression contains negation
    private LinkedHashSet<String> parameters = null;

    private final boolean log; // whether extra outputs should be included.
    private final boolean full; // true if all extra operations should be done.

    public Parser(boolean log, boolean full) {
        this.log = log;
        this.full = full;
    }

    /**
     * Starts the parsing process, which parses one or more regular
     * expressions and returns a list containing each one of them.
     *
     * @param input The input containing one or more regular expressions.
     * @return Parse result containing the parsed regular expressions.
     */
    public List<ParsedRE> parse(final String input) {

        this.lexer = new Lexer(input, full);
        this.lookahead = lexer.getNextToken();

        final List<ParsedRE> parsedRES = new ArrayList<>();
        try {
            // Parse an expression
            while (lookahead.getType() != TK_NULLTOKEN) {
                final LinkedHashMap<String, String> paramClassMap = new LinkedHashMap<>();

                // Get start index
                final int startIndex = lexer.getPreviousIndex();

                // Parse zero or more typedefs
                while (lookahead.getType() == TK_TYPEDEF) {
                    final Pair<String, String> pcPair = parseParamClassPair();
                    if (paramClassMap.containsKey(pcPair.getKey())) {
                        throw new DuplicateParamClassPairException(pcPair, true);
                    } else if (paramClassMap.containsValue(pcPair.getValue())) {
                        throw new DuplicateParamClassPairException(pcPair, false);
                    } else {
                        paramClassMap.put(pcPair.getKey(), pcPair.getValue());
                        if (log) {
                            System.out.println("Successfully parsed param-class pair: " + pcPair.getKey() + " <=> " + pcPair.getValue());
                        }
                    }
                }

                // Parse the expression
                if (lookahead.getType() != TK_NULLTOKEN) {
                    parsedRES.add(parseSingleExpression(paramClassMap, startIndex));
                }
            }
        } catch (ParserException pe) {
            System.err.println("Parsing had to terminate prematurely due to an error.");
            System.err.println("Expressions successfully parsed: " + parsedRES.size());
            pe.printStackTrace();
        }
        return parsedRES;
    }

    /**
     * Checks that any possible first symbol in the expression is fully parameterized.
     * This makes use of the getFrontSymbols(...) method defined in the RE classes.
     *
     * @param parsedRE The parsed RE which will be checked.
     * @throws IllegalStartOfParamExpressionException If the expression has at least one non-
     *                                                fully-parameterized symbol at its front.
     */
    static void checkFirstSymbol(final ParsedRE parsedRE) throws IllegalStartOfParamExpressionException {

        final LinkedHashSet<String> allParameters = parsedRE.getParameters();
        if (allParameters.isEmpty()) {
            return;
        }

        for (final Symbol frontSymbol : parsedRE.getRegExp().getFrontSymbols(parsedRE.getAlphabet())) {
            if (frontSymbol.getParams().size() != allParameters.size()) {
                throw new IllegalStartOfParamExpressionException();
            }
        }
    }

    /**
     * Parses {@code typedef p : a1.a2.a3...aN.ClassName;}
     *
     * @return A pair consisting of {@code p} and {@code a1.a2.a3...aN.ClassName;}
     */
    private Pair<String, String> parseParamClassPair() {

        match(TK_TYPEDEF);
        final String theParam = match(TK_WORD).getLexeme();
        match(TK_COLON);
        final StringBuilder javaClassPath = new StringBuilder();
        javaClassPath.append(match(TK_WORD).getLexeme());
        while (lookahead.getType().equals(TK_DOT)) {
            match(TK_DOT);
            javaClassPath.append(".").append(match(TK_WORD).getLexeme());
        }
        match(TK_SEMICOLON);
        return new Pair<>(theParam, javaClassPath.toString());
    }

    /**
     * (Only for testing purposes!)
     * <p>
     * This method bypasses the parse(...) method so that thrown
     * exceptions can be detected and checked by the test methods.
     */
    ParsedRE parseSingleExpression(final String input) {

        this.lexer = new Lexer(input, true);
        this.lookahead = lexer.getNextToken();
        return parseSingleExpression(new LinkedHashMap<>());
    }

    /**
     * Parses a single regular expression with a prefixed positivity
     * "[p]" or "[n]" and an optional alphabet extension "{A,B,C,...}".
     * <p>
     * This calls the alternate parseSingleExpression method with the
     * start index argument set as an index obtained from the lexer.
     *
     * @param paramClassMap The param-class map to include in the resultant parsed expression.
     * @return The parsed regular expression.
     * @throws MissingSemicolonException If a semicolon was not found at the expected expression end.
     */
    private ParsedRE parseSingleExpression(final LinkedHashMap<String, String> paramClassMap) {
        return parseSingleExpression(paramClassMap, lexer.getPreviousIndex());
    }

    /**
     * Parses a single regular expression with a prefixed positivity
     * "[p]" or "[n]" and an optional alphabet extension "{A,B,C,...}".
     *
     * @param paramClassMap The param-class map to include in the resultant parsed expression.
     * @param startIndex    The lexer index at which the expression (including typedefs) started.
     * @return The parsed regular expression.
     * @throws MissingSemicolonException If a semicolon was not found at the expected expression end.
     */
    private ParsedRE parseSingleExpression(final LinkedHashMap<String, String> paramClassMap, final int startIndex) throws MissingSemicolonException {

        // Initialization
        alphabet = new LinkedHashSet<>();
        containsNegation = false;
        parameters = new LinkedHashSet<>();

        // Parse a regular expression, with an optional prefixed positivity [p] or [n]
        final boolean positive = parsePositivity();
        final RegExp regExp = parseOrExpr();

        // Parse semicolon and create result if successful
        try {
            match(TK_SEMICOLON);
        } catch (UnexpectedLexemeException ule) {
            throw new MissingSemicolonException(lookahead);
        }

        // Parse optional alphabet extension
        if (lookahead.getType() == TK_OpCurl) {
            parseAlphabetExtension();
        }

        // Check that all parameters were defined
        final Optional<String> param = parameters.stream().filter(p -> !paramClassMap.containsKey(p)).findFirst();
        if (param.isPresent()) {
            throw new MissingParameterDefinitionException(param.get());
        }

        // Create originalInput string
        final String originalInput;
        if (full) {
            final int endIndex = lexer.getPreviousIndex();
            originalInput = lexer.getInput().substring(startIndex, endIndex).trim();
        } else {
            originalInput = null;
        }

        // Print warning if contains negation and alphabet size is 1
        if (full && containsNegation && alphabet.size() == 1) {
            System.out.println("" +
                    "WARNING: input contains a negation and an alphabet size of 1.\n" +
                    "This means that the negated symbol will never be satisfied.");
        }

        // Create result
        final ParsedRE result = new ParsedRE(originalInput, positive, regExp, alphabet, paramClassMap);

        // Confirm that the first symbol is fully parameterized
        if (full) {
            checkFirstSymbol(result);
        }

        // Print success message and return result
        if (log) {
            System.out.println("Successfully parsed expression: " + result.toString());
        }
        return result;
    }

    /**
     * Parses the positive prefix of the regular expression which indicates
     * whether the expression represents good "[p]" or bad "[n]" behaviour.
     *
     * @return The resultant positivity.
     */
    private boolean parsePositivity() throws InvalidPositivityException {

        // If expression does not start with positivity, assume positive
        if (!lookahead.getType().equals(TK_OpSqBr)) {
            return true;
        }

        // Otherwise, try to parse the positivity
        try {
            match(TK_OpSqBr);
            final String temp = match(TK_WORD).getLexeme();
            match(TK_ClSqBr);

            if (temp.equalsIgnoreCase("p") || temp.equalsIgnoreCase("n")) {
                return temp.equalsIgnoreCase("p");
            } else {
                throw new InvalidPositivityException();
            }
        } catch (final UnexpectedLexemeException ule) {
            throw new InvalidPositivityException();
        }
    }

    /**
     * Parses an "OR expression" consisting of an "AND expression" optionally
     * followed by an OR and an "OR expression". If the OR operator is found,
     * the resultant expression will be an OR between the two expressions.
     * <p>
     * "OR expression" = "AND expression" (+ "OR expression")
     *
     * @return Resultant "OR expression".
     */
    private RegExp parseOrExpr() {

        final RegExp re1 = parseAndExpr();
        if (lookahead.getType() == TK_OR) {
            match(TK_OR);
            final RegExp re2 = parseOrExpr();
            return Or.build(re1, re2);
        } else {
            return re1;
        }
    }

    /**
     * Parses an "AND expression" consisting of a "Concat expression" optionally
     * followed by an AND and an "AND expression". If the AND operator is found,
     * the resultant expression will be an AND between the two expressions.
     * <p>
     * "AND expression" = "Concat expression" (& "AND expression")
     *
     * @return Resultant "AND expression".
     */
    private RegExp parseAndExpr() {

        final RegExp re1 = parseConcatExpr();
        if (lookahead.getType() == TK_AND) {
            match(TK_AND);
            final RegExp re2 = parseAndExpr();
            return And.build(re1, re2);
        } else {
            return re1;
        }
    }

    /**
     * Parses a "Concat expression" consisting of a "Factor expression" optionally
     * followed by a concat. and a "Concat expression". If the Concat operator is found,
     * the resultant expression will be a concatenation between the two expressions.
     * <p>
     * "Concat expression" = "Factor expression" (. "Concat expression")
     *
     * @return Resultant "Concat expression".
     */
    private RegExp parseConcatExpr() {

        final RegExp re1 = parseFactorExpr();
        if (lookahead.getType() == TK_DOT) {
            match(TK_DOT);
            final RegExp re2 = parseConcatExpr();
            return Concat.build(re1, re2);
        } else {
            return re1;
        }
    }

    /**
     * Parses a "Factor expression" which take many forms: an "OR expression" in
     * parentheses, a "Not expression", or an event (including ?, 1, and 0). The
     * expression may also be followed by an interval operator.
     *
     * @return Resultant "Factor expression".
     */
    private RegExp parseFactorExpr() {

        // The factor to return, without star or interval operators
        RegExp toReturn = parseFactor();

        // Allows for zero or more star and interval operators
        while (true) {
            switch (lookahead.getType()) {
                case TK_STAR:
                    toReturn = parseStar(toReturn);
                    continue;
                case TK_OpSqBr:
                    toReturn = parseInterval(toReturn);
                    continue;
                default:
                    return toReturn;
            }
        }
    }

    /**
     * Parses a "Factor" which take many forms: an "OR expression" in par-
     * entheses, a "Not expression", or an event (including ?, 1, and 0).
     *
     * @return Resultant "Factor".
     */
    private RegExp parseFactor() {

        RegExp toReturn;
        switch (lookahead.getType()) {
            case TK_OpRnBr: {
                match(TK_OpRnBr);
                toReturn = parseOrExpr();
                match(TK_ClRnBr);
                break;
            }
            case TK_NOT: {
                toReturn = parseNot();
                containsNegation = true;
                break;
            }
            case TK_WORD: {
                final Symbol symbolToReturn = parseSymbolWithPossibleParameters();
                alphabet.add(symbolToReturn);
                toReturn = symbolToReturn;
                break;
            }
            case TK_ANY: {
                match(TK_ANY);
                toReturn = AnySymbol.INSTANCE;
                break;
            }
            case TK_ZERO: {
                match(TK_ZERO);
                toReturn = EmptySet.INSTANCE;
                break;
            }
            case TK_ONE: {
                match(TK_ONE);
                toReturn = EmptyString.INSTANCE;
                break;
            }
            default:
                throw new UnexpectedLexemeException("factor", lookahead.toString());
        }
        return toReturn;
    }

    /**
     * Parses a "Not expression" consisting of the NOT operator followed
     * followed by a "Factor expression" operand that must be a symbol.
     *
     * @return Resultant "Not expression".
     * @throws IllegalNegationException If the operand is not a symbol.
     */
    private RegExp parseNot() {

        match(TK_NOT);
        final RegExp operand = parseFactor();

        // Negation only allowed at bottom-most level
        if (operand instanceof Symbol) {
            return new NegatedSymbol((Symbol) operand);
        } else {
            throw new IllegalNegationException();
        }
    }

    /**
     * Parses a "Star expression" but requires the operand to
     * be supplied since this is found before the star operator.
     * This method only checks that the operator is present.
     *
     * @param regExp The operand for the star operator.
     * @return Resultant "Star expression"
     */
    private RegExp parseStar(final RegExp regExp) {

        match(TK_STAR);
        return Star.build(regExp);
    }

    /**
     * Parses an "Interval expression" but requires the operand
     * to be supplied since this is found before the operator.
     * This method only checks that the interval is present.
     *
     * @param regExp The operand for the interval operator.
     * @return Resultant "Interval expression"
     */
    private RegExp parseInterval(final RegExp regExp) {

        match(TK_OpSqBr);

        final Token t1 = match(TK_TIME);
        match(TK_COMMA);
        final Token t2 = match(TK_TIME);

        match(TK_ClSqBr);
        return TimeInterval.build(regExp, t1.getTimeValue(), t2.getTimeValue());
    }

    /**
     * Parses an extension to the alphabet. This is important when there are
     * symbols that are not in the regular expression but should still be
     * detected. The extension has the following form: {symbol, symbol, ...}
     */
    private void parseAlphabetExtension() {

        match(TK_OpCurl);
        alphabet.add(parseSymbolWithPossibleParameters());
        while (lookahead.getType() != TK_ClCurl) {
            match(TK_COMMA);
            alphabet.add(parseSymbolWithPossibleParameters());
        }
        match(TK_ClCurl);
    }

    /**
     * Parses a symbol along with an optional one or more parameters which
     * indicate that the symbol will represent a parameterized event.
     */
    private Symbol parseSymbolWithPossibleParameters() {

        final Token temp = match(TK_WORD);
        if (lookahead.getType() == TK_OpRnBr) {
            return new Symbol(temp.getLexeme(), parseSymbolParameters());
        } else {
            return new Symbol(temp.getLexeme());
        }
    }

    /**
     * Parses one or more parameters for a symbol to indicate
     * that the symbol will represent a parameterized event.
     */
    private List<String> parseSymbolParameters() {

        final List<String> params = new ArrayList<>();
        String param;

        match(TK_OpRnBr);
        param = match(TK_WORD).getLexeme();
        params.add(param);
        parameters.add(param);
        while (lookahead.getType() != TK_ClRnBr) {
            match(TK_COMMA);
            param = match(TK_WORD).getLexeme();
            params.add(param);
            parameters.add(param);
        }
        match(TK_ClRnBr);

        return params;
    }

    /**
     * Checks that the type of the next token in consideration matches with the
     * expected type for the next token. Then, a new token is obtained and
     * assigned to lookahead. The old lookahead is returned for any further use.
     *
     * @param tkType The expected type for the next token.
     * @return The previous token stored by lookahead.
     */
    private Token match(final TokenType tkType) throws UnexpectedLexemeException {

        if (lookahead.getType() == tkType) {
            final Token previous = lookahead;
            lookahead = lexer.getNextToken();
            return previous;
        } else {
            throw new UnexpectedLexemeException("token of type " + tkType, lookahead.toString());
        }
    }
}
