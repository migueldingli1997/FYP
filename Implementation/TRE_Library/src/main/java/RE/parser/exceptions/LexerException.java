package RE.parser.exceptions;

public abstract class LexerException extends RuntimeException {

    LexerException(String message) {
        super(message);
    }
}
