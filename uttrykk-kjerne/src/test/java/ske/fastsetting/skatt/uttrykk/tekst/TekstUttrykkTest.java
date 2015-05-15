package ske.fastsetting.skatt.uttrykk.tekst;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ske.fastsetting.skatt.uttrykk.tekst.TekstKonstantUttrykk.tekst;

import org.junit.Test;

import ske.fastsetting.skatt.uttrykk.TestUttrykkContext;

public class TekstUttrykkTest {
    @Test
    public void test() {
        TekstKonstantUttrykk a = tekst("A");
        TekstKonstantUttrykk b = tekst("B");

        assertTrue(TestUttrykkContext.beregne(a.er(a)).verdi());
        assertFalse(TestUttrykkContext.beregne(a.er(b)).verdi());
        assertTrue(TestUttrykkContext.beregne(a.erMindreEnn(b)).verdi());
    }
}
