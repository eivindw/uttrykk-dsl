package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.CompareableUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public interface BelopUttrykk extends CompareableUttrykk<Belop> {

    default BelopMultiplikasjonsUttrykk multiplisertMed(TallUttrykk verdi) {
        return new BelopMultiplikasjonsUttrykk(this, verdi);
    }

    default BelopDivisjonsUttrykk dividertMed(TallUttrykk verdi) {
        return new BelopDivisjonsUttrykk(this, verdi);
    }

    default BelopDiffUttrykk minus(BelopUttrykk uttrykk) {
        return new BelopDiffUttrykk(this, uttrykk);
    }

    default BelopSumUttrykk pluss(BelopUttrykk uttrykk) {
        return BelopSumUttrykk.sum(this, uttrykk);
    }

    default BelopDividertMedBelopUttrykk dividertMed(BelopUttrykk divident) {
        return new BelopDividertMedBelopUttrykk(this, divident);
    }

    default BelopUttrykk byttFortegn() { return new BelopByttFortegnUttrykk(this);  }

    static class BelopDividertMedBelopUttrykk extends AbstractUttrykk<Tall, BelopDividertMedBelopUttrykk> implements TallUttrykk {
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
            return ctx.beskriv(divisior) + " / " + ctx.beskriv(divident);
        }
    }

    class BelopByttFortegnUttrykk extends AbstractUttrykk<Belop,BelopByttFortegnUttrykk>  implements BelopUttrykk {
        private BelopUttrykk belopUttrykk;

        public BelopByttFortegnUttrykk(BelopUttrykk belopUttrykk) {
            this.belopUttrykk = belopUttrykk;
        }

        @Override
        public Belop eval(UttrykkContext ctx) {
            return ctx.eval(belopUttrykk).byttFortegn();
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return " - "+ ctx.beskriv(belopUttrykk);
        }
    }
}
