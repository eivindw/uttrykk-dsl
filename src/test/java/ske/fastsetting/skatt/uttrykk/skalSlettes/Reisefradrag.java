package ske.fastsetting.skatt.uttrykk.skalSlettes;

import org.junit.Test;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;
import ske.fastsetting.skatt.uttrykk.belop.BelopMultisatsFunksjon2;
import ske.fastsetting.skatt.uttrykk.belop.BelopPerKvantitetUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.distanse.Distanse;
import ske.fastsetting.skatt.uttrykk.distanse.DistanseUttrykk;

import static ske.fastsetting.skatt.uttrykk.belop.BelopHvisUttrykk.hvis;
import static ske.fastsetting.skatt.uttrykk.belop.BelopMultisatsFunksjon.multisatsFunksjonAv;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.distanse.KilometerUttrykk.km;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

//import static ske.fastsetting.skatt.uttrykk.skalSlettes.Reisefradrag.BelopMultisatsFunksjon2.multisatsFunksjonAv;
//import static ske.fastsetting.skatt.uttrykk.skalSlettes.Reisefradrag.ProsentUttrykk.prosent;


public class Reisefradrag {

    @Test
    public void test() {
        System.out.println(UttrykkContextImpl.beregne(reisefradrag(107_000)).verdi());
        System.out.println(UttrykkContextImpl.beregne(prosent2()).verdi());
        System.out.println(UttrykkContextImpl.beregne(km(18).pluss(km(19)).pluss(km(31))).verdi());
        System.out.println(UttrykkContextImpl.beregne(km(18).multiplisertMed(kr(1.40).per(km()))).verdi());
    }

    BelopUttrykk reisefradrag(int km) {
        DistanseUttrykk reiseKm = km(km);

        hvis(reiseKm.erFra(km(18))).brukDa(kr(23)).ellersBruk(kr(48));


        BelopPerKvantitetUttrykk<Distanse> SATS_REISE_HOY = kr(1.40).per(km());
        BelopPerKvantitetUttrykk<Distanse> SATS_REISE_LAV = kr(0.70).per(km());
        BelopPerKvantitetUttrykk<Distanse> SATS_NULL = kr(0).per(km());

        return BelopMultisatsFunksjon2.multisatsFunksjonAv(reiseKm).medSats(kr(1.40).per(km())).til(km(50_000))
                .deretterMedSats(SATS_REISE_LAV).til(km(75_000))
                .deretterMedSats(SATS_NULL);

    }

    BelopUttrykk prosent2() {

        return multisatsFunksjonAv(kr(650)).medSats(prosent(12)).til(kr(300))
                .deretterMedSats(prosent(6)).til(kr(700))
                .deretterMedSats(prosent(0));

    }


}
