package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.*;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

import java.util.function.Predicate;

/**
 * Created by jorn ola birkeland on 23.02.15.
 */
public interface StedbundetBelopUttrykk extends Uttrykk<StedbundetBelop> {
    default StedbundetBelopSumUttrykk pluss(StedbundetBelopUttrykk ledd) { return StedbundetBelopSumUttrykk.sum(this, ledd);}

    default SumProporsjonalFordelingUttrykk plussProporsjonalt(BelopUttrykk belop) { return new SumProporsjonalFordelingUttrykk(this, belop);}
    default StedbundetBelopDivisjonsUttrykk dividertMed(TallUttrykk tall) {return new StedbundetBelopDivisjonsUttrykk(this,tall);}
    default StedbundetBelopMultiplikasjonsUttrykk multiplisertMed(TallUttrykk tall) {return new StedbundetBelopMultiplikasjonsUttrykk(this,tall);}
    default FiltrertStedbundetBelopUttrykk filtrer(Predicate<String> filter) { return new FiltrertStedbundetBelopUttrykk(this,filter);}

    default BelopUttrykk minus(BelopUttrykk ledd) { return new TilSteduavhengigBelopUttrykk(this).minus(ledd);}

    default BelopUttrykk steduavhengig() {
        return new TilSteduavhengigBelopUttrykk(this);
    }

    default DiffProporsjonalFordelingUttrykk minusProporsjonalt(BelopUttrykk ledd) { return new DiffProporsjonalFordelingUttrykk(this, ledd);}


    static class DiffProporsjonalFordelingUttrykk extends AbstractUttrykk<StedbundetBelop,DiffProporsjonalFordelingUttrykk> implements StedbundetBelopUttrykk {
        private final StedbundetBelopUttrykk stedbundetBelopUttrykk;
        private final BelopUttrykk belop;

        public DiffProporsjonalFordelingUttrykk(StedbundetBelopUttrykk stedbundetBelopUttrykk, BelopUttrykk belop) {
            this.stedbundetBelopUttrykk = stedbundetBelopUttrykk;
            this.belop = belop;
        }

        @Override
        public StedbundetBelop eval(UttrykkContext ctx) {
            return ctx.eval(stedbundetBelopUttrykk).fordelProporsjonalt(ctx.eval(belop.byttFortegn()));
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(stedbundetBelopUttrykk)+ " - " +ctx.beskriv(belop);
        }
    }

    static class SumProporsjonalFordelingUttrykk extends AbstractUttrykk<StedbundetBelop,SumProporsjonalFordelingUttrykk> implements StedbundetBelopUttrykk {
        private final StedbundetBelopUttrykk stedbundetBelopUttrykk;
        private final BelopUttrykk belop;

        public SumProporsjonalFordelingUttrykk(StedbundetBelopUttrykk stedbundetBelopUttrykk, BelopUttrykk belop) {
            this.stedbundetBelopUttrykk = stedbundetBelopUttrykk;
            this.belop = belop;
        }

        @Override
        public StedbundetBelop eval(UttrykkContext ctx) {
            return ctx.eval(stedbundetBelopUttrykk).fordelProporsjonalt(ctx.eval(belop));
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(stedbundetBelopUttrykk)+ " + " +ctx.beskriv(belop);
        }
    }


    static class TilSteduavhengigBelopUttrykk extends AbstractUttrykk<Belop,TilSteduavhengigBelopUttrykk> implements BelopUttrykk {
        private StedbundetBelopUttrykk stedbundetBelopUttrykk;

        public TilSteduavhengigBelopUttrykk(StedbundetBelopUttrykk stedbundetBelopUttrykk) {
            this.stedbundetBelopUttrykk = stedbundetBelopUttrykk;
        }

        @Override
        public Belop eval(UttrykkContext ctx) {
            return ctx.eval(stedbundetBelopUttrykk).steduavhengig();
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(stedbundetBelopUttrykk);
        }
    }

    static class FiltrertStedbundetBelopUttrykk extends AbstractUttrykk<StedbundetBelop,FiltrertStedbundetBelopUttrykk> implements StedbundetBelopUttrykk {
        private final StedbundetBelopUttrykk stedbundetBelopUttrykk;
        private final Predicate<String> filter;

        public FiltrertStedbundetBelopUttrykk(StedbundetBelopUttrykk stedbundetBelopUttrykk, Predicate<String> filter) {
            this.stedbundetBelopUttrykk = stedbundetBelopUttrykk;
            this.filter = filter;
        }

        @Override
        public StedbundetBelop eval(UttrykkContext ctx) {
            return ctx.eval(stedbundetBelopUttrykk).filtrer(filter);
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return stedbundetBelopUttrykk.beskriv(ctx);
        }
    }
}
