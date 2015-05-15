package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetBelopMultisatsFunksjon.multisatsFunksjonAv;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetKroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.TestUttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;

public class StedbundetBelopMultisatsFunksjonTest {


    @Test
    public void testEnkeltStedEnSatsGrunnlagUnderNull() {

        StedbundetBelopUttrykk<String> multisats
          = multisatsFunksjonAv(kr(-5, "A"))
          .medSats(prosent(10));

        assertStedBelop(multisats, Belop.kr(0), "A");
    }

    @Test
    public void testEnkeltStedEnSatsUnderGrense() {

        StedbundetBelopUttrykk<String> multisats
          = multisatsFunksjonAv(kr(30, "A"))
          .medSats(prosent(10), KroneUttrykk.kr(50));

        assertStedBelop(multisats, Belop.kr(3), "A");
    }

    @Test
    public void testEnkeltStedEnSatsOverGrense() {

        StedbundetBelopUttrykk<String> multisats
          = multisatsFunksjonAv(kr(70, "A"))
          .medSats(prosent(10), KroneUttrykk.kr(50));

        assertStedBelop(multisats, Belop.kr(5), "A");
    }

    @Test
    public void testEnkeltStedToSatserUnderNedersteGrense() {

        StedbundetBelopUttrykk<String> multisats
          = multisatsFunksjonAv(kr(10, "A"))
          .medSats(prosent(10), KroneUttrykk.kr(20))
          .medSats(prosent(5), KroneUttrykk.kr(50));

        assertStedBelop(multisats, Belop.kr(1), "A");
    }


    @Test
    public void testEnkeltStedToSatserUnderOevrsteGense() {

        StedbundetBelopUttrykk<String> multisats
          = multisatsFunksjonAv(kr(30, "A"))
          .medSats(prosent(100), KroneUttrykk.kr(20))
          .medSats(prosent(50), KroneUttrykk.kr(50));

        assertStedBelop(multisats, Belop.kr(25), "A");
    }

    @Test
    public void testEnkeltStedToSatserOverOevrsteGense() {

        StedbundetBelopUttrykk<String> multisats
          = multisatsFunksjonAv(kr(70, "A"))
          .medSats(prosent(100), KroneUttrykk.kr(20))
          .medSats(prosent(50), KroneUttrykk.kr(50));

        assertStedBelop(multisats, Belop.kr(35), "A");
    }

    @Test
    public void testToStederEnSatsBeggeGrunnlagUnderNull() {

        StedbundetBelopUttrykk<String> multisats
          = multisatsFunksjonAv(kr(-5, "A").pluss(kr(-10, "B")))
          .medSats(prosent(10));

        assertStedBelop(multisats, Belop.kr(0), "A");
        assertStedBelop(multisats, Belop.kr(0), "B");
    }

    @Test
    public void testToStederEnSatsUnderGense() {

        StedbundetBelopUttrykk<String> multisats
          = multisatsFunksjonAv(kr(50, "A").pluss(kr(100, "B")))
          .medSats(prosent(10), KroneUttrykk.kr(200));

        assertStedBelop(multisats, Belop.kr(5), "A");
        assertStedBelop(multisats, Belop.kr(10), "B");
    }

    @Test
    public void testToStederEnSatsOverGense() {

        StedbundetBelopUttrykk<String> multisats
          = multisatsFunksjonAv(kr(100, "A").pluss(kr(200, "B")))
          .medSats(prosent(100), KroneUttrykk.kr(200));

        assertStedBelop(multisats, Belop.kr(67), "A");
        assertStedBelop(multisats, Belop.kr(133), "B");
    }

    @Test
    public void testToStederToSatserUnderOeversteGense() {

        StedbundetBelopUttrykk<String> multisats
          = multisatsFunksjonAv(kr(100, "A").pluss(kr(200, "B")))
          .medSats(prosent(100), KroneUttrykk.kr(200))
          .medSats(prosent(50), KroneUttrykk.kr(400));

        // A: 66.67 + 16.67
        // B: 133.33 + 33.33

        assertStedBelop(multisats, Belop.kr(83), "A");
        assertStedBelop(multisats, Belop.kr(167), "B");
    }

    @Test
    public void testToStederToSatserOverOeversteGense() {

        StedbundetBelopUttrykk<String> multisats
          = multisatsFunksjonAv(kr(200, "A").pluss(kr(400, "B")))
          .medSats(prosent(100), KroneUttrykk.kr(200))
          .medSats(prosent(50), KroneUttrykk.kr(400));

        // A: 66.67 + 33.33
        // B 133.33 + 66.67

        assertStedBelop(multisats, Belop.kr(100), "A");
        assertStedBelop(multisats, Belop.kr(200), "B");
    }

    @Test
    public void testToStederEnSatsEttGrunnlagUnderNull() {

        StedbundetBelopUttrykk<String> multisats
          = multisatsFunksjonAv(kr(-10, "A").pluss(kr(10, "B")))
          .medSats(prosent(50));

        assertStedBelop(multisats, Belop.kr(-5), "A");
        assertStedBelop(multisats, Belop.kr(5), "B");
    }

    @Test
    public void testEtStedToSatserOeverstegrenseLavereEnnUnderste() {
        StedbundetBelopUttrykk<String> multisats
          = multisatsFunksjonAv(kr(100, "A"))
          .medSats(prosent(50), KroneUttrykk.kr(50))
          .medSats(prosent(25), KroneUttrykk.kr(0));

        assertStedBelop(multisats, Belop.kr(25), "A");

    }

    static void assertStedBelop(StedbundetBelopUttrykk<String> uttrykk, Belop forventetBelop, String sted) {
        assertEquals(forventetBelop, TestUttrykkContext.beregne(uttrykk).verdi().get(sted).rundAvTilHeleKroner());
    }


}
