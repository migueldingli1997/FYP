package RE.parser.exceptions;

public class InvalidCommentException extends LexerException {

    public InvalidCommentException() {
        super("The input contains an unopened ('*/' without '/*') or unclosed ('/*' without '*/') comment.");
    }
}
