package RE.parser.tokens;

public enum TokenType {
    TK_OpRnBr,      // Opening round  bracket, generally used for grouping
    TK_ClRnBr,      // Closing round  bracket, generally used for grouping
    TK_OpSqBr,      // Opening square bracket, used for time intervals
    TK_ClSqBr,      // Closing square bracket, used for time intervals
    TK_OpCurl,      // Opening curly bracket, used for alphabet extensions
    TK_ClCurl,      // Closing curly bracket, used for alphabet extensions
    TK_NOT,         // Operator: NOT (~)
    TK_STAR,        // Operator: STAR (*)
    TK_AND,         // Operator: AND (&)
    TK_OR,          // Operator: OR (+)
    TK_DOT,         // Operator: CONCAT (.) or java package delimiter (for typedef)
    TK_ZERO,        // 0 symbol, where [[0]] = {}
    TK_ONE,         // 1 symbol, where [[1]] = {λ}
    TK_ANY,         // ? symbol, where [[?]] = Σ (the entire alphabet)
    TK_WORD,        // Specific event, positivity (p/n), java package/class name (for typedef)
    TK_SEMICOLON,   // Semicolon, used to distinguish between expressions
    TK_COMMA,       // Comma, used to separate the lower and upper limits in a time interval
    TK_TIME,        // Time interval limit (integer, ISO-8601 duration, or infinity)
    TK_TYPEDEF,     // Represents the typedef keyword, used in a typedef statement
    TK_COLON,       // Colon, used to separate the parameter and class in a typedef statement
    TK_NULLTOKEN    // Dummy token returned when the input was completely parsed
}