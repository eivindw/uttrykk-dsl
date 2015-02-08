package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ske.fastsetting.skatt.uttrykk.belop.BelopSumUttrykk.sum;
import static ske.fastsetting.skatt.uttrykk.belop.GrenseUttrykk.begrens;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;

/* TODO: UFULLSTENDIG */
public class BelopMultisatsFunksjon extends AbstractUttrykk<Belop, BelopMultisatsFunksjon> implements BelopUttrykk {

    private BelopUttrykk xVerdi;
    private List<BelopUttrykk> satsSteg = new ArrayList<>();

    public BelopMultisatsFunksjon(BelopUttrykk x) {

        this.xVerdi = x;
    }

    public static BelopMultisatsFunksjon multisatsFunksjonAv(BelopUttrykk x) {
        return new BelopMultisatsFunksjon(x);
    }

    public SatsStegBuilderUttrykk medSats(TallUttrykk sats) {

        return new SatsStegBuilderUttrykk(this, kr(0), sats);
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
        private final TallUttrykk sats;
        private BelopUttrykk oevreGrense;
        private final BelopUttrykk nedreGrense;

        public SatsStegUttrykk(BelopUttrykk nedreGrense, TallUttrykk sats) {
            this.sats = sats;
            this.nedreGrense = nedreGrense;
        }

        @Override
        public Belop eval(UttrykkContext ctx) {
            GrenseUttrykk grenseUttrykk = begrens(xVerdi.minus(nedreGrense).multiplisertMed(sats)).nedad(kr(0));
            if (oevreGrense != null) {
                grenseUttrykk.oppad(oevreGrense.minus(nedreGrense).multiplisertMed(sats));
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


    public class SatsStegBuilderUttrykk extends AbstractSatsStegBuilderUttrykk<SatsStegBuilderUttrykk> {
        private final SatsStegUttrykk satsStegUttrykk;


        public SatsStegBuilderUttrykk(BelopMultisatsFunksjon funksjon, BelopUttrykk nedreGrense, TallUttrykk sats) {
            super(funksjon);
            this.satsStegUttrykk = new SatsStegUttrykk(nedreGrense,sats);
            this.funksjon.satsSteg.add(satsStegUttrykk);
        }

        public TilSatsStegBuilderUttrykk til(BelopUttrykk til) {
            satsStegUttrykk.oevreGrense = til;
            return new TilSatsStegBuilderUttrykk(funksjon);
        }

        public class TilSatsStegBuilderUttrykk extends AbstractSatsStegBuilderUttrykk<TilSatsStegBuilderUttrykk> {

            public TilSatsStegBuilderUttrykk(BelopMultisatsFunksjon funksjon) {
                super(funksjon);
            }

            public SatsStegBuilderUttrykk deretterMedSats(TallUttrykk sats) {
                return new SatsStegBuilderUttrykk(funksjon, satsStegUttrykk.oevreGrense, sats);
            }

        }
    }

    protected abstract class AbstractSatsStegBuilderUttrykk<B extends AbstractSatsStegBuilderUttrykk<B>> extends AbstractUttrykk<Belop,B> implements BelopUttrykk {
        protected final BelopMultisatsFunksjon funksjon;

        @SuppressWarnings("unchecked")
        protected B self = (B) this;

        protected AbstractSatsStegBuilderUttrykk(BelopMultisatsFunksjon funksjon) {
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
