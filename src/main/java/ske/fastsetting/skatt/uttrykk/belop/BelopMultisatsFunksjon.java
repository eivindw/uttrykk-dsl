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
    private List<SatsStegUttrykk> satsSteg = new ArrayList<>();

    public BelopMultisatsFunksjon(BelopUttrykk x) {

        this.xVerdi = x;
    }

    public static BelopMultisatsFunksjon multisatsFunksjonAv(BelopUttrykk x) {
        return new BelopMultisatsFunksjon(x);
    }

    public SatsStegUttrykk medSats(TallUttrykk sats) {

        return new SatsStegUttrykk(this, kr(0), sats);
    }

    @Override
    public Belop eval(UttrykkContext ctx) {

        return ctx.eval(sum(satsSteg.stream().map(this::stegTilGrenseUttrykk).collect(Collectors.toList())));
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return satsSteg.stream().map(ss->stegTilString(ctx,ss)).collect(Collectors.joining(";", "multisats(", ")"));
    }

    private BelopUttrykk stegTilGrenseUttrykk(SatsStegUttrykk steg) {
        GrenseUttrykk grenseUttrykk = begrens(xVerdi.minus(steg.nedreGrense).multiplisertMed(steg.sats)).nedad(kr(0));
        if (steg.oevreGrense != null) {
            grenseUttrykk.oppad(steg.oevreGrense.minus(steg.nedreGrense).multiplisertMed(steg.sats));
        }

        return grenseUttrykk;
    }

    private String stegTilString(UttrykkContext ctx, SatsStegUttrykk steg) {
        String stegResultat;

        if (steg.oevreGrense != null) {
            stegResultat = "satsFraTil(" + ctx.beskriv(xVerdi)+";" + ctx.beskriv(steg.sats) + ";" + ctx.beskriv(steg.nedreGrense) + ";" + ctx.beskriv(steg.oevreGrense) + ")";
        } else {
            stegResultat = "satsFra(" + ctx.beskriv(xVerdi)+";" + ctx.beskriv(steg.sats) + ";" + ctx.beskriv(steg.nedreGrense) + ")";
        }
        return stegResultat;
    }


    public class SatsStegUttrykk extends AbstractSatsStegUttrykk<SatsStegUttrykk>  {
        private final TallUttrykk sats;
        private BelopUttrykk oevreGrense;
        private final BelopUttrykk nedreGrense;


        public SatsStegUttrykk(BelopMultisatsFunksjon funksjon, BelopUttrykk nedreGrense, TallUttrykk sats) {
            super(funksjon);
            this.sats = sats;
            this.nedreGrense = nedreGrense;
            this.funksjon.satsSteg.add(this);
        }

        public TilSatsStegUttrykk til(BelopUttrykk til) {
            oevreGrense = til;
            return new TilSatsStegUttrykk(funksjon);
        }

        public class TilSatsStegUttrykk extends AbstractSatsStegUttrykk<TilSatsStegUttrykk>  {

            public TilSatsStegUttrykk(BelopMultisatsFunksjon funksjon) {
                super(funksjon);
            }

            public SatsStegUttrykk deretterMedSats(TallUttrykk sats) {
                return new SatsStegUttrykk(funksjon, oevreGrense, sats);
            }

        }
    }

    protected abstract class AbstractSatsStegUttrykk<B extends AbstractSatsStegUttrykk<B>> extends AbstractUttrykk<Belop,B> implements BelopUttrykk {
        protected final BelopMultisatsFunksjon funksjon;

        @SuppressWarnings("unchecked")
        protected B self = (B) this;

        protected AbstractSatsStegUttrykk(BelopMultisatsFunksjon funksjon) {
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
