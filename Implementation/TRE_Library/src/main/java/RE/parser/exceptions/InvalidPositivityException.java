package RE.parser.exceptions;

public class InvalidPositivityException extends ParserException {

    public InvalidPositivityException() {
        super("The positivity was invalid. Expected: [p] or [n].");
    }
}
