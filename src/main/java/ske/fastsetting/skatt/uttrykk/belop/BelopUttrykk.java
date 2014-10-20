package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.CompareableUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public interface BelopUttrykk<B extends BelopUttrykk<B,C>, C> extends CompareableUttrykk<Belop, B, C> {

    default BelopUttrykk<?,C> multiplisertMed(TallUttrykk<?,C> verdi) {
        return new BelopMultiplikasjonsUttrykk<C>(this, verdi);
    }

    default BelopUttrykk<?,C> dividertMed(TallUttrykk<?,C> verdi) {
        return new BelopDivisjonsUttrykk<C>(this, verdi);
    }

    default BelopUttrykk<?,C> minus(BelopUttrykk<?,C> uttrykk) {
        return new BelopDiffUttrykk<C>(this, uttrykk);
    }

    default BelopUttrykk<?,C> pluss(BelopUttrykk<?,C> uttrykk) {
        return BelopSumUttrykk.sum(this, uttrykk);
    }

    default TallUttrykk<?,C> dividertMed(BelopUttrykk<?,C> divident) {
        return new BelopDividertMedBelopUttrykk<C>(this, divident);
    }

    B navn(String navn);

    B regler(Regel... regel);

    B tag(String tag);

    @Override
    B tags(String... tags);

    static class BelopDividertMedBelopUttrykk<C> extends AbstractUttrykk<Tall, BelopDividertMedBelopUttrykk<C>,C> implements TallUttrykk<BelopDividertMedBelopUttrykk<C>, C> {
        private final BelopUttrykk<?,C> divisior;
        private final BelopUttrykk<?,C> divident;

        public BelopDividertMedBelopUttrykk(BelopUttrykk<?,C> belopUttrykk, BelopUttrykk<?,C> divident) {
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
