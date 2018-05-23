package RE.parser.exceptions;

public abstract class ParserException extends RuntimeException {

    ParserException(String message) {
        super(message);
    }
}
