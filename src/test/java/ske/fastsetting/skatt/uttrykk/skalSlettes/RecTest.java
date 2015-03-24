package ske.fastsetting.skatt.uttrykk.skalSlettes;

import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;
import ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetBelopUttrykk;
import ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetKroneUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

import java.util.function.Function;

import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

public class RecTest {
    @Test
    public void recTest() {

        Rec<Beregningsgrunnlag> beregningsgrunnlag = new Rec<>();
        Rec<Alderspensjon> alderspensjonRec = new Rec<>();
        alderspensjonRec.put(Alderspensjon.pensjonsgrad, prosent(50));
        alderspensjonRec.put(Alderspensjon.belop, KroneUttrykk.kr(500));

        Rec<Bruttoformue> bruttoformue = new Rec<>();
        bruttoformue.put(Bruttoformue.fritidsbolig, StedbundetKroneUttrykk.kr(50, "A"));

        beregningsgrunnlag.put(Beregningsgrunnlag.alderspensjon, alderspensjonRec);
        beregningsgrunnlag.put(Beregningsgrunnlag.bruttoformue, bruttoformue);

        BelopUttrykk alderspensjon = new RecBelopUttrykk<>(r -> r.get(Beregningsgrunnlag.alderspensjon).get
          (Alderspensjon.belop));

        StedbundetBelopUttrykk fritidsbolig = new RecStedbundetBelopUttrykk<>(r -> r.get(Beregningsgrunnlag
          .bruttoformue).get(Bruttoformue.fritidsbolig));

        TallUttrykk pensjonsgrad = new RecTallUttrykk<>(r -> r.get(Beregningsgrunnlag.alderspensjon).get
          (Alderspensjon.pensjonsgrad));

        BelopUttrykk fullPensjon = alderspensjon.dividertMed(pensjonsgrad);
    }

    static interface Beregningsgrunnlag {
        Rec.Key<Rec<Alderspensjon>> alderspensjon = new Rec.Key<>();
        Rec.Key<BelopUttrykk> gjeld = new Rec.Key<>();
        Rec.Key<Rec<Bruttoformue>> bruttoformue = new Rec.Key<>();
    }

    static interface Alderspensjon {
        Rec.Key<BelopUttrykk> belop = new Rec.Key<>();
        Rec.Key<ProsentUttrykk> pensjonsgrad = new Rec.Key<>();

    }

    static interface Bruttoformue {
        Rec.Key<StedbundetBelopUttrykk> fritidsbolig = new Rec.Key<>();
        Rec.Key<StedbundetBelopUttrykk> ovrigStedbundenFormue = new Rec.Key<>();
        Rec.Key<BelopUttrykk> ovrigFormue = new Rec.Key<>();
    }

    static abstract class RecUttrykk<V, T> extends AbstractUttrykk<V, RecUttrykk<V, T>> {

        private Function<Rec<T>, Uttrykk<V>> func;

        RecUttrykk(Function<Rec<T>, Uttrykk<V>> func) {
            this.func = func;
        }

        @Override
        public V eval(UttrykkContext ctx) {
            Rec<T> rec = (Rec<T>) ctx.input(Rec.class);

            final Uttrykk<V> apply = func.apply(rec);
            return ctx.eval(apply);
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            Rec<T> rec = (Rec<T>) ctx.input(Rec.class);

            return ctx.beskriv(func.apply(rec));
        }
    }

    static class RecBelopUttrykk<T> extends RecUttrykk<Belop, T> implements BelopUttrykk {

        RecBelopUttrykk(Function<Rec<T>, Uttrykk<Belop>> func) {
            super(func);
        }

    }

    static class RecStedbundetBelopUttrykk<K, T> extends RecUttrykk<StedbundetBelop<K>, T> implements
      StedbundetBelopUttrykk<K> {

        RecStedbundetBelopUttrykk(Function<Rec<T>, Uttrykk<StedbundetBelop<K>>> func) {
            super(func);
        }
    }

    static class RecTallUttrykk<T> extends RecUttrykk<Tall, T> implements TallUttrykk {

        RecTallUttrykk(Function<Rec<T>, Uttrykk<Tall>> func) {
            super(func);
        }
    }

}
