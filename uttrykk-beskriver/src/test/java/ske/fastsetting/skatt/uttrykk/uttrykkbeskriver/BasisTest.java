package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.belop.BelopHvisUttrykk.hvis;
import static ske.fastsetting.skatt.uttrykk.belop.BelopSumUttrykk.sum;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk;

public class BasisTest {

    private static final String TAG_SATS = "satser";
    private static final String TAG_GRLAG = "grunnlag";
    private static final String TAG_SKATT = "skatter";

    @Test
    public void prosentUttrykk() {
        final UttrykkResultat<Belop> resultat = lagEnkeltUttrykkResultat();

        assertEquals(Belop.kr(37_312), resultat.verdi());

        ConsoleUttrykkBeskriver.print(resultat);
    }

    public static UttrykkResultat<Belop> lagEnkeltUttrykkResultat() {
        final ProsentUttrykk satsTrygdeavgift = prosent(8.2).navn("Sats trygdeavgift").tags(TAG_SATS);
        final ProsentUttrykk satsSkatt = prosent(27).navn("Sats skatt").tags(TAG_SATS);

        final KroneUttrykk lonn = kr(60_000).navn("Lønn").regler(Regel.skatteloven("5-1")).tags(TAG_GRLAG);
        final KroneUttrykk bonus = kr(40_000).navn("Bonus").tags(TAG_GRLAG);
        final BelopUttrykk sumLonn = sum(
          lonn,
          bonus,
          hvis(lonn.erMindreEnn(kr(90_000)))
            .brukDa(bonus.multiplisertMed(prosent(15)))
            .ellersBruk(kr(0)).navn("Ekstrabonus").tags(TAG_GRLAG),
          kr(20_000)
            .minus(kr(15_000).navn("Særfradrag").tags(TAG_SATS))
            .minus(kr(5_000).navn("Minstefradrag").tags(TAG_SATS))
        ).navn("Sum lønn").regler(Regel.skatteloven("3.2")).tags(TAG_GRLAG);

        final BelopUttrykk trygdeavgift = sumLonn.multiplisertMed(satsTrygdeavgift).navn("Trygdeavgift").tags
          (TAG_SKATT);
        final BelopUttrykk skatt = sumLonn.multiplisertMed(satsSkatt).navn("Skatt").tags(TAG_SKATT);
        final BelopUttrykk sum = trygdeavgift.pluss(skatt).navn("Sum skatt & avgift").tags(TAG_SKATT);

        return UttrykkContextImpl.beregneOgBeskrive(sum);
    }
}
