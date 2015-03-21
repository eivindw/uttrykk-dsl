package ske.fastsetting.skatt.uttrykk.skalSlettes;

import org.junit.Test;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;
import ske.fastsetting.skatt.uttrykk.belop.BelopMultisatsFunksjon;
import ske.fastsetting.skatt.uttrykk.belop.BelopPerKvantitetUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.distanse.Distanse;
import ske.fastsetting.skatt.uttrykk.distanse.DistanseUttrykk;

import static ske.fastsetting.skatt.uttrykk.belop.BelopHvisUttrykk.hvis;
import static ske.fastsetting.skatt.uttrykk.belop.BelopMultisatsKvantitetUttrykk.multisats;
import static ske.fastsetting.skatt.uttrykk.belop.GrenseUttrykk.begrens;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.distanse.KilometerUttrykk.km;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;


public class Reisefradrag {

    @Test
    public void test() {
        System.out.println(UttrykkContextImpl.beregne(reisefradrag(km(200000), km(4_000), kr(3_400))).verdi());
        System.out.println(UttrykkContextImpl.beregne(prosent2()).verdi());
//        System.out.println(UttrykkContextImpl.beregne(km(18).pluss(km(19)).pluss(km(31))).verdi());
//        System.out.println(UttrykkContextImpl.beregne(km(18).multiplisertMed(kr(1.40).per(km()))).verdi());
    }

    BelopUttrykk reisefradrag(DistanseUttrykk hjemArbeidReiseKm, DistanseUttrykk besoekReiseKm, BelopUttrykk bomEtcBrutto) {
        final BelopPerKvantitetUttrykk<Distanse> SATS_REISE_HOY = kr(1.50).per(km());
        final BelopPerKvantitetUttrykk<Distanse> SATS_REISE_LAV = kr(0.70).per(km());

        final BelopUttrykk NEDRE_GRENSE_BOM = kr(3_300);
        final BelopUttrykk MAKS_REISEUTGIFTER = kr(92_500);
        final BelopUttrykk EGENANDEL_REISEUTGIFTER = kr(16_000);

        final DistanseUttrykk OEVRE_GRENSE_SATS_REISE_HØY_KM = km(50_000);
        final DistanseUttrykk OEVRE_GRENSE_SATS_REISE_LAV_KM = km(75_000);

        DistanseUttrykk reiseKm = hjemArbeidReiseKm.pluss(besoekReiseKm);

        BelopUttrykk bruttoReise = multisats(reiseKm)
                .medSats(SATS_REISE_HOY, OEVRE_GRENSE_SATS_REISE_HØY_KM)
                .medSats(SATS_REISE_LAV, OEVRE_GRENSE_SATS_REISE_LAV_KM);

        BelopUttrykk bomEtcUtgifter = hvis(bomEtcBrutto.erStorreEnn(NEDRE_GRENSE_BOM)).brukDa(bomEtcBrutto).ellersBruk(kr(0));

        BelopUttrykk begrensetBrutto = begrens(bruttoReise.pluss(bomEtcUtgifter)).oppad(MAKS_REISEUTGIFTER);

        return begrens(begrensetBrutto.minus(EGENANDEL_REISEUTGIFTER)).nedad(kr(0));

    }

    BelopUttrykk prosent2() {

        return BelopMultisatsFunksjon.multisatsFunksjonAv(kr(650)).medSats(prosent(12), kr(300))
                .medSats(prosent(6), kr(700))
                .medSats(prosent(0), null);

    }

}
