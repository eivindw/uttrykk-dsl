package ske.fastsetting.skatt.uttrykk.skalSlettes;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Kvantitet;
import ske.fastsetting.skatt.domene.enhet.Enhet;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.CompareableUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;
import ske.fastsetting.skatt.uttrykk.belop.BelopMultisatsFunksjon2;
import ske.fastsetting.skatt.uttrykk.belop.BelopPerKvantitetUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

import java.math.BigDecimal;

import static ske.fastsetting.skatt.uttrykk.belop.BelopHvisUttrykk.hvis;
import static ske.fastsetting.skatt.uttrykk.belop.BelopMultisatsFunksjon.multisatsFunksjonAv;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.skalSlettes.Reisefradrag.DistanseUttrykk.km;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

//import static ske.fastsetting.skatt.uttrykk.skalSlettes.Reisefradrag.BelopMultisatsFunksjon2.multisatsFunksjonAv;
//import static ske.fastsetting.skatt.uttrykk.skalSlettes.Reisefradrag.ProsentUttrykk.prosent;


public class Reisefradrag {

    @Test
    public void test() {
        System.out.println(UttrykkContextImpl.beregne(reisefradrag(107_000)).verdi());
        System.out.println(UttrykkContextImpl.beregne(prosent2()).verdi());
    }

    BelopUttrykk reisefradrag(int km) {
        DistanseUttrykk reiseKm = km(km);

        hvis(reiseKm.erFra(km(18))).brukDa(kr(23)).ellersBruk(kr(48));

        BelopPerKvantitetUttrykk<Distanse> SATS_REISE_HOY = kr(1.40).dividertMed(km(1));
        BelopPerKvantitetUttrykk<Distanse> SATS_REISE_LAV = kr(0.70).dividertMed(km(1));
        BelopPerKvantitetUttrykk<Distanse> SATS_NULL = kr(0).dividertMed(km(1));

        return BelopMultisatsFunksjon2.multisatsFunksjonAv(reiseKm).medSats(SATS_REISE_HOY).til(km(50_000))
                .deretterMedSats(SATS_REISE_LAV).til(km(75_000))
                .deretterMedSats(SATS_NULL);

    }

    BelopUttrykk prosent2() {

        return multisatsFunksjonAv(kr(650)).medSats(prosent(12)).til(kr(300))
                .deretterMedSats(prosent(6)).til(kr(700))
                .deretterMedSats(prosent(0));

    }


    private static interface Kilometer extends Enhet {}

    private static class Distanse extends Kvantitet<Double,Kilometer> implements Comparable<Distanse>  {

        public Distanse(Double verdi) {
            super(verdi);
        }

        @Override
        public BigDecimal toBigDecimal() {
            return BigDecimal.valueOf(verdi());
        }

        @Override
        public Distanse multiplisertMed(BigDecimal faktor) {
            return new Distanse(verdi()*faktor.doubleValue());
        }

        @Override
        public int compareTo(Distanse o) {
            return verdi().compareTo(o.verdi());
        }
    }

    static class DistanseUttrykk extends AbstractUttrykk<Distanse,DistanseUttrykk> implements CompareableUttrykk<Distanse> {


        private final Distanse distanse;

        public DistanseUttrykk(double km) {
            this.distanse = new Distanse(km);
        }

        public static DistanseUttrykk km(double km) {
            return new DistanseUttrykk(km);
        }


        @Override
        public Distanse eval(UttrykkContext ctx) {
            return distanse;
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return distanse.verdi() + " km";
        }
    }


}
