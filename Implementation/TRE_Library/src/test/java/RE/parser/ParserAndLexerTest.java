package RE.parser;

import RE.lib.RegExp;
import RE.lib.basic.*;
import RE.lib.operators.Concat;
import RE.lib.operators.And;
import RE.lib.operators.Or;
import RE.lib.operators.Star;
import RE.lib.operators.TimeInterval;
import RE.parser.exceptions.*;
import RE.parser.util.CommentRemover;
import org.junit.*;

import java.time.Duration;
import java.util.*;

public class ParserAndLexerTest {

    private Parser parser;

    private static final Symbol A = new Symbol("A");
    private static final Symbol B = new Symbol("B");
    private static final Symbol C = new Symbol("C");
    private static final Symbol D = new Symbol("D");

    private static final Symbol A_u_v_w = new Symbol("A", Arrays.asList("u", "v", "w"));
    private static final Symbol B_u = new Symbol("B", Collections.singletonList("u"));
    private static final Symbol C_v_w = new Symbol("C", Arrays.asList("v", "w"));
    private static final Symbol D_x_y_z = new Symbol("D", Arrays.asList("x", "y", "z"));

    @Before
    public void setUp() {
        parser = new Parser(true, true);
    }

    @After
    public void tearDown() {
        parser = null;
    }

    @Test
    public void parse_goodBehaviour() {

        final ParsedRE parsedRE = parseSingleExpression("[p] A;");
        Assert.assertTrue(parsedRE.isPositive());
    }

    @Test
    public void parse_badBehaviour() {

        final ParsedRE parsedRE = parseSingleExpression("[n] A;");
        Assert.assertTrue(parsedRE.isNegative());
    }

    @Test
    public void parse_defaultBehaviour() {

        final ParsedRE parsedRE = parseSingleExpression("A;");
        Assert.assertTrue(parsedRE.isPositive());
    }

    @Test
    public void parse_anySymbol() {

        final String INPUT = "[p] ?;";
        final ParsedRE parsedRE = parseSingleExpression(INPUT);

        Assert.assertEquals(cleanString(INPUT), parsedRE.getOriginalInput());
        Assert.assertEquals(AnySymbol.INSTANCE, parsedRE.getRegExp());
        assertAlphabetContains(parsedRE);
    }

    @Test
    public void parse_emptySet() {

        final String INPUT = "[p] 0;";
        final ParsedRE parsedRE = parseSingleExpression(INPUT);

        Assert.assertEquals(cleanString(INPUT), parsedRE.getOriginalInput());
        Assert.assertEquals(EmptySet.INSTANCE, parsedRE.getRegExp());
        assertAlphabetContains(parsedRE);
    }

    @Test
    public void parse_emptyString() {

        final String INPUT = "[p] 1;";
        final ParsedRE parsedRE = parseSingleExpression(INPUT);

        Assert.assertEquals(cleanString(INPUT), parsedRE.getOriginalInput());
        Assert.assertEquals(EmptyString.INSTANCE, parsedRE.getRegExp());
        assertAlphabetContains(parsedRE);
    }

    @Test
    public void parse_symbol() {

        final String INPUT = "[p] A;";
        final ParsedRE parsedRE = parseSingleExpression(INPUT);

        Assert.assertEquals(cleanString(INPUT), parsedRE.getOriginalInput());
        Assert.assertEquals(A, parsedRE.getRegExp());
        assertAlphabetContains(parsedRE, A);
    }

    @Test
    public void parse_symbolWithParameters() {

        final String INPUT = "" +
                "typedef x: xClass; typedef y: yClass; typedef z: zClass; " +
                "[p] D(x, y, z);";
        final ParsedRE parsedRE = parseSingleExpression(INPUT);
        
        Assert.assertEquals(cleanString(INPUT), parsedRE.getOriginalInput());
        Assert.assertEquals(D_x_y_z, parsedRE.getRegExp());
        assertAlphabetContains(parsedRE, D_x_y_z);
    }

    @Test
    public void parse_symbolWithPackagedParameter() {

        final String INPUT = "" +
                "typedef u: java.uClass; " +
                "[p] B(u);";
        final ParsedRE parsedRE = parseSingleExpression(INPUT);

        Assert.assertEquals(cleanString(INPUT), parsedRE.getOriginalInput());
        Assert.assertEquals(B_u, parsedRE.getRegExp());
        assertAlphabetContains(parsedRE, B_u);
    }

