package RE.lib.exceptions;

/**
 * Thrown when at least one of the limits of the interval
 * when constructing a time interval operator is negative.
 */
public class NegativeTimeException extends IllegalArgumentException {

    public NegativeTimeException() {
        super("Attempted to pass negative time as an argument.");
    }
}
