package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.belop.BelopGrenseUttrykk.begrens;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.multibelop.MultiKroneUttrykk.kr;


import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.TestUttrykkContext;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.multibelop.MultiBelopUttrykk;
import ske.fastsetting.skatt.uttrykk.multibelop.MultiKroneUttrykk;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConsoleUttrykkBeskriver;

/**
 * Created by jorn ola birkeland on 02.11.15.
 */
public class MultiBelopForHverUttrykkTest {
    @Test
    public void test() {
        MultiBelopUttrykk<String> a = kr(5, "A").pluss(kr(7, "B"));

        MultiBelopUttrykk<String> aPluss13 = a.forHver(u -> u.pluss(kr(13)).navn("bjarne")) ;

        final UttrykkResultat<MultiBelop<String>> resultat = TestUttrykkContext.beregneOgBeskrive(aPluss13);

        assertEquals(Belop.kr(18),resultat.verdi().get("A"));
        assertEquals(Belop.kr(20), resultat.verdi().get("B"));
    }
}