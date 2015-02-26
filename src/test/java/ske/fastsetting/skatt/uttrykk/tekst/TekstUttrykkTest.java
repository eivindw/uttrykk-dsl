package ske.fastsetting.skatt.uttrykk.tekst;

import org.junit.Test;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ske.fastsetting.skatt.uttrykk.tekst.KonstantTekstUttrykk.tekst;

public class TekstUttrykkTest {
    @Test
    public void test() {
        KonstantTekstUttrykk a = tekst("A");
        KonstantTekstUttrykk b = tekst("B");

        assertTrue(UttrykkContextImpl.beregne(a.er(a)).verdi());
        assertFalse(UttrykkContextImpl.beregne(a.er(b)).verdi());
        assertTrue(UttrykkContextImpl.beregne(a.erMindreEnn(b)).verdi());
    }
}