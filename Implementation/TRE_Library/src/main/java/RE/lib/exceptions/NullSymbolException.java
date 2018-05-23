package RE.lib.exceptions;

/**
 * Thrown when the symbol supplied when constructing a Symbol is null.
 */
public class NullSymbolException extends IllegalArgumentException {

    public NullSymbolException() {
        super("Attempted to construct symbol using a null symbol string.");
    }
}
