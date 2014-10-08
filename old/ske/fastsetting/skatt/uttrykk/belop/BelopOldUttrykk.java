package ske.fastsetting.skatt.uttrykk.belop;


import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.CompareableOldUttrykk;
import ske.fastsetting.skatt.uttrykk.RegelUtil;
import ske.fastsetting.skatt.uttrykk.RegelUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkBeskriver;
import ske.fastsetting.skatt.uttrykk.tall.TallOldUttrykk;

import java.util.List;

public interface BelopOldUttrykk extends CompareableOldUttrykk<Belop> {

    BelopOldUttrykk medNavn(String navn);

    BelopOldUttrykk medRegel(Regel... regel);

    String getNavn();

    List<Regel> getRegler();

    default BelopOldUttrykk multiplisertMed(TallOldUttrykk verdi) {
        return new BelopMultiplikasjonsOldUttrykk(this, verdi);
    }

    default BelopOldUttrykk dividertMed(TallOldUttrykk verdi) {
        return new BelopDivisjonsOldUttrykk(this, verdi);
    }


    default BelopOldUttrykk minus(BelopOldUttrykk uttrykk) {
        return new BelopDiffOldUttrykk(this, uttrykk);
    }

    default BelopOldUttrykk pluss(BelopOldUttrykk uttrykk) {
        return BelopSumOldUttrykk.sum(this, uttrykk);
    }


    default TallOldUttrykk dividertMed(BelopOldUttrykk divident) {
        return new BelopDividertMedBelopOldUttrykk(this, divident);
    }

    static class BelopDividertMedBelopOldUttrykk extends RegelUttrykk<BelopDividertMedBelopOldUttrykk> implements TallOldUttrykk {
        private final BelopOldUttrykk divisior;
        private final BelopOldUttrykk divident;

        public BelopDividertMedBelopOldUttrykk(BelopOldUttrykk belopUttrykk, BelopOldUttrykk divident) {
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