    @Test
    public void parse_concat() {

        final String INPUT = "[p] A.B;";
        final ParsedRE parsedRE = parseSingleExpression(INPUT);

        Assert.assertEquals(cleanString(INPUT), parsedRE.getOriginalInput());
        Assert.assertEquals(Concat.build(A, B), parsedRE.getRegExp());
        assertAlphabetContains(parsedRE, A, B);
    }

    @Test
    public void parse_star() {

        final String INPUT = "[p] A*;";
        final ParsedRE parsedRE = parseSingleExpression(INPUT);

        Assert.assertEquals(cleanString(INPUT), parsedRE.getOriginalInput());
        Assert.assertEquals(Star.build(A), parsedRE.getRegExp());
        assertAlphabetContains(parsedRE, A);
    }

    @Test
    public void parse_and() {

        final String INPUT = "[p] A & B;";
        final ParsedRE parsedRE = parseSingleExpression(INPUT);

        Assert.assertEquals(cleanString(INPUT), parsedRE.getOriginalInput());
        Assert.assertEquals(And.build(A, B), parsedRE.getRegExp());
        assertAlphabetContains(parsedRE, A, B);
    }

    @Test
    public void parse_or() {

        final String INPUT = "[p] A + B;";
        final ParsedRE parsedRE = parseSingleExpression(INPUT);

        Assert.assertEquals(cleanString(INPUT), parsedRE.getOriginalInput());
        Assert.assertEquals(Or.build(A, B), parsedRE.getRegExp());
        assertAlphabetContains(parsedRE, A, B);
    }

    @Test
    public void parse_timeInterval_integerTimeValues() {

        final Duration dur1 = Duration.ofSeconds(5);
        final Duration dur2 = Duration.ofSeconds(10);

        final String INPUT = "[p] A[5,10];";
        final ParsedRE parsedRE = parseSingleExpression(INPUT);

        Assert.assertEquals(cleanString(INPUT), parsedRE.getOriginalInput());
        Assert.assertEquals(TimeInterval.build(A, dur1, dur2), parsedRE.getRegExp());
        assertAlphabetContains(parsedRE, A);
    }

    @Test
    public void parse_timeInterval_durationTimeValues() {

        final String dur1String = "P1DT2H3M4.5S";
        final String dur2String = "P2DT3H4M5.6S";
        final Duration dur1 = Duration.parse(dur1String);
        final Duration dur2 = Duration.parse(dur2String);

        final String INPUT = String.format("[p] A[%s,%s];", dur1String, dur2String);
        final ParsedRE parsedRE = parseSingleExpression(INPUT);

        Assert.assertEquals(cleanString(INPUT), parsedRE.getOriginalInput());
        Assert.assertEquals(TimeInterval.build(A, dur1, dur2), parsedRE.getRegExp());
        assertAlphabetContains(parsedRE, A);
    }

    @Test
    public void parse_timeInterval_infinityUpperLimit() {

        final Duration dur1 = Duration.ofSeconds(5);
        final String dur2 = "inf";

        final String INPUT = String.format("[p] A[%s,%s];", dur1, dur2);
        final ParsedRE parsedRE = parseSingleExpression(INPUT);

        Assert.assertEquals(cleanString(INPUT), parsedRE.getOriginalInput());
        Assert.assertEquals(TimeInterval.build(A, dur1, TimeInterval.INFINITY), parsedRE.getRegExp());
        assertAlphabetContains(parsedRE, A);
    }

    @Test
    public void parse_zeroAndOneInIntervalNotTreatedAsEmptySetAndEmptyString() {

        final Duration dur1 = Duration.ofSeconds(0);
        final Duration dur2 = Duration.ofSeconds(1);

        final String INPUT = "[p] A[0,1];";
        final ParsedRE parsedRE = parseSingleExpression(INPUT);

        Assert.assertEquals(cleanString(INPUT), parsedRE.getOriginalInput());
        Assert.assertEquals(TimeInterval.build(A, dur1, dur2), parsedRE.getRegExp());
        assertAlphabetContains(parsedRE, A);
    }

