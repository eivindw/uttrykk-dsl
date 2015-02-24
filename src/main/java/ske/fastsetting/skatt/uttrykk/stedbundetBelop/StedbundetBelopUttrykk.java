package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.*;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jorn ola birkeland on 23.02.15.
 */
public interface StedbundetBelopUttrykk extends Uttrykk<StedbundetBelop> {
    default StedbundetBelopUttrykk pluss(StedbundetBelopUttrykk ledd) { return StedbundetBelopSumUttrykk.sum(this, ledd);}
    default StedbundetBelopUttrykk minus(StedbundetBelopUttrykk ledd) { return new StedbundetBelopDiffUttrykk(this, ledd);}

    default StedbundetBelopUttrykk fordelProporsjonalt(BelopUttrykk belop) { return new FordelProporsjonaltUttrykk(this, belop);}
    default StedbundetBelopUttrykk dividertMed(TallUttrykk tall) {return new StedbundetBelopDivisjonsUttrykk(this,tall);}
    default StedbundetBelopUttrykk multiplisertMed(TallUttrykk tall) {return new StedbundetBelopMultiplikasjonsUttrykk(this,tall);}
    default StedbundetBelopUttrykk filtrer(Predicate<String> filter) { return new FiltrertStedbundetBelopUttrykk(this,filter);}

    default BelopUttrykk minus(BelopUttrykk ledd) { return new TilSteduavhengigBelopUttrykk(this).minus(ledd);}

    default BelopUttrykk steduavhengig() {
        return new TilSteduavhengigBelopUttrykk(this);
    }

    default StedbundetBelopUttrykk minusProporsjonalt(BelopUttrykk ledd) { return new FordelProporsjonaltUttrykk(this, ledd.byttFortegn());}

    static class StedbundetBelopSumUttrykk extends SumUttrykk<StedbundetBelop,StedbundetBelopUttrykk,StedbundetBelopSumUttrykk> implements StedbundetBelopUttrykk{

        protected StedbundetBelopSumUttrykk(Collection<StedbundetBelopUttrykk> uttrykk) {
            super(uttrykk);
        }

        @Override
        protected StedbundetBelop nullVerdi() {
            return StedbundetBelop.NULL;
        }

        public static StedbundetBelopUttrykk sum(StedbundetBelopUttrykk... stedbundetBelopUttrykk) {
            return new StedbundetBelopSumUttrykk(Stream.of(stedbundetBelopUttrykk).collect(Collectors.toList()));
        }
    }

    static class StedbundetBelopDiffUttrykk extends DiffUttrykk<StedbundetBelop,StedbundetBelopUttrykk,StedbundetBelopDiffUttrykk> implements StedbundetBelopUttrykk {
        public StedbundetBelopDiffUttrykk(StedbundetBelopUttrykk ledd1, StedbundetBelopUttrykk ledd2) {
            super(ledd1,ledd2);
        }
    }

    static class FordelProporsjonaltUttrykk extends AbstractUttrykk<StedbundetBelop,FordelProporsjonaltUttrykk> implements StedbundetBelopUttrykk {
        private final StedbundetBelopUttrykk stedbundetBelopUttrykk;
        private final BelopUttrykk belop;

        public FordelProporsjonaltUttrykk(StedbundetBelopUttrykk stedbundetBelopUttrykk, BelopUttrykk belop) {
            this.stedbundetBelopUttrykk = stedbundetBelopUttrykk;
            this.belop = belop;
        }

        @Override
        public StedbundetBelop eval(UttrykkContext ctx) {
            return ctx.eval(stedbundetBelopUttrykk).fordelProporsjonalt(ctx.eval(belop));
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return null;
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
            return null;
        }
    }

    static class StedbundetBelopDivisjonsUttrykk extends DivisjonsUttrykk<StedbundetBelop,StedbundetBelopUttrykk,StedbundetBelopDivisjonsUttrykk> implements StedbundetBelopUttrykk {
        public StedbundetBelopDivisjonsUttrykk(StedbundetBelopUttrykk stedbundetBelopUttrykk, TallUttrykk tall) {
            super(stedbundetBelopUttrykk,tall);
        }
    }

    static class StedbundetBelopMultiplikasjonsUttrykk extends MultiplikasjonsUttrykk<StedbundetBelop,StedbundetBelopUttrykk,StedbundetBelopMultiplikasjonsUttrykk> implements StedbundetBelopUttrykk {
        public StedbundetBelopMultiplikasjonsUttrykk(StedbundetBelopUttrykk stedbundetBelopUttrykk, TallUttrykk tall) {
            super(stedbundetBelopUttrykk,tall);
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
            return null;
        }
    }
}
