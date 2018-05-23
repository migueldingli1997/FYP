package RE.parser.exceptions;

public class MissingParameterDefinitionException extends RuntimeException {

    public MissingParameterDefinitionException(final String parameter) {
        super("Missing parameter definition for \"" + parameter + "\";\n" +
                "Please define it using a parameter-class pair \"typedef " + parameter + ": JAVA_CLASS;\" before the regular expression.");
    }
}
