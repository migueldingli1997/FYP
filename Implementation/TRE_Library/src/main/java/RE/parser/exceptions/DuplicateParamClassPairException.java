package RE.parser.exceptions;

import javafx.util.Pair;

public class DuplicateParamClassPairException extends ParserException {

    public DuplicateParamClassPairException(final Pair<String, String> pcPair, final boolean duplicateParam) {
        super(duplicateParam ?
                "Found multiple typedefs for parameter \'" + pcPair.getKey() + "\'." :
                "Found multiple typedefs for class \'" + pcPair.getValue() + "\'."
        );
    }
}
