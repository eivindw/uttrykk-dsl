package ske.fastsetting.skatt.uttrykk.multibelop;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.multibelop.MultiKroneUttrykk.kr;

import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.TestUttrykkContext;
import ske.fastsetting.skatt.uttrykk.multitall.MultiTallUttrykk;

public class MultiBelopMultiplisertMedMultiTallTest {
    @Test
    public void skalMultiplisereFlere() {
        MultiBelopUttrykk<String> divdent = kr(30, "A").pluss(kr(60, "B"));
        MultiBelopUttrykk<String> divisor = kr(3, "A").pluss(kr(10, "B"));

        MultiTallUttrykk<String> kvotientUttrykk = divdent.hverDividertMed(divisor);

        MultiBelopUttrykk<String> faktor = kr(2, "A").pluss(kr(4, "B"));

        MultiBelopUttrykk<String> produktUttrykk = faktor.hverMultiplisertMed(kvotientUttrykk);


        MultiBelop<String> produkt = TestUttrykkContext.verdiAv(produktUttrykk);


        assertEquals(Belop.kr(20), produkt.get("A"));
        assertEquals(Belop.kr(24), produkt.get("B"));
    }

}
