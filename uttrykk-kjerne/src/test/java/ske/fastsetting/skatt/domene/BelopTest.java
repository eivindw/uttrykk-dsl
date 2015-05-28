package ske.fastsetting.skatt.domene;


import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.domene.Belop.kr;

import java.math.BigDecimal;

import org.junit.Test;

public class BelopTest {

    @Test
    public void rundesAvTilOrer() {
        assertBelop(10, 10.49);
        assertBelop(11, 10.499);
        assertBelop(11, 10.50);
    }

    @Test
    public void testAvrundingAvVeldigHoyeBelop(){
        assertBelop(999999999999999L,999999999999999.2);
        assertBelop(1000000000000000L,999999999999999.5);
    }

    @Test
    public void avrunding() {
        assertBelop(125, kr(123).rundAvTilNaermeste(5));
        assertBelop(120, kr(123).rundAvTilNaermeste(10));
        assertBelop(100, kr(123).rundAvTilNaermeste(100));
        assertBelop(0, kr(123).rundAvTilNaermeste(1000));
    }

    @Test
    public void forholdstall() {
        final BigDecimal forhold = kr(2).dividertMed(kr(3));

        assertEquals("0.6666666666666666", forhold.toPlainString());
    }

    @Test
    public void divisjonMultiplikasjon() {
        final Belop belop = kr(5).dividertMed(2);

        assertBelop(3, belop);
        assertBelop(5, belop.multiplisertMed(BigDecimal.valueOf(2)));
    }


    public void assertBelop(int forventet, Belop belop) {
        assertBelop((long) forventet, belop);
    }

    public void assertBelop(int forventet, double belop) {
        assertBelop((long) forventet, belop);
    }

    public void assertBelop(Long forventet, double belop) {
        assertBelop(forventet, Belop.kr(belop));
    }

    public void assertBelop(Long forventet, Belop belop) {
        assertEquals(forventet, belop.tilHeleKroner());
    }

}