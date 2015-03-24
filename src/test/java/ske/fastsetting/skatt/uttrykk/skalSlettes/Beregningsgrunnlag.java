package ske.fastsetting.skatt.uttrykk.skalSlettes;

import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Skattegrunnlag;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

import java.util.function.Function;

public interface Beregningsgrunnlag {
    BruttoformueUttrykk getBruttoformue();

    BelopUttrykk getGjeld();

    AlderspensjonUttrykk getAlderspensjon();

    TallUttrykk getBjarne();

    public abstract class BeregningsgrunnlagUttrykk<V, U extends Uttrykk<V>, B extends AbstractUttrykk<V, B>> extends
      AbstractUttrykk<V, B> {


        private Function<Beregningsgrunnlag, U> function;

        public <T> BeregningsgrunnlagUttrykk(Function<Beregningsgrunnlag, U> function) {

            this.function = function;
        }


        @Override
        public V eval(UttrykkContext ctx) {

            Beregningsgrunnlag bg = getBeregningsgrunnlag(ctx);

            return ctx.eval(function.apply(bg));
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            Beregningsgrunnlag bg = getBeregningsgrunnlag(ctx);

            return ctx.beskriv(function.apply(bg));
        }

        private Beregningsgrunnlag getBeregningsgrunnlag(UttrykkContext ctx) {
            Beregningsgrunnlag bg;

            if (ctx.harInput(Beregningsgrunnlag.class)) {
                bg = ctx.input(Beregningsgrunnlag.class);
            } else if (ctx.harInput(Skattegrunnlag.class)) {
                bg = SumFastsettelser.hent();
            } else {
                bg = ctx.input(Beregningsgrunnlag.class);
            }
            return bg;
        }

        private static class SumFastsettelser {
            public static Beregningsgrunnlag hent() {
                return null;
            }
        }
    }

}
