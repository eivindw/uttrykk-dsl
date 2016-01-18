package ske.fastsetting.skatt.uttrykk.multibelop;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.TestUttrykkContext;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.TestUttrykkContext.verdiAv;
import static ske.fastsetting.skatt.uttrykk.multibelop.MulitBelopStorsteAvHverUttrykk.storsteAvHver;
import static ske.fastsetting.skatt.uttrykk.multibelop.MultiKroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.multibelop.MultiKroneUttrykk.kr0;

/**
 * Created by x00jen on 04.11.15.
 */
public class MultiBelopStorsteAvHverUttrykkTest {
    @Test
    public void skalSammenlikneEnTilEn() {
        MultiBelopUttrykk<String> a = kr(5, "A").pluss(kr(7, "B"));
        MultiBelopUttrykk<String> b = kr(7, "A").pluss(kr(3, "B"));

        MultiBelop<String> resultat = verdiAv(storsteAvHver(a, b));

        assertEquals(Belop.kr(7),resultat.get("A"));
        assertEquals(Belop.kr(7), resultat.get("B"));
    }

    @Test
    public void skalSammenlikneUtenOverlapp() {
        MultiBelopUttrykk<String> a = kr(5, "A").pluss(kr(7, "B"));
        MultiBelopUttrykk<String> b = kr(7, "C").pluss(kr(3, "D"));

        MultiBelop<String> resultat = verdiAv(storsteAvHver(a, b));

        assertEquals(Belop.kr(5),resultat.get("A"));
        assertEquals(Belop.kr(7),resultat.get("B"));
        assertEquals(Belop.kr(7),resultat.get("C"));
        assertEquals(Belop.kr(3),resultat.get("D"));

    }

    @Test
    public void skalSammenlikneMedDelvisOverlapp() {
        MultiBelopUttrykk<String> a = kr(5, "A").pluss(kr(-3, "B"));
        MultiBelopUttrykk<String> b = kr(-2, "B").pluss(kr(3, "C"));

        MultiBelop<String> resultat = verdiAv(storsteAvHver(a, b));

        assertEquals(Belop.kr(5),resultat.get("A"));
        assertEquals(Belop.kr(-2),resultat.get("B"));
        assertEquals(Belop.kr(3),resultat.get("C"));

    }

    @Test
    public void skalSammenlikneMedTom() {
        MultiBelopUttrykk<String> a = kr(5, "A").pluss(kr(-3, "B"));
        MultiBelopUttrykk<String> b = kr0();

        MultiBelop<String> resultat = verdiAv(storsteAvHver(a, b));

        assertEquals(Belop.kr(5),resultat.get("A"));
        assertEquals(Belop.kr(-3),resultat.get("B"));
    }

    @Test
    public void skalSammenlikneTomMedTom() {
        MultiBelopUttrykk<String> a = kr0();
        MultiBelopUttrykk<String> b = kr0();

        MultiBelop<String> resultat = verdiAv(storsteAvHver(a, b));

        assertEquals(0,resultat.steder().size());
    }

}
