package ske.fastsetting.skatt.uttrykk.multibelop;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.multibelop.MultiKroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.multitall.MultiTallKonstantUttrykk.heltall;

import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.TestUttrykkContext;
import ske.fastsetting.skatt.uttrykk.multitall.MultiTallUttrykk;

public class MultiBelopMultiplisertMedMultiTallTest {
    @Test
    public void skalMultiplisereFlereEnTilEn() {
        MultiTallUttrykk<String> tallUttrykk = heltall(10,"A").pluss(heltall(6,"B"));

        MultiBelopUttrykk<String> belopUttrykk = kr(2, "A").pluss(kr(4, "B"));

        MultiBelopUttrykk<String> produktUttrykk = belopUttrykk.hverMultiplisertMed(tallUttrykk);


        MultiBelop<String> produkt = TestUttrykkContext.verdiAv(produktUttrykk);


        assertEquals(Belop.kr(20), produkt.get("A"));
        assertEquals(Belop.kr(24), produkt.get("B"));
    }

    @Test
    public void skalMultiplisereFlereNaarMultiBelopHarFaerre() {
        MultiTallUttrykk<String> tallUttrykk = heltall(10,"A").pluss(heltall(6, "B"));

        MultiBelopUttrykk<String> belopUttrykk = kr(2, "A");

        MultiBelopUttrykk<String> produktUttrykk = belopUttrykk.hverMultiplisertMed(tallUttrykk);

        MultiBelop<String> produkt = TestUttrykkContext.verdiAv(produktUttrykk);


        assertEquals(Belop.kr(20), produkt.get("A"));
        assertEquals(Belop.kr(0), produkt.get("B"));
    }

    @Test
    public void skalMultiplisereFlereNaarMultiBelopHarFlere() {
        MultiTallUttrykk<String> tallUttrykk = heltall(10,"A").pluss(heltall(6, "B"));

        MultiBelopUttrykk<String> belopUttrykk = kr(2, "A").pluss(kr(4,"B")).pluss(kr(3,"C"));

        MultiBelopUttrykk<String> produktUttrykk = belopUttrykk.hverMultiplisertMed(tallUttrykk);

        MultiBelop<String> produkt = TestUttrykkContext.verdiAv(produktUttrykk);


        assertEquals(Belop.kr(20), produkt.get("A"));
        assertEquals(Belop.kr(24), produkt.get("B"));
        assertEquals(Belop.kr(0), produkt.get("C"));
    }

}
