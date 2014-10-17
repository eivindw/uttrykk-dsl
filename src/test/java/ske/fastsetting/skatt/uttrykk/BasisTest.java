package ske.fastsetting.skatt.uttrykk;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConsoleUttrykkBeskriver;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.belop.BelopSumUttrykk.sum;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

public class BasisTest {

    @Test
    public void prosentUttrykk() {
        final UttrykkResultat<Belop> resultat = lagEnkeltUttrykkResultat();

        assertEquals(new Belop(8_200), resultat.verdi());

        ConsoleUttrykkBeskriver.print(resultat);
    }

    public static UttrykkResultat<Belop> lagEnkeltUttrykkResultat() {
        final ProsentUttrykk satsTrygdeavgift = prosent(8.2).navn("Sats trygdeavgift").tags("sats");

        final BelopUttrykk sumLonn = sum(
            kr(60_000).navn("Lønn").regler(Regel.skatteloven("5-1")),
            kr(40_000).navn("Bonus"),
            kr(20_000).minus(kr(15_000)).minus(kr(5_000))
        ).navn("Sum lønn");

        final BelopUttrykk<?> trygdeavgift = sumLonn.multiplisertMed(satsTrygdeavgift).navn("Trygdeavgift");

        return UttrykkContextImpl.beregneOgBeskrive(trygdeavgift);
    }
}
