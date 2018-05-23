package RE.parser;

import RE.lib.operators.TimeInterval;
import RE.parser.exceptions.DummyParserException;
import RE.parser.exceptions.InvalidCommentException;
import RE.parser.exceptions.UnrecognizedTokenException;
import RE.parser.tokens.Token;
import RE.parser.tokens.TokenType;
import RE.parser.util.CommentRemover;

import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Lexer {

    private final static String INFINITY = "inf";
    private final static Matcher VALID_WORD = Pattern.compile("[a-zA-Z_][a-zA-Z_0-9]*").matcher("");
    private final static Matcher VALID_FLOAT = Pattern.compile("\\d*\\.?\\d*").matcher("");

    private final String input; // the input
    private int index; // current position in the string
    private int prevIndex; // previous value of index
    private Token latestToken; // the latest generated token

    /**
     * Constructs a lexer which will convert the given string into
     * a sequence of tokens; one token per call to getNextToken().
     *
     * @param input The string to be converted to tokens.
     * @param full True if all extra operations should be done.
     */
    Lexer(final String input, final boolean full) {
        if (full) {
            final String cleanedInput = input.trim().replaceAll("(\\s)+", " ");
            this.input = CommentRemover.removeComments(cleanedInput);
        } else {
            this.input = input;
        }
        this.index = 0;
        this.prevIndex = 0; // assumed to be 0
        this.latestToken = null;

        // Check for unopened or unclosed comments
        if (this.input.contains("/*") || this.input.contains("*/")) {
            throw new InvalidCommentException();
        }
    }

    /**
     * @return The previous position in the input.
     */
    int getPreviousIndex() {
        return prevIndex;
    }

    /**
     * @return Full original input.
     */
    String getInput() {
        return input;
    }

    /**
     * Reads enough characters from the input string to be able
     * to form the next token and returns it. For any invocations
     * after the string is exceeded, the method returns NULLTOKEN.
     *
     * @return The next token formed from the input string.
     */
    Token getNextToken() {

        // Update previous index
        prevIndex = index;

        // Skip any spaces
        while (index < input.length() && input.charAt(index) == ' ') {
            index++;
        }

        // If input end was reached, return null token
        if (index == input.length()) {
            return (latestToken = Token.NULLTOKEN);
        }

        // Obtain current character
        final char currChar = input.charAt(index);

        // Identify symbol
        index++; // assume that symbol will be identified
        switch (currChar) {
            case '(':
                return (latestToken = new Token(TokenType.TK_OpRnBr));
            case ')':
                return (latestToken = new Token(TokenType.TK_ClRnBr));
            case '[':
                return (latestToken = new Token(TokenType.TK_OpSqBr));
            case ']':
                return (latestToken = new Token(TokenType.TK_ClSqBr));
            case '{':
                return (latestToken = new Token(TokenType.TK_OpCurl));
            case '}':
                return (latestToken = new Token(TokenType.TK_ClCurl));
            case '~':
                return (latestToken = new Token(TokenType.TK_NOT));
            case '*':
                return (latestToken = new Token(TokenType.TK_STAR));
            case '&':
                return (latestToken = new Token(TokenType.TK_AND));
            case '+':
                return (latestToken = new Token(TokenType.TK_OR));
            case '.':
                return (latestToken = new Token(TokenType.TK_DOT));
            case '?':
                return (latestToken = new Token(TokenType.TK_ANY));
            case ',':
                return (latestToken = new Token(TokenType.TK_COMMA));
            case ';':
                return (latestToken = new Token(TokenType.TK_SEMICOLON));
            case ':':
                return (latestToken = new Token(TokenType.TK_COLON));
            case '0': {
                if (notNumberOrTimeValue()) {
                    return (latestToken = new Token(TokenType.TK_ZERO));
                } else break;
            }
            case '1': {
                if (notNumberOrTimeValue()) {
                    return (latestToken = new Token(TokenType.TK_ONE));
                } else break;
            }
        }
        index--; // nullify the previous assumption

        // Attempt to form a time duration (integer, ISO-8601 duration, or infinity)
        if (Character.isDigit(currChar) || currChar == 'P' || input.startsWith(INFINITY, index)) {
            try {
                return (latestToken = new Token(TokenType.TK_TIME, getDurationToken()));
            } catch (DummyParserException ignored) {
            }
        }

        // Attempt to form a word (event, java class/package, positivity, or keywords), starting with a letter or underscore
        try {
            final String word = getWord();
            if (word.equals("typedef")) {
                return (latestToken = new Token(TokenType.TK_TYPEDEF));
            } else {
                return (latestToken = new Token(TokenType.TK_WORD, word));
            }
        } catch (DummyParserException ignored) {
        }

        // If none of the above conditions were satisfied, the token is invalid
        throw new UnrecognizedTokenException(latestToken, currChar);
    }

    /**
     * Forms a duration expression of the general form PnDTnHnMn.nS.
     * This expected form is based on the ISO-8601 duration format.
     * If the duration is just a floating-point number (e.g. 12.5)
     * it is assumed that this is a duration in seconds (12.5 sec)
     *
     * @return An instance of Duration if a duration was successfully formed.
     * @throws DummyParserException If a duration was not formed.
     */
    private Duration getDurationToken() throws DummyParserException {

        // Find the end of the duration
        int index2 = index;
        while (index2 < input.length() && (Character.isLetterOrDigit(input.charAt(index2)) || input.charAt(index2) == '.')) {
            index2++;
        }

        // Create and return duration expression if successful
        final String timeDuration = input.substring(index, index2);
        final int backupIndex = index;
        try {
            index = index2;
            if (timeDuration.startsWith(INFINITY)) {
                return TimeInterval.INFINITY; // infinity duration
            } else if (VALID_FLOAT.reset(timeDuration).matches()) {
                return Duration.ofMillis((long) (Double.parseDouble(timeDuration) * 1000)); // converting to PTnS
            } else {
                return Duration.parse(timeDuration); // assumed to be in correct format
            }
        } catch (DateTimeParseException dtpe) {
            index = backupIndex;
            throw new DummyParserException();
        }
    }

    /**
     * Forms a word which is meant to represent infinity ("inf"), positive ("p"),
     * negative ("n"), or the name of an event consisting of a letter or underscore
     * as the first character and alphanumeric and/or underscores as the remainder.
     *
     * @return The resultant word (empty string if no word is found)
     * @throws DummyParserException If a word was not formed.
     */
    private String getWord() {

        final int start = index;
        final Matcher matcher = VALID_WORD.reset(input.substring(start));
        if (matcher.find()) {
            final int end = start + matcher.end();
            index = end;
            return input.substring(start, end);
        } else {
            throw new DummyParserException();
        }
    }

    /**
     * This method is meant to be used when checking whether
     * a '0' or a '1' are standalone (i.e. symbols) or are a
     * part of a time value starting with the digits 0 or 1
     * or are found within a time interval (',' or ']').
     *
     * @return True if next character is not a digit or decimal point, and is not part of time interval
     * (',' and ']'). True is also returned in the case that the character lies at the end of the input.
     */
    private boolean notNumberOrTimeValue() {

        // N.B: assumes that index was pre-incremented by 1 due to an assumption that a symbol will be
        // identified in the switch statement of getNextToken(). Thus, index is index of next character.
        if (index < input.length()) {
            final char nextChar = input.charAt(index);
            return !Character.isDigit(nextChar)
                    && nextChar != '.' // floating point number
                    && nextChar != ',' // time interval comma
                    && nextChar != ']'; // time interval closing bracket
        } else {
            return true;
        }
    }
}