    @Test
    public void parse_alphabetExtension() {

        final String INPUT = "[p] A; {B, C, D}";
        final ParsedRE parsedRE = parseSingleExpression(INPUT);

        Assert.assertEquals(cleanString(INPUT), parsedRE.getOriginalInput());
        Assert.assertEquals(A, parsedRE.getRegExp());
        assertAlphabetContains(parsedRE, A, B, C, D);
    }

    @Test
    public void parse_alphabetExtensionWithParameters() {

        final String INPUT = "" +
                "typedef u: uClass; typedef v: vClass; typedef w: wClass; " +
                "[p] A(u, v, w); {B(u), C(v, w), D}";
        final ParsedRE parsedRE = parseSingleExpression(INPUT);

        Assert.assertEquals(cleanString(INPUT), parsedRE.getOriginalInput());
        Assert.assertEquals(A_u_v_w, parsedRE.getRegExp());
        assertAlphabetContains(parsedRE, A_u_v_w, B_u, C_v_w, D);
    }

    @Test
    public void parse_parantheses() {

        final String INPUT = "[p] ((((((A))))));";
        final ParsedRE parsedRE = parseSingleExpression(INPUT);

        Assert.assertEquals(cleanString(INPUT), parsedRE.getOriginalInput());
        Assert.assertEquals(A, parsedRE.getRegExp());
        assertAlphabetContains(parsedRE, A);
    }

    @Test(expected = IllegalNegationException.class)
    public void failedParse_negationAtUpperLevels() {
        parseErroneousExpression("[p] ~(A.B);");
    }

    @Test(expected = MissingSemicolonException.class)
    public void failedParse_missingSemicolon() {
        parseErroneousExpression("[p] A");
    }

    @Test(expected = InvalidPositivityException.class)
    public void failedParse_invalidPositivity1() {
        parseErroneousExpression("[z] A;");
    }

    @Test(expected = InvalidPositivityException.class)
    public void failedParse_invalidPositivity2() {
        parseErroneousExpression("[p) A;");
    }

    @Test(expected = UnexpectedLexemeException.class)
    public void failedParse_missingFirstOperand() {
        parseErroneousExpression("[p] +A;");
    }

    @Test(expected = UnexpectedLexemeException.class)
    public void failedParse_missingSecondOperand() {
        parseErroneousExpression("[p] A+;");
    }

    @Test(expected = UnexpectedLexemeException.class)
    public void failedParse_missingBinaryOperator() {
        parseErroneousExpression("[p] ((A)(B));");
        // Inner parentheses needed or otherwise "AB" assumed to be a symbol
        // Outer parentheses needed or otherwise MissingSemicolonException thrown
    }

    @Test(expected = MissingParameterDefinitionException.class)
    public void failedParse_symbolWithUndefinedParameter() {
        parseErroneousExpression("[p] A(u);");
    }

    @Test
    public void failedParse_multipleDefintionsOfClassInParamClassPair() {
        // No exception thrown since it is captured by the parse method itself
        Assert.assertEquals(0, new Parser(true, true).parse("typedef u: classA; typedef v: classA;").size());
    }

    @Test
    public void failedParse_multipleDefintionsOfParamInParamClassPair() {
        // No exception thrown since it is captured by the parse method itself
        Assert.assertEquals(0, new Parser(true, true).parse("typedef u: classA; typedef u: classB;").size());
    }

    @Test
    public void failedParse_doesNotIncludeInvalidExpression() {

        final String IN1 = "[p] A;";
        final String IN2 = "[z] B;";
        final String INPUT = IN1 + IN2;
        final List<ParsedRE> parseResult = new Parser(true, true).parse(INPUT);

        Assert.assertEquals(1, parseResult.size());
        final ParsedRE parsedRE = parseResult.get(0);

        Assert.assertEquals(cleanString(IN1), parsedRE.getOriginalInput());
        Assert.assertEquals(A, parsedRE.getRegExp());
        assertAlphabetContains(parsedRE, A);
    }

    @Test(expected = UnrecognizedTokenException.class)
    public void failedParse_unrecognizedToken() {

        final String INPUT = "£";
        new Parser(true, true).parse(INPUT);
    }

    @Test(expected = MissingSemicolonException.class)// just for coverage of Lexer.notTimeValue()
    public void failedParse_zeroAtEndOfInput() {

        final String INPUT = "[p] 0";
        parseErroneousExpression(INPUT);
    }

