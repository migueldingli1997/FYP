package larvaGenerator.exceptions;

public class IllegalParametersException extends RuntimeException {

    public IllegalParametersException(final String originalInput, final String event) {
        super("In expression \"" + originalInput + "\"\n" +
                "Another event has a set of parameters which is neither a subset nor a superset of the set of parameters of event " + event);
    }
}
