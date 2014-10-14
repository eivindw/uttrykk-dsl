package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.beregn.UttrykkContext;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.CompareableUttrykk;
import ske.fastsetting.skatt.uttrykk.RegelUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

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

    static class BelopDividertMedBelopUttrykk extends RegelUttrykk<BelopDividertMedBelopUttrykk, Tall> implements TallUttrykk {
        private final BelopUttrykk divisior;
        private final BelopUttrykk divident;

        public BelopDividertMedBelopUttrykk(BelopUttrykk belopUttrykk, BelopUttrykk divident) {
            this.divisior = belopUttrykk;
            this.divident = divident;
        }

        @Override
        public Tall eval(UttrykkContext ctx) {
            return Tall.ukjent(ctx.eval(divisior).dividertMed(ctx.eval(divident)));
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(divisior) + " dividert med " + ctx.beskriv(divident);
        }
    }

}
