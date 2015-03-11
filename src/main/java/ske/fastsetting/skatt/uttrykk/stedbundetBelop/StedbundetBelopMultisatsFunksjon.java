package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StebundetGrenseUttrykk.begrens;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetBelopSumUttrykk.sum;

/* TODO: UFULLSTENDIG */
public class StedbundetBelopMultisatsFunksjon<K> extends AbstractUttrykk<StedbundetBelop<K>, StedbundetBelopMultisatsFunksjon<K>> implements StedbundetBelopUttrykk<K> {

    private StedbundetBelopUttrykk<K> xVerdi;
    private List<StedbundetBelopUttrykk<K>> satsSteg = new ArrayList<>();

    public StedbundetBelopMultisatsFunksjon(StedbundetBelopUttrykk<K> x) {

        this.xVerdi = x;
    }

    public static <K> StedbundetBelopMultisatsFunksjon<K> multisatsFunksjonAv(StedbundetBelopUttrykk<K> x) {
        return new StedbundetBelopMultisatsFunksjon<>(x);
    }

    public StedbundetSatsStegBuilderUttrykk medSats(TallUttrykk sats) {

        return new StedbundetSatsStegBuilderUttrykk(this, kr(0), sats);
    }

    @Override
    public StedbundetBelop<K> eval(UttrykkContext ctx) {

        return ctx.eval(sum(satsSteg));
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return satsSteg.stream().map(ctx::beskriv).collect(Collectors.joining(",", "multisats(", ")"));
    }


    public class StedbundetSatsStegUttrykk extends AbstractUttrykk<StedbundetBelop<K>,StedbundetSatsStegUttrykk> implements StedbundetBelopUttrykk<K> {
        private final TallUttrykk sats;
        private BelopUttrykk oevreGrense;
        private final BelopUttrykk nedreGrense;

        public StedbundetSatsStegUttrykk(BelopUttrykk nedreGrense, TallUttrykk sats) {
            this.sats = sats;
            this.nedreGrense = nedreGrense;
        }

        @Override
        public StedbundetBelop<K> eval(UttrykkContext ctx) {
            StebundetGrenseUttrykk<K> grenseUttrykk = begrens(xVerdi.minusProporsjonalt(nedreGrense).multiplisertMed(sats))
                    .nedadProporsjonalt(kr(0));
            if (oevreGrense != null) {
                grenseUttrykk.oppadProporsjonalt(oevreGrense.minus(nedreGrense).multiplisertMed(sats));
            }

            return ctx.eval(grenseUttrykk);
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            String stegResultat;

            if (oevreGrense != null) {
                stegResultat = "satsFraTil(" + ctx.beskriv(xVerdi)+"," + ctx.beskriv(sats) + "," + ctx.beskriv(nedreGrense) + "," + ctx.beskriv(oevreGrense) + ")";
            } else {
                stegResultat = "satsFra(" + ctx.beskriv(xVerdi)+"," + ctx.beskriv(sats) + "," + ctx.beskriv(nedreGrense) + ")";
            }
            return stegResultat;
        }
    }


    public class StedbundetSatsStegBuilderUttrykk extends AbstractSatsStegBuilderUttrykk<K,StedbundetSatsStegBuilderUttrykk> {
        private final StedbundetSatsStegUttrykk satsStegUttrykk;


        public StedbundetSatsStegBuilderUttrykk(StedbundetBelopMultisatsFunksjon<K> funksjon, BelopUttrykk nedreGrense, TallUttrykk sats) {
            super(funksjon);
            this.satsStegUttrykk = new StedbundetSatsStegUttrykk(nedreGrense,sats);
            this.funksjon.satsSteg.add(satsStegUttrykk);
        }

        public TilSatsStegBuilderUttrykk til(BelopUttrykk til) {
            satsStegUttrykk.oevreGrense = til;
            return new TilSatsStegBuilderUttrykk(funksjon);
        }

        public TilSatsStegBuilderUttrykk til(StedbundetBelopUttrykk<K> til) {
            return til(til.steduavhengig());
        }

        public class TilSatsStegBuilderUttrykk extends AbstractSatsStegBuilderUttrykk<K,TilSatsStegBuilderUttrykk> {

            public TilSatsStegBuilderUttrykk(StedbundetBelopMultisatsFunksjon funksjon) {
                super(funksjon);
            }

            public StedbundetSatsStegBuilderUttrykk deretterMedSats(TallUttrykk sats) {
                return new StedbundetSatsStegBuilderUttrykk(funksjon, satsStegUttrykk.oevreGrense, sats);
            }

        }
    }

    protected abstract class AbstractSatsStegBuilderUttrykk<K, B extends AbstractSatsStegBuilderUttrykk<K,B>> extends AbstractUttrykk<StedbundetBelop<K>,B> implements StedbundetBelopUttrykk<K> {
        protected final StedbundetBelopMultisatsFunksjon<K> funksjon;

        @SuppressWarnings("unchecked")
        protected B self = (B) this;

        protected AbstractSatsStegBuilderUttrykk(StedbundetBelopMultisatsFunksjon<K> funksjon) {
            this.funksjon = funksjon;
        }

        @Override
        public StedbundetBelop<K> eval(UttrykkContext ctx) {
            return ctx.eval(funksjon);
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(funksjon);
        }

        @Override
        public B navn(String navn) {
            funksjon.navn(navn);
            return self;
        }

        @Override
        public B tags(String... tags) {
            funksjon.tags(tags);
            return self;
        }

    }
}
