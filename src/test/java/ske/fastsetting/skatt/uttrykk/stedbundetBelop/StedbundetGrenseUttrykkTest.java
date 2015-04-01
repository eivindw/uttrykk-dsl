package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;

import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StebundetBelopForholdsmessigGrenseUttrykk.begrensFordholdmessig;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetBelopMultisatsFunksjonTest.assertStedBelop;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetKroneUttrykk.kr;

public class StedbundetGrenseUttrykkTest {

    @Test
    public void testToStederBegrensOppadUnderGrense() {
        StedbundetBelopUttrykk<String> uttrykk = begrensFordholdmessig(kr(25, "A").pluss(kr(50, "B"))).oppad
          (KroneUttrykk.kr(100));

        assertStedBelop(uttrykk, Belop.kr(25), "A");
        assertStedBelop(uttrykk, Belop.kr(50), "B");
    }

    @Test
    public void testToStederBegrensOppadOverGrense() {
        StedbundetBelopUttrykk<String> uttrykk = begrensFordholdmessig(kr(100, "A").pluss(kr(50, "B"))).oppad
          (KroneUttrykk.kr(100));

        assertStedBelop(uttrykk, Belop.kr(67), "A");
        assertStedBelop(uttrykk, Belop.kr(33), "B");
    }

    @Test
    public void testToStederBegrensNedadOverGrense() {
        StedbundetBelopUttrykk<String> uttrykk = begrensFordholdmessig(kr(25, "A").pluss(kr(50, "B"))).nedad
          (KroneUttrykk.kr(50));

        assertStedBelop(uttrykk, Belop.kr(25), "A");
        assertStedBelop(uttrykk, Belop.kr(50), "B");
    }

    @Test
    public void testToStederBegrensNedadUnderGrense() {
        StedbundetBelopUttrykk<String> uttrykk = begrensFordholdmessig(kr(25, "A").pluss(kr(50, "B"))).nedad
          (KroneUttrykk.kr(100));

        assertStedBelop(uttrykk, Belop.kr(33), "A");
        assertStedBelop(uttrykk, Belop.kr(67), "B");
    }

}
