package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.BelopPerKvantitet;
import ske.fastsetting.skatt.domene.Kvantitet;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ske.fastsetting.skatt.uttrykk.belop.BelopSumUttrykk.sum;

public class BelopMultisatsFunksjon2<K extends Kvantitet> extends AbstractUttrykk<Belop,BelopMultisatsFunksjon2<K>> implements BelopUttrykk {

    private Uttrykk<K> xVerdi;
    private List<BelopUttrykk> satsSteg = new ArrayList<>();

    public BelopMultisatsFunksjon2(Uttrykk<K> x) {

        this.xVerdi = x;
    }

    public static <K extends Kvantitet> BelopMultisatsFunksjon2<K> multisatsFunksjonAv(Uttrykk<K> grunnlag) {
        return new BelopMultisatsFunksjon2<>(grunnlag);
    }

    public SatsStegBuilderUttrykk medSats(BelopPerKvantitetUttrykk<K> sats) {

        return new SatsStegBuilderUttrykk(this, null, sats);
    }

    @Override
    public Belop eval(UttrykkContext ctx) {

        return ctx.eval(sum(satsSteg));
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return satsSteg.stream().map(ctx::beskriv).collect(Collectors.joining(",", "multisats(", ")"));
    }


    public class SatsStegUttrykk extends AbstractUttrykk<Belop,SatsStegUttrykk> implements BelopUttrykk {
        private final BelopPerKvantitetUttrykk<K> sats;
        private Uttrykk<K> oevreGrense;
        private final Uttrykk<K> nedreGrense;

        public SatsStegUttrykk(Uttrykk<K> nedreGrense, BelopPerKvantitetUttrykk<K> sats) {
            this.sats = sats;
            this.nedreGrense = nedreGrense;
        }

        @Override
        public Belop eval(UttrykkContext ctx) {

            final BelopPerKvantitet<K> satsEval = ctx.eval(sats);
            Belop oevre = oevreGrense!=null ? satsEval.multiplisertMed(ctx.eval(oevreGrense)) : null;
            Belop nedre = nedreGrense!=null ? satsEval.multiplisertMed(ctx.eval(nedreGrense)) : Belop.NULL;
            Belop x = satsEval.multiplisertMed(ctx.eval(xVerdi));

            if(x.erMindreEnn(nedre)) {
                return Belop.NULL;
            } else if(oevre!=null && x.erStorreEnn(oevre)) {
                return oevre.minus(nedre);
            } else {
                return x.minus(nedre);
            }

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


    public class SatsStegBuilderUttrykk extends AbstractSatsStegBuilderUttrykk<SatsStegBuilderUttrykk> {
        private final SatsStegUttrykk satsStegUttrykk;


        public SatsStegBuilderUttrykk(BelopMultisatsFunksjon2<K> funksjon, Uttrykk<K> nedreGrense, BelopPerKvantitetUttrykk<K> sats) {
            super(funksjon);
            this.satsStegUttrykk = new SatsStegUttrykk(nedreGrense,sats);
            this.funksjon.satsSteg.add(satsStegUttrykk);
        }

        public SatsStegBuilderUttrykk.TilSatsStegBuilderUttrykk til(Uttrykk<K> til) {
            satsStegUttrykk.oevreGrense = til;
            return new SatsStegBuilderUttrykk.TilSatsStegBuilderUttrykk(funksjon);
        }

        public class TilSatsStegBuilderUttrykk extends AbstractSatsStegBuilderUttrykk<SatsStegBuilderUttrykk.TilSatsStegBuilderUttrykk> {

            public TilSatsStegBuilderUttrykk(BelopMultisatsFunksjon2<K> funksjon) {
                super(funksjon);
            }

            public SatsStegBuilderUttrykk deretterMedSats(BelopPerKvantitetUttrykk<K> sats) {
                return new SatsStegBuilderUttrykk(funksjon, satsStegUttrykk.oevreGrense, sats);
            }

        }
    }

    protected abstract class AbstractSatsStegBuilderUttrykk<B extends AbstractSatsStegBuilderUttrykk<B>> extends AbstractUttrykk<Belop,B> implements BelopUttrykk {
        protected final BelopMultisatsFunksjon2<K> funksjon;

        @SuppressWarnings("unchecked")
        protected B self = (B) this;

        protected AbstractSatsStegBuilderUttrykk(BelopMultisatsFunksjon2<K> funksjon) {
            this.funksjon = funksjon;
        }

        @Override
        public Belop eval(UttrykkContext ctx) {
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
