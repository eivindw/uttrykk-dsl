package ske.fastsetting.skatt.uttrykk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class MultisatsUttrykk<V,K,S,B extends MultisatsUttrykk<V, K,S,B>> extends AbstractUttrykk<V,B> {

    private Uttrykk<K> grunnlag;
    private List<Uttrykk<V>> alleSatsSteg = new ArrayList<>();
    private SatsStegUttrykk<V,K,S> gjeldendeSatsSteg;

    public MultisatsUttrykk(Uttrykk<K> grunnlag) {
        this.grunnlag = grunnlag;
    }

    public B medSats(Uttrykk<S> sats) {
         return medSats(sats,null);
    }

    public B medSats(Uttrykk<S> sats, Uttrykk<K> oevreGrense) {
        Uttrykk<K> nedreGrense = gjeldendeSatsSteg!=null ? gjeldendeSatsSteg.oevreGrense : null;

        gjeldendeSatsSteg = lagSteg()
                .medGrunnlag(grunnlag)
                .medNedreGrense(nedreGrense)
                .medSats(sats)
                .medOevreGrense(oevreGrense);

        alleSatsSteg.add(gjeldendeSatsSteg);

        return self;
    }

    @Override
    public V eval(UttrykkContext ctx) {
        return ctx.eval(sum(alleSatsSteg));
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return alleSatsSteg.stream().map(ctx::beskriv).collect(Collectors.joining(",", "multisats(", ")"));
    }

    protected abstract SatsStegUttrykk<V,K, S> lagSteg();
    protected abstract Uttrykk<V> sum(Collection<Uttrykk<V>> satsSteg);

    /**
    * Created by jorn ola birkeland on 20.03.15.
    */
    public static abstract class SatsStegUttrykk<V,K,S> extends AbstractUttrykk<V,SatsStegUttrykk<V,K,S>>  {
        protected Uttrykk<S> sats;
        protected Uttrykk<K> oevreGrense;
        protected Uttrykk<K> nedreGrense;
        protected Uttrykk<K> grunnlag;

        protected SatsStegUttrykk() {
        }

        @Override
        public final String beskriv(UttrykkContext ctx) {
            String stegResultat;

            if (oevreGrense != null && nedreGrense!=null) {
                stegResultat = "satsFraTil(" + ctx.beskriv(grunnlag)+"," + ctx.beskriv(sats) + "," + ctx.beskriv(nedreGrense) + "," + ctx.beskriv(oevreGrense) + ")";
            } else if(nedreGrense!=null) {
                stegResultat = "satsFra(" + ctx.beskriv(grunnlag)+"," + ctx.beskriv(sats) + "," + ctx.beskriv(nedreGrense) + ")";
            } else if(oevreGrense!=null) {
                stegResultat = "satsTil(" + ctx.beskriv(grunnlag)+"," + ctx.beskriv(sats) + "," + ctx.beskriv(oevreGrense) + ")";
            } else {
                stegResultat = "sats(" + ctx.beskriv(grunnlag)+"," + ctx.beskriv(sats) +")";
            }
            return stegResultat;
        }

        public SatsStegUttrykk<V,K,S> medOevreGrense(Uttrykk<K> oevregrense) {
            if(oevregrense!=null) { this.oevreGrense = oevregrense; }
            return this;
        }

        public SatsStegUttrykk<V,K,S> medNedreGrense(Uttrykk<K> nedregrense) {
            if(nedregrense!=null) { this.nedreGrense = nedregrense; }
            return this;
        }

        public SatsStegUttrykk<V,K,S> medSats(Uttrykk<S> sats) {
            if(sats!=null) { this.sats = sats; }
            return this;
        }

        public SatsStegUttrykk<V,K,S> medGrunnlag(Uttrykk<K> grunnlag) {
            if(grunnlag==null) { throw new IllegalArgumentException("grunnlag kan ikke være null");}

            this.grunnlag = grunnlag;
            return this;
        }

    }
}
