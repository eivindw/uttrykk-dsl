package ske.fastsetting.skatt.old.uttrykk.belop;


import ske.fastsetting.skatt.old.domene.Belop;
import ske.fastsetting.skatt.old.domene.Regel;
import ske.fastsetting.skatt.old.domene.Tall;
import ske.fastsetting.skatt.old.uttrykk.CompareableUttrykk;
import ske.fastsetting.skatt.old.uttrykk.RegelUtil;
import ske.fastsetting.skatt.old.uttrykk.RegelUttrykk;
import ske.fastsetting.skatt.old.uttrykk.UttrykkBeskriver;
import ske.fastsetting.skatt.old.uttrykk.tall.TallUttrykk;

import java.util.List;

public interface BelopUttrykk extends CompareableUttrykk<Belop> {

    BelopUttrykk medNavn(String navn);

    BelopUttrykk medRegel(Regel... regel);

    String getNavn();

    List<Regel> getRegler();

    default BelopUttrykk multiplisertMed(TallUttrykk verdi) {
        return new BelopMultiplikasjonsUttrykk(this, verdi);
    }

    default BelopUttrykk dividertMed(TallUttrykk verdi) {
        return new BelopDivisjonsUttrykk(this, verdi);
    }


    default BelopUttrykk minus(BelopUttrykk uttrykk) {
        return new BelopDiffUttrykk(this, uttrykk);
    }

    default BelopUttrykk pluss(BelopUttrykk uttrykk) {
        return BelopSumUttrykk.sum(this, uttrykk);
    }


    default TallUttrykk dividertMed(BelopUttrykk divident) {
        return new BelopDividertMedBelopUttrykk(this, divident);
    }

    static class BelopDividertMedBelopUttrykk extends RegelUttrykk<BelopDividertMedBelopUttrykk> implements TallUttrykk {
        private final BelopUttrykk divisior;
        private final BelopUttrykk divident;

        public BelopDividertMedBelopUttrykk(BelopUttrykk belopUttrykk, BelopUttrykk divident) {
            this.divisior = belopUttrykk;
            this.divident = divident;
        }

        @Override
        public Tall evaluer() {
//            return new Tall(Tall.TallUttrykkType.PROSENT,divisior.evaluer().dividertMed(divident.evaluer()));
            return Tall.ukjent(divisior.evaluer().dividertMed(divident.evaluer())
            );
        }

        @Override
        public void beskrivEvaluering(UttrykkBeskriver beskriver) {
            UttrykkBeskriver nyBeskriver = beskriver.overskrift(evaluer() + RegelUtil.formater(navn));
            divisior.beskrivEvaluering(nyBeskriver);
            nyBeskriver.skriv("dividert med");
            divident.beskrivEvaluering(nyBeskriver);
        }

        @Override
        protected void beskrivGeneriskMedRegel(UttrykkBeskriver beskriver) {
            divisior.beskrivGenerisk(beskriver.rykkInn());
            beskriver.skriv("dividert med");
            divident.beskrivGenerisk(beskriver.rykkInn());
        }
    }

}
