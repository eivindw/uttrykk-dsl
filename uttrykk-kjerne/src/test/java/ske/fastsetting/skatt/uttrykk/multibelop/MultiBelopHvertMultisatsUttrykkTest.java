package ske.fastsetting.skatt.uttrykk.multibelop;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.multibelop.MultiBelopHverMultisatsUttrykk.multisatsHverAv;
import static ske.fastsetting.skatt.uttrykk.multibelop.MultiKroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.TestUttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;

public class MultiBelopHvertMultisatsUttrykkTest {
    @Test
    public void testEnkeltStedEnSatsGrunnlagUnderNull() {

        MultiBelopUttrykk<String> multisats
          = multisatsHverAv(kr(-5, "A"))
          .medSats(prosent(10));

        assertStedBelop(multisats, Belop.kr(0), "A");
    }

    @Test
    public void testToStederEnSatsBeggeUnderGense() {

        MultiBelopUttrykk<String> multisats
          = multisatsHverAv(kr(50, "A").pluss(kr(100, "B")))
          .medSats(prosent(10), KroneUttrykk.kr(200));

        assertStedBelop(multisats, Belop.kr(5), "A");
        assertStedBelop(multisats, Belop.kr(10), "B");
    }

    @Test
    public void testToStederEnSatsBeggeOverGense() {

        MultiBelopUttrykk<String> multisats
          = multisatsHverAv(kr(100, "A").pluss(kr(200, "B")))
          .medSats(prosent(100), KroneUttrykk.kr(80));

        assertStedBelop(multisats, Belop.kr(80), "A");
        assertStedBelop(multisats, Belop.kr(80), "B");
    }

    @Test
    public void testToStederToSatserEnOverOgEnUnderFoersteGense() {

        MultiBelopUttrykk<String> multisats
          = multisatsHverAv(kr(100, "A").pluss(kr(200, "B")))
          .medSats(prosent(10), KroneUttrykk.kr(150))
          .medSats(prosent(50), KroneUttrykk.kr(400));

        assertStedBelop(multisats, Belop.kr(10), "A");
        assertStedBelop(multisats, Belop.kr(40), "B");
    }

    @Test
    public void testToStederToSatserBeggeOverOeversteGense() {

        MultiBelopUttrykk<String> multisats
          = multisatsHverAv(kr(300, "A").pluss(kr(500, "B")))
          .medSats(prosent(100), KroneUttrykk.kr(100))
          .medSats(prosent(50), KroneUttrykk.kr(200));

        assertStedBelop(multisats, Belop.kr(150), "A");
        assertStedBelop(multisats, Belop.kr(150), "B");
    }



    static void assertStedBelop(MultiBelopUttrykk<String> uttrykk, Belop forventetBelop, String sted) {
        assertEquals(forventetBelop, TestUttrykkContext.beregne(uttrykk).verdi().get(sted).rundAvTilHeleKroner());
    }


}
