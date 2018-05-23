package RE.parser;

import RE.lib.RegExp;
import RE.lib.basic.Symbol;
import TA.lib.TimedAutomaton;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.LinkedHashSet;

@RunWith(MockitoJUnitRunner.class)
public class ParsedRETest {

    @Mock
    private RegExp regExp;

    private final static Symbol A = new Symbol("A");
    private final static Symbol B = new Symbol("B");
    private final static Symbol C = new Symbol("C");
    private final static Symbol X = new Symbol("X");
    private final static Symbol Y = new Symbol("Y");
    private final static Symbol Z = new Symbol("Z");

    @Mock
    private TimedAutomaton timAut;
    private final LinkedHashSet<Symbol> origAlphb = new LinkedHashSet<>(Arrays.asList(A, B, C));

    private ParsedRE parsedRE;
    private final LinkedHashSet<Symbol> extdAlphb = new LinkedHashSet<>(Arrays.asList(A, B, C, X, Y, Z));

    @Before
    public void setUp() {
        parsedRE = new ParsedRE(null, true, regExp, extdAlphb, null);
    }

    @After
    public void tearDown() {
        parsedRE = null;
    }

    @Test
    public void toTimedAutomaton_returnsRegExpAutomatonWithExtendedAlphabet() {
        Mockito.when(regExp.toTimedAutomaton(extdAlphb)).thenReturn(timAut);
        Mockito.when(timAut.getAlphabet()).thenReturn(origAlphb);

        final TimedAutomaton ta = parsedRE.toTimedAutomaton();
        Assert.assertArrayEquals(extdAlphb.toArray(), ta.getAlphabet().toArray());
    }
}