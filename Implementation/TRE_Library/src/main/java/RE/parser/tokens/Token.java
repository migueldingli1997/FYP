package RE.parser.tokens;

import java.time.Duration;

public class Token {

    private final static Duration NULLVALUE = Duration.ofSeconds(-1);
    private final static String NULLEXEME = null;
    public final static Token NULLTOKEN = new Token(TokenType.TK_NULLTOKEN, NULLEXEME, NULLVALUE);

    private final TokenType type;
    private final String lexeme;
    private final Duration timeValue;

    private Token(final TokenType type, final String lexeme, final Duration timeValue) {
        this.type = type;
        this.lexeme = lexeme;
        this.timeValue = timeValue;
    }

    public Token(final TokenType type, final Duration value) {
        this(type, value.toString(), value);
    }

    public Token(final TokenType type, final String lexeme) {
        this(type, lexeme, NULLVALUE);
    }

    public Token(final TokenType type) {
        this(type, NULLEXEME, NULLVALUE);
    }

    public TokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public Duration getTimeValue() {
        return timeValue;
    }

    @Override
    public String toString() {
        return String.format("Token{type=%s, lexeme='%s', value=%s}", type, lexeme, timeValue);
    }
}
