package RE.parser.exceptions;

import RE.parser.tokens.Token;

public class UnrecognizedTokenException extends LexerException {

    public UnrecognizedTokenException(final Token latestToken, final char firstChar) {
        super("Unrecognized token encountered." +
                "\nLast successfully parsed token: " + latestToken +
                "\nFirst character of unrecognized token: \'" + firstChar + "\'");
    }
}
