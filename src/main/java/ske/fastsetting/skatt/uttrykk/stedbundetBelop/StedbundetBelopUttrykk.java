package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

import java.util.Arrays;

/**
 * Created by jorn ola birkeland on 23.02.15.
 */
public interface StedbundetBelopUttrykk<K> extends Uttrykk<StedbundetBelop<K>> {
    default StedbundetBelopSumUttrykk<K> pluss(StedbundetBelopUttrykk<K> ledd) { return StedbundetBelopSumUttrykk.sum(Arrays.asList(this, ledd));}

    default SumProporsjonalFordelingUttrykk<K> plussProporsjonalt(BelopUttrykk belop) { return new SumProporsjonalFordelingUttrykk<>(this, belop);}
    default StedbundetBelopDivisjonsUttrykk<K> dividertMed(TallUttrykk tall) {return new StedbundetBelopDivisjonsUttrykk<>(this,tall);}
    default StedbundetBelopMultiplikasjonsUttrykk<K> multiplisertMed(TallUttrykk tall) {return new StedbundetBelopMultiplikasjonsUttrykk<>(this,tall);}

    default BelopUttrykk minus(BelopUttrykk ledd) { return new TilSteduavhengigBelopUttrykk<>(this).minus(ledd);}

    default BelopUttrykk steduavhengig() {
        return new TilSteduavhengigBelopUttrykk<>(this);
    }

    default DiffProporsjonalFordelingUttrykk<K> minusProporsjonalt(BelopUttrykk ledd) { return new DiffProporsjonalFordelingUttrykk<>(this, ledd);}

    default MinusStedUttrykk<K> minusSted(StedbundetBelopUttrykk<K> ledd) { return new MinusStedUttrykk<>(this,ledd);}

    static class DiffProporsjonalFordelingUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>,DiffProporsjonalFordelingUttrykk<K>> implements StedbundetBelopUttrykk<K> {
        private final StedbundetBelopUttrykk<K> stedbundetBelopUttrykk;
        private final BelopUttrykk belop;

        public DiffProporsjonalFordelingUttrykk(StedbundetBelopUttrykk<K> stedbundetBelopUttrykk, BelopUttrykk belop) {
            this.stedbundetBelopUttrykk = stedbundetBelopUttrykk;
            this.belop = belop;
        }

        @Override
        public StedbundetBelop<K> eval(UttrykkContext ctx) {
            return ctx.eval(stedbundetBelopUttrykk).fordelProporsjonalt(ctx.eval(belop.byttFortegn()));
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(stedbundetBelopUttrykk)+ " - " +ctx.beskriv(belop);
        }
    }

    static class SumProporsjonalFordelingUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>,SumProporsjonalFordelingUttrykk<K>> implements StedbundetBelopUttrykk<K> {
        private final StedbundetBelopUttrykk<K> stedbundetBelopUttrykk;
        private final BelopUttrykk belopUttrykk;

        public SumProporsjonalFordelingUttrykk(StedbundetBelopUttrykk<K> stedbundetBelopUttrykk, BelopUttrykk belopUttrykk) {
            this.stedbundetBelopUttrykk = stedbundetBelopUttrykk;
            this.belopUttrykk = belopUttrykk;
        }

        @Override
        public StedbundetBelop<K> eval(UttrykkContext ctx) {
            return ctx.eval(stedbundetBelopUttrykk).fordelProporsjonalt(ctx.eval(belopUttrykk));

//            final StedbundetBelop<K> stedbundetBelop = ctx.eval(stedbundetBelopUttrykk);
//            final Belop belop = ctx.eval(belopUttrykk);
//
//            Belop absSum = stedbundetBelop.steder().stream()
//                    .map(s -> stedbundetBelop.get(s).abs())
//                    .reduce(Belop.NULL, Belop::pluss);
//
//            StedbundetBelop<K> resultat = StedbundetBelop.kr0();
//
//            if(absSum.erStorreEnn(Belop.NULL)) {
//                resultat = stedbundetBelop.steder().stream()
//                        .map(s -> StedbundetBelop.kr(stedbundetBelop.get(s).pluss(belop.multiplisertMed(stedbundetBelop.get(s).abs().dividertMed(absSum))), s))
//                        .reduce(StedbundetBelop.kr0(), StedbundetBelop::pluss);
//
//            } else if(stedbundetBelop.steder().size()>0) {
//                Belop andel = belop.dividertMed(stedbundetBelop.steder().size());
//                resultat = stedbundetBelop.steder().stream()
//                        .map(s -> StedbundetBelop.kr(stedbundetBelop.get(s).pluss(andel),s))
//                        .reduce(StedbundetBelop.kr0(), StedbundetBelop::pluss);
//            }
//            return resultat;

        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(stedbundetBelopUttrykk)+ " + " +ctx.beskriv(belopUttrykk);
        }
    }


    static class TilSteduavhengigBelopUttrykk<K> extends AbstractUttrykk<Belop,TilSteduavhengigBelopUttrykk<K>> implements BelopUttrykk {
        private StedbundetBelopUttrykk<K> stedbundetBelopUttrykk;

        public TilSteduavhengigBelopUttrykk(StedbundetBelopUttrykk<K> stedbundetBelopUttrykk) {
            this.stedbundetBelopUttrykk = stedbundetBelopUttrykk;
        }

        @Override
        public Belop eval(UttrykkContext ctx) {

            final StedbundetBelop<K> stedbundetBelop = ctx.eval(stedbundetBelopUttrykk);

            return stedbundetBelop.steder().stream()
                    .map(stedbundetBelop::get)
                    .reduce(Belop.NULL, Belop::pluss);
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(stedbundetBelopUttrykk);
        }
    }


    static class MinusStedUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>,MinusStedUttrykk<K>> implements StedbundetBelopUttrykk<K> {

        private final StedbundetBelopUttrykk<K> uttrykk;
        private final StedbundetBelopUttrykk<K> ledd;

        public MinusStedUttrykk(StedbundetBelopUttrykk<K> stedbundetBelopUttrykk, StedbundetBelopUttrykk<K> ledd) {

            this.uttrykk = stedbundetBelopUttrykk;
            this.ledd = ledd;
        }

        @Override
        public StedbundetBelop<K> eval(UttrykkContext ctx) {

            StedbundetBelop<K> ledd1 = ctx.eval(uttrykk);
            StedbundetBelop<K> ledd2 = ctx.eval(ledd);

            return ledd1.steder().stream()
                    .filter(s -> !ledd2.harSted(s))
                    .map(s -> StedbundetBelop.kr(ledd1.get(s), s))
                    .reduce(StedbundetBelop.kr0(), StedbundetBelop::pluss);
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(uttrykk)+ " - sted("+ctx.beskriv(ledd)+")";
        }
    }
}
