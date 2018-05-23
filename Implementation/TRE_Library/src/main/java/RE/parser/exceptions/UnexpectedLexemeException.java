package RE.parser.exceptions;

public class UnexpectedLexemeException extends ParserException {

    public UnexpectedLexemeException(final String expected, final String actual) {
        super("Expected " + expected + " but found " + actual + ".");
    }
}
