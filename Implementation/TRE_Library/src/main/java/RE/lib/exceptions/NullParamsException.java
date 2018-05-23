package RE.lib.exceptions;

/**
 * Thrown when the parameters supplied when constructing a ParamsSymbol is null.
 */
public class NullParamsException extends IllegalArgumentException {

    public NullParamsException() {
        super("Attempted to construct symbol using a null parameters list.");
    }
}