    @Test
    public void checkFirstSymbol_lotsOfOperatorsButAllWithFirstSymbolFullyParameterized() {

        final List<String> params = Arrays.asList("u", "v", "w");
        final Symbol A = new Symbol("A", params);
        final Symbol B = new Symbol("B", params);
        final Symbol C = new Symbol("C", params);
        final Symbol X = new Symbol("X", Collections.singletonList("u"));

        final Duration d1 = Duration.ZERO;
        final Duration d2 = Duration.ofSeconds(5);

        final RegExp re1 = And.build(A, Concat.build(B, X)); // A(u,v,w) + B(u,v,w).X(u)
        final RegExp re2 = And.build(TimeInterval.build(C, d1, d2), Star.build(A)); // (C(u,v,w))[0,5] + (A(u,v,w))*
        final RegExp re3 = Concat.build(EmptyString.INSTANCE, B); // 1.B(u,v,w)
        final RegExp re = Or.build(re1, Or.build(re2, re3)); // re1 + re2 + re3

        final LinkedHashSet<Symbol> alphabet = new LinkedHashSet<>(Arrays.asList(A, B, C, X));
        final LinkedHashMap<String, String> paramClassMap = new LinkedHashMap<>();
        paramClassMap.put("u", "U");
        paramClassMap.put("v", "V");
        paramClassMap.put("w", "W");

        final ParsedRE pre = new ParsedRE(null, true, re, alphabet, paramClassMap);
        Parser.checkFirstSymbol(pre);
    }

    @Test(expected = IllegalStartOfParamExpressionException.class)
    public void checkFirstSymbol_failsIfExpressionDoesNotStartWithFullyParameterizedSymbol() {

        final LinkedHashSet<Symbol> alphabet = new LinkedHashSet<>();
        alphabet.add(C_v_w);

        final LinkedHashMap<String, String> paramClassMap = new LinkedHashMap<>();
        paramClassMap.put("u", "uClass");
        paramClassMap.put("v", "vClass");
        paramClassMap.put("w", "wClass");

        Parser.checkFirstSymbol(new ParsedRE(null, true, C_v_w, alphabet, paramClassMap));
    }

    @Test
    public void removeComments_removesCommentsFromTheInput() {
        final String INPUT_WITH_COMMENTS = String.format("%s%s[%sp]%s A%s + %sB;%s %s",
                "/*comment1*/", "/*comment2*/", "/*comment3*/", "/*comment4*/",
                "/*comment5*/", "/*comment6*/", "/*comment7*/", "/*comment8*/");
        final String INPUT_PLAIN = "[p] A + B;";
        final ParsedRE parsedRE = parseSingleExpression(INPUT_WITH_COMMENTS);

        Assert.assertEquals(cleanString(INPUT_PLAIN), parsedRE.getOriginalInput());
        Assert.assertEquals(Or.build(A, B), parsedRE.getRegExp());
        assertAlphabetContains(parsedRE, A, B);
    }

    /**
     * Parses the input which should contain only one regular expression.
     */
    private ParsedRE parseSingleExpression(final String toParse) {
        final List<ParsedRE> parseResult = parser.parse(toParse);
        Assume.assumeTrue(parseResult.size() == 1);
        return parseResult.get(0);
    }

    /**
     * Parses the input which should contain an erroneous regular expression.
     */
    private void parseErroneousExpression(final String toParse) {
        parser.parseSingleExpression(toParse);
    }

    /**
     * Checks that the set contains (not more, not less) the specified strings.
     */
    private void assertAlphabetContains(ParsedRE parsedRE, Symbol... toCheck) {

        final LinkedHashSet<Symbol> alphabet = parsedRE.getAlphabet();
        if (alphabet.size() != toCheck.length) {
            Assert.fail();
        }
        for (final Symbol s : toCheck) {
            if (!alphabet.contains(s)) {
                Assert.fail();
            }
        }
    }

    /**
     * Removes any whitespace (excluding spaces) and comments from the string.
     */
    private String cleanString(final String input) {
        final String cleanedInput = input.replaceAll("(\\s)+", " ");
        return CommentRemover.removeComments(cleanedInput);
    }
}