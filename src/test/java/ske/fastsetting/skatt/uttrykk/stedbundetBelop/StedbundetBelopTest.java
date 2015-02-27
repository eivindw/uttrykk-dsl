package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.domene.StedbundetBelop.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

/**
 * Created by jorn ola birkeland on 24.02.15.
 */
public class StedbundetBelopTest {

    @Test
    public void skalFordelePositivtProporsjonaltPaaPositiveVerdier() {
        StedbundetBelop a = kr(2, "A").pluss(kr(3, "B"));
        StedbundetBelop b = a.fordelProporsjonalt(Belop.kr(5));

        assertEquals(Belop.kr(4), b.get("A"));
        assertEquals(Belop.kr(6), b.get("B"));
    }

    @Test
    public void skalFordeleNegativtProporsjonaltPaaPositiveVerdier() {
        StedbundetBelop a = kr(2, "A").pluss(kr(3, "B"));
        StedbundetBelop b = a.fordelProporsjonalt(Belop.kr(-10));

        assertEquals(Belop.kr(-2), b.get("A"));
        assertEquals(Belop.kr(-3), b.get("B"));
    }

    @Test
    public void skalFordelePositivtProporsjonaltPaaNegativeVerdier() {
        StedbundetBelop a = kr(-2, "A").pluss(kr(-3, "B"));
        StedbundetBelop b = a.fordelProporsjonalt(Belop.kr(10));

        assertEquals(Belop.kr(2), b.get("A"));
        assertEquals(Belop.kr(3), b.get("B"));
    }


    @Test
    public void skalFordelePositivtProporsjonaltPaaNull() {
        StedbundetBelop a = kr(0, "A").pluss(kr(0, "B"));
        StedbundetBelop b = a.fordelProporsjonalt(Belop.kr(5));

        assertEquals(Belop.kr(2.5), b.get("A"));
        assertEquals(Belop.kr(2.5), b.get("B"));
    }

    @Test
    public void skalFordelePositivtProporsjonaltPaaNoenNull() {
        StedbundetBelop a = kr(0, "A").pluss(kr(3, "B"));
        StedbundetBelop b = a.fordelProporsjonalt(Belop.kr(5));

        assertEquals(Belop.kr(0), b.get("A"));
        assertEquals(Belop.kr(8), b.get("B"));
    }

    @Test
    public void skalFordeleProporsjonaltPaaSumNull() {
        StedbundetBelop a = kr(-10, "A").pluss(kr(5, "B")).pluss(kr(5, "C"));
        StedbundetBelop b = a.fordelProporsjonalt(Belop.kr(-6));

        assertEquals(Belop.kr(-13), b.get("A"));
        assertEquals(Belop.kr(3.5), b.get("B"));
        assertEquals(Belop.kr(3.5), b.get("C"));
    }

    @Test
    public void skalFordeleNullPaaSumNull() {
        StedbundetBelop a = kr(-10, "A").pluss(kr(5, "B")).pluss(kr(5, "C"));
        StedbundetBelop b = a.fordelProporsjonalt(Belop.kr(0));

        assertEquals(Belop.kr(-10), b.get("A"));
        assertEquals(Belop.kr(5), b.get("B"));
        assertEquals(Belop.kr(5), b.get("C"));
    }

    @Test
    public void skalFordeleNullPaaAlleNull() {
        StedbundetBelop a = kr(0, "A").pluss(kr(0, "B"));
        StedbundetBelop b = a.fordelProporsjonalt(Belop.kr(0));

        assertEquals(Belop.kr(0), b.get("A"));
        assertEquals(Belop.kr(0), b.get("B"));
    }

    @Test
    public void skalFordeleProporsjonaltPaaSmaaVerdier() {
        StedbundetBelop a = kr(0.000001d, "A").pluss(kr(0.000002d, "B"));
        StedbundetBelop b = a.fordelProporsjonalt(Belop.kr(6));

        assertEquals(Belop.kr(2), b.get("A").rundAvTilHeleKroner());
        assertEquals(Belop.kr(4), b.get("B").rundAvTilHeleKroner());
    }

    @Test
    public void beregningsgrunnlagTest() {
        TallUttrykk pensjonsgrad = BeregningsgrunnlagTallUttrykk.beregningsgrunnlag(bg->bg.getAlderspensjon().pensjonsgrad());

    }

    @Test
    public void recTest() {

        Rec.Key<Rec> alderspensjonKey = new Rec.Key<>();
        Rec.Key<BelopUttrykk> belopKey = new Rec.Key<>();
        Rec.Key<TallUttrykk> pensjonsgradKey = new Rec.Key<>();

        Rec beregningsgrunnlag = new Rec();
        Rec alderspensjonRec = new Rec();

        beregningsgrunnlag.put(alderspensjonKey,alderspensjonRec);
        alderspensjonRec.put(belopKey, KroneUttrykk.kr(45));
        alderspensjonRec.put(pensjonsgradKey, prosent(45));

        TallUttrykk pensjonsgrad = beregningsgrunnlag.get(alderspensjonKey).get(pensjonsgradKey);

        BelopUttrykk alderspensjon = new RecBelopUttrykk(r->r.get(alderspensjonKey).get(belopKey));
    }


    static class RecBelopUttrykk extends AbstractUttrykk<Belop,RecBelopUttrykk> implements BelopUttrykk {


        private Function<Rec, BelopUttrykk> func;

        RecBelopUttrykk(Function<Rec,BelopUttrykk> func) {
            this.func = func;
        }

        @Override
        public Belop eval(UttrykkContext ctx) {
            Rec rec = ctx.input(Rec.class);

            return ctx.eval(func.apply(rec));
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            Rec rec = ctx.input(Rec.class);

            return ctx.beskriv(func.apply(rec));
        }
    }



}
