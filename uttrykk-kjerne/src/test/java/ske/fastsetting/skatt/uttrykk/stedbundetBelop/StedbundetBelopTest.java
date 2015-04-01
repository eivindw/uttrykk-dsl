package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.domene.StedbundetBelop.kr;

import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.skalSlettes.BeregningsgrunnlagTallUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public class StedbundetBelopTest {

    @Test
    public void skalFordelePositivtProporsjonaltPaaPositiveVerdier() {
        StedbundetBelop<Long> a = kr(2, 1L).pluss(kr(3, 2L));
        StedbundetBelop<Long> b = a.fordelProporsjonalt(Belop.kr(5));

        assertEquals(Belop.kr(4), b.get(1L));
        assertEquals(Belop.kr(6), b.get(2L));
    }

    @Test
    public void skalFordeleNegativtProporsjonaltPaaPositiveVerdier() {
        StedbundetBelop<String> a = kr(2, "A").pluss(kr(3, "B"));
        StedbundetBelop<String> b = a.fordelProporsjonalt(Belop.kr(-10));

        assertEquals(Belop.kr(-2), b.get("A"));
        assertEquals(Belop.kr(-3), b.get("B"));
    }

    @Test
    public void skalFordelePositivtProporsjonaltPaaNegativeVerdier() {
        StedbundetBelop<String> a = kr(-2, "A").pluss(kr(-3, "B"));
        StedbundetBelop<String> b = a.fordelProporsjonalt(Belop.kr(10));

        assertEquals(Belop.kr(2), b.get("A"));
        assertEquals(Belop.kr(3), b.get("B"));
    }

    @Test
    public void skalFordelePositivtProporsjonaltPaaNull() {
        StedbundetBelop<String> a = kr(0, "A").pluss(kr(0, "B"));
        StedbundetBelop<String> b = a.fordelProporsjonalt(Belop.kr(5));

        assertEquals(Belop.kr(2.5), b.get("A"));
        assertEquals(Belop.kr(2.5), b.get("B"));
    }

    @Test
    public void skalFordelePositivtProporsjonaltPaaNoenNull() {
        StedbundetBelop<String> a = kr(0, "A").pluss(kr(3, "B"));
        StedbundetBelop<String> b = a.fordelProporsjonalt(Belop.kr(5));

        assertEquals(Belop.kr(0), b.get("A"));
        assertEquals(Belop.kr(8), b.get("B"));
    }

    @Test
    public void skalFordeleProporsjonaltPaaSumNull() {
        StedbundetBelop<String> a = kr(-10, "A").pluss(kr(5, "B")).pluss(kr(5, "C"));
        StedbundetBelop<String> b = a.fordelProporsjonalt(Belop.kr(-6));

        assertEquals(Belop.kr(-13), b.get("A"));
        assertEquals(Belop.kr(3.5), b.get("B"));
        assertEquals(Belop.kr(3.5), b.get("C"));
    }

    @Test
    public void skalFordeleNullPaaSumNull() {
        StedbundetBelop<String> a = kr(-10, "A").pluss(kr(5, "B")).pluss(kr(5, "C"));
        StedbundetBelop<String> b = a.fordelProporsjonalt(Belop.kr(0));

        assertEquals(Belop.kr(-10), b.get("A"));
        assertEquals(Belop.kr(5), b.get("B"));
        assertEquals(Belop.kr(5), b.get("C"));
    }

    @Test
    public void skalFordeleNullPaaAlleNull() {
        StedbundetBelop<String> a = kr(0, "A").pluss(kr(0, "B"));
        StedbundetBelop<String> b = a.fordelProporsjonalt(Belop.kr(0));

        assertEquals(Belop.kr(0), b.get("A"));
        assertEquals(Belop.kr(0), b.get("B"));
    }

    @Test
    public void skalFordeleProporsjonaltPaaSmaaVerdier() {
        StedbundetBelop<String> a = kr(0.01, "A").pluss(kr(0.02, "B"));
        StedbundetBelop<String> b = a.fordelProporsjonalt(Belop.kr(6));

        assertEquals(Belop.kr(2), b.get("A").rundAvTilHeleKroner());
        assertEquals(Belop.kr(4), b.get("B").rundAvTilHeleKroner());
    }

    @Test
    public void beregningsgrunnlagTest() {
        TallUttrykk pensjonsgrad = BeregningsgrunnlagTallUttrykk.beregningsgrunnlag(bg -> bg.getAlderspensjon()
          .pensjonsgrad());

    }
}
