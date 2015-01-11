package ske.fastsetting.skatt.uttrykk;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConsoleUttrykkBeskriver;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.SkattegrunnlagHelper.kr;
import static ske.fastsetting.skatt.uttrykk.SkattegrunnlagHelper.prosent;
import static ske.fastsetting.skatt.uttrykk.belop.BelopHvisUttrykk.hvis;
import static ske.fastsetting.skatt.uttrykk.belop.BelopSumUttrykk.sum;

public class BasisTest {


    @Test
    public void prosentUttrykk() {
        final UttrykkResultat<Belop> resultat = lagEnkeltUttrykkResultat();

        assertEquals(new Belop(8_692), resultat.verdi());

        ConsoleUttrykkBeskriver.print(resultat);
    }

    public static UttrykkResultat<Belop> lagEnkeltUttrykkResultat() {
        final ProsentUttrykk satsTrygdeavgift = prosent(8.2).navn("Sats trygdeavgift").tags("sats");

        final KroneUttrykk lonn = kr(60_000).navn("Lønn").regler(Regel.skatteloven("5-1"));
        final KroneUttrykk bonus = kr(40_000).navn("Bonus");
        final BelopUttrykk sumLonn = sum(
                lonn,
                bonus,
                hvis(lonn.erMindreEnn(kr(90_000))).brukDa(bonus.multiplisertMed(prosent(15))).ellersBruk(kr(0)).navn("Ekstrabonus"),
            kr(20_000).minus(kr(15_000).navn("Særfradrag").tags("sats")).minus(kr(5_000).navn("Minstefradrag").tags("sats"))
        ).navn("Sum lønn").regler(Regel.skatteloven("3.2"));

        final BelopUttrykk trygdeavgift = sumLonn.multiplisertMed(satsTrygdeavgift).navn("Trygdeavgift").tags("trygd");

        return UttrykkContextImpl.beregneOgBeskrive(trygdeavgift, new Skattegrunnlag());
    }
}
