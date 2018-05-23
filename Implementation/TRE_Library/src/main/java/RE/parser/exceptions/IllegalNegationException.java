package RE.parser.exceptions;

public class IllegalNegationException extends ParserException {

    public IllegalNegationException() {
        super("Negation is only allowed on symbols. For an overall negated expression, change the positivity.");
    }
}
