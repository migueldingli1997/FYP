package RE.parser.exceptions;

public class IllegalStartOfParamExpressionException extends ParserException {

    public IllegalStartOfParamExpressionException() {
        super("A parameterized expression must start with an event having all parameters.");
    }
}
