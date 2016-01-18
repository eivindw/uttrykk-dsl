package ske.fastsetting.skatt.uttrykk.multibelop;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.domene.MultiBelop.kr;

import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.skalSlettes.BeregningsgrunnlagTallUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public class MultiBelopTest {

    @Test
    public void skalFordelePositivtProporsjonaltPaaPositiveVerdier() {
        MultiBelop<Long> a = kr(2, 1L).pluss(kr(3, 2L));
        MultiBelop<Long> b = a.fordelProporsjonalt(Belop.kr(5));

        assertEquals(Belop.kr(4), b.get(1L));
        assertEquals(Belop.kr(6), b.get(2L));
    }

    @Test
    public void skalFordeleNegativtProporsjonaltPaaPositiveVerdier() {
        MultiBelop<String> a = kr(2, "A").pluss(kr(3, "B"));
        MultiBelop<String> b = a.fordelProporsjonalt(Belop.kr(-10));

        assertEquals(Belop.kr(-2), b.get("A"));
        assertEquals(Belop.kr(-3), b.get("B"));
    }

    @Test
    public void skalFordelePositivtProporsjonaltPaaNegativeVerdier() {
        MultiBelop<String> a = kr(-2, "A").pluss(kr(-3, "B"));
        MultiBelop<String> b = a.fordelProporsjonalt(Belop.kr(10));

        assertEquals(Belop.kr(2), b.get("A"));
        assertEquals(Belop.kr(3), b.get("B"));
    }

    @Test
    public void skalFordelePositivtProporsjonaltPaaNull() {
        MultiBelop<String> a = kr(0, "A").pluss(kr(0, "B"));
        MultiBelop<String> b = a.fordelProporsjonalt(Belop.kr(5));

        assertEquals(Belop.kr(2.5), b.get("A"));
        assertEquals(Belop.kr(2.5), b.get("B"));
    }

    @Test
    public void skalFordelePositivtProporsjonaltPaaNoenNull() {
        MultiBelop<String> a = kr(0, "A").pluss(kr(3, "B"));
        MultiBelop<String> b = a.fordelProporsjonalt(Belop.kr(5));

        assertEquals(Belop.kr(0), b.get("A"));
        assertEquals(Belop.kr(8), b.get("B"));
    }

    @Test
    public void skalFordeleProporsjonaltPaaSumNull() {
        MultiBelop<String> a = kr(-10, "A").pluss(kr(5, "B")).pluss(kr(5, "C"));
        MultiBelop<String> b = a.fordelProporsjonalt(Belop.kr(-6));

        assertEquals(Belop.kr(-13), b.get("A"));
        assertEquals(Belop.kr(3.5), b.get("B"));
        assertEquals(Belop.kr(3.5), b.get("C"));
    }

    @Test
    public void skalFordeleNullPaaSumNull() {
        MultiBelop<String> a = kr(-10, "A").pluss(kr(5, "B")).pluss(kr(5, "C"));
        MultiBelop<String> b = a.fordelProporsjonalt(Belop.kr(0));

        assertEquals(Belop.kr(-10), b.get("A"));
        assertEquals(Belop.kr(5), b.get("B"));
        assertEquals(Belop.kr(5), b.get("C"));
    }

    @Test
    public void skalFordeleNullPaaAlleNull() {
        MultiBelop<String> a = kr(0, "A").pluss(kr(0, "B"));
        MultiBelop<String> b = a.fordelProporsjonalt(Belop.kr(0));

        assertEquals(Belop.kr(0), b.get("A"));
        assertEquals(Belop.kr(0), b.get("B"));
    }

    @Test
    public void skalFordeleProporsjonaltPaaSmaaVerdier() {
        MultiBelop<String> a = kr(0.01, "A").pluss(kr(0.02, "B"));
        MultiBelop<String> b = a.fordelProporsjonalt(Belop.kr(6));

        assertEquals(Belop.kr(2), b.get("A").rundAvTilHeleKroner());
        assertEquals(Belop.kr(4), b.get("B").rundAvTilHeleKroner());
    }

    @Test
    public void beregningsgrunnlagTest() {
        TallUttrykk pensjonsgrad = BeregningsgrunnlagTallUttrykk.beregningsgrunnlag(bg -> bg.getAlderspensjon()
          .pensjonsgrad());

    }
}
