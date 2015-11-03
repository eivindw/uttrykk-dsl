package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import org.junit.Test;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.TestUttrykkContext;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConsoleUttrykkBeskriver;

import static ske.fastsetting.skatt.uttrykk.belop.BelopGrenseUttrykk.begrens;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;

/**
 * Created by jorn ola birkeland on 02.11.15.
 */
public class StedbundetBelopForHverUttrykkTest {
    @Test
    public void test() {
        StedbundetBelopUttrykk<String> a = StedbundetKroneUttrykk.kr(5, "A").pluss(StedbundetKroneUttrykk.kr(7, "B"));

        StedbundetBelopUttrykk<String> b = a.forHver(u -> begrens(u).nedad(kr(6)).navn("bjarne")) ;
        StedbundetBelopUttrykk<String> c = a.forHver(u -> u.pluss(kr(13)).navn("bjarne")) ;

        final UttrykkResultat<StedbundetBelop<String>> resultat = TestUttrykkContext.beregneOgBeskrive(c);
        System.out.println(resultat.verdi());
        ConsoleUttrykkBeskriver.print(resultat);
    }
}
