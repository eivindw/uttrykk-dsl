package ske.fastsetting.skatt.uttrykk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// V - type på verdi som returneres ved eval, f.eks Belop, Tall eller MultiBelop
// G - type på grunnlag, f.eks Belop eller Distanse
// S - type på sats, Tall eller BelopPerKvantitet
// L - type på "limits" - på øvre og nedre grenser
// B - baseklasse - brukes for self-referanser
public abstract class MultisatsUttrykk<V, G, S, L, B extends MultisatsUttrykk<V, G, S, L, B>> extends
  AbstractUttrykk<V, B> {

    private Uttrykk<G> grunnlag;
    private List<Uttrykk<V>> alleSatsSteg = new ArrayList<>();
    private SatsStegUttrykk<V, G, S, L> gjeldendeSatsSteg;

    public MultisatsUttrykk(Uttrykk<G> grunnlag) {
        this.grunnlag = grunnlag;
    }

    public B medSats(Uttrykk<S> sats) {
        return medSats(sats, null);
    }

    public B medSats(Uttrykk<S> sats, Uttrykk<L> oevreGrense) {
        Uttrykk<L> nedreGrense = gjeldendeSatsSteg != null ? gjeldendeSatsSteg.oevreGrense : null;

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

    protected abstract SatsStegUttrykk<V, G, S, L> lagSteg();

    protected abstract Uttrykk<V> sum(Collection<Uttrykk<V>> satsSteg);

    /**
     * Created by jorn ola birkeland on 20.03.15.
     */
    public static abstract class SatsStegUttrykk<V, G, S, L> extends AbstractUttrykk<V, SatsStegUttrykk<V, G, S, L>> {
        protected Uttrykk<S> sats;
        protected Uttrykk<L> oevreGrense;
        protected Uttrykk<L> nedreGrense;
        protected Uttrykk<G> grunnlag;

        protected SatsStegUttrykk() {
        }

        @Override
        public final String beskriv(UttrykkContext ctx) {
            String stegResultat;

            if (oevreGrense != null && nedreGrense != null) {
                stegResultat = "satsFraTil(" + ctx.beskriv(grunnlag) + "," + ctx.beskriv(sats) + "," + ctx.beskriv
                  (nedreGrense) + "," + ctx.beskriv(oevreGrense) + ")";
            } else if (nedreGrense != null) {
                stegResultat = "satsFra(" + ctx.beskriv(grunnlag) + "," + ctx.beskriv(sats) + "," + ctx.beskriv
                  (nedreGrense) + ")";
            } else if (oevreGrense != null) {
                stegResultat = "satsTil(" + ctx.beskriv(grunnlag) + "," + ctx.beskriv(sats) + "," + ctx.beskriv
                  (oevreGrense) + ")";
            } else {
                stegResultat = "sats(" + ctx.beskriv(grunnlag) + "," + ctx.beskriv(sats) + ")";
            }
            return stegResultat;
        }

        public SatsStegUttrykk<V, G, S, L> medOevreGrense(Uttrykk<L> oevregrense) {
            if (oevregrense != null) {
                this.oevreGrense = oevregrense;
            }
            return this;
        }

        public SatsStegUttrykk<V, G, S, L> medNedreGrense(Uttrykk<L> nedregrense) {
            if (nedregrense != null) {
                this.nedreGrense = nedregrense;
            }
            return this;
        }

        public SatsStegUttrykk<V, G, S, L> medSats(Uttrykk<S> sats) {
            if (sats != null) {
                this.sats = sats;
            }
            return this;
        }

        public SatsStegUttrykk<V, G, S, L> medGrunnlag(Uttrykk<G> grunnlag) {
            if (grunnlag == null) {
                throw new IllegalArgumentException("grunnlag kan ikke være null");
            }

            this.grunnlag = grunnlag;
            return this;
        }

    }
}
