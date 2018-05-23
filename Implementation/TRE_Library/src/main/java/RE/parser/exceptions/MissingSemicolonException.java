package RE.parser.exceptions;

import RE.parser.tokens.Token;

public class MissingSemicolonException extends ParserException {

    public MissingSemicolonException(final Token token) {
        super("Expected semicolon but found " + token + ".");
    }
}
