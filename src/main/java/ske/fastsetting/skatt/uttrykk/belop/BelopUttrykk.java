package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.CompareableUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public interface BelopUttrykk<C> extends CompareableUttrykk<Belop, C> {

    default BelopMultiplikasjonsUttrykk<C> multiplisertMed(TallUttrykk<C> verdi) {
        return new BelopMultiplikasjonsUttrykk<>(this, verdi);
    }

    default BelopDivisjonsUttrykk<C> dividertMed(TallUttrykk<C> verdi) {
        return new BelopDivisjonsUttrykk<>(this, verdi);
    }

    default BelopDiffUttrykk<C> minus(BelopUttrykk<C> uttrykk) {
        return new BelopDiffUttrykk<>(this, uttrykk);
    }

    default BelopSumUttrykk<C> pluss(BelopUttrykk<C> uttrykk) {
        return BelopSumUttrykk.sum(this, uttrykk);
    }

    default BelopDividertMedBelopUttrykk<C> dividertMed(BelopUttrykk<C> divident) {
        return new BelopDividertMedBelopUttrykk<>(this, divident);
    }

    static class BelopDividertMedBelopUttrykk<C> extends AbstractUttrykk<Tall, BelopDividertMedBelopUttrykk<C>,C> implements TallUttrykk<C> {
        private final BelopUttrykk<C> divisior;
        private final BelopUttrykk<C> divident;

        public BelopDividertMedBelopUttrykk(BelopUttrykk<C> belopUttrykk, BelopUttrykk<C> divident) {
            this.divisior = belopUttrykk;
            this.divident = divident;
        }

        @Override
        public Tall eval(UttrykkContext<C> ctx) {
            return Tall.ukjent(ctx.eval(divisior).dividertMed(ctx.eval(divident)));
        }

        @Override
        public String beskriv(UttrykkContext<C> ctx) {
            return ctx.beskriv(divisior) + " dividert med " + ctx.beskriv(divident);
        }
    }

}
