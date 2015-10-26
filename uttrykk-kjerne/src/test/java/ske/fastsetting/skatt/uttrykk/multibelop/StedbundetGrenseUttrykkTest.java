package ske.fastsetting.skatt.uttrykk.multibelop;

import static ske.fastsetting.skatt.uttrykk.multibelop.MultiBelopForholdsmessigGrenseUttrykk.begrensFordholdmessig;
import static ske.fastsetting.skatt.uttrykk.multibelop.StedbundetBelopMultisatsFunksjonTest.assertStedBelop;
import static ske.fastsetting.skatt.uttrykk.multibelop.MultiKroneUttrykk.kr;

import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;

public class StedbundetGrenseUttrykkTest {

    @Test
    public void testToStederBegrensOppadUnderGrense() {
        MultiBelopUttrykk<String> uttrykk = begrensFordholdmessig(kr(25, "A").pluss(kr(50, "B"))).oppad
          (KroneUttrykk.kr(100));

        assertStedBelop(uttrykk, Belop.kr(25), "A");
        assertStedBelop(uttrykk, Belop.kr(50), "B");
    }

    @Test
    public void testToStederBegrensOppadOverGrense() {
        MultiBelopUttrykk<String> uttrykk = begrensFordholdmessig(kr(100, "A").pluss(kr(50, "B"))).oppad
          (KroneUttrykk.kr(100));

        assertStedBelop(uttrykk, Belop.kr(67), "A");
        assertStedBelop(uttrykk, Belop.kr(33), "B");
    }

    @Test
    public void testToStederBegrensNedadOverGrense() {
        MultiBelopUttrykk<String> uttrykk = begrensFordholdmessig(kr(25, "A").pluss(kr(50, "B"))).nedad
          (KroneUttrykk.kr(50));

        assertStedBelop(uttrykk, Belop.kr(25), "A");
        assertStedBelop(uttrykk, Belop.kr(50), "B");
    }

    @Test
    public void testToStederBegrensNedadUnderGrense() {
        MultiBelopUttrykk<String> uttrykk = begrensFordholdmessig(kr(25, "A").pluss(kr(50, "B"))).nedad
          (KroneUttrykk.kr(100));

        assertStedBelop(uttrykk, Belop.kr(33), "A");
        assertStedBelop(uttrykk, Belop.kr(67), "B");
    }

}
