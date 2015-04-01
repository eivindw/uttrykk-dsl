package ske.fastsetting.skatt.domene;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static ske.fastsetting.skatt.domene.Belop.kr;

public class BelopTest {

    @Test
    public void rundesAvTilOrer() {
        assertDoubleToBelop(10, 10.49);
        assertDoubleToBelop(11, 10.499);
        assertDoubleToBelop(11, 10.50);
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

    public void assertDoubleToBelop(int forventet, double input) {
        assertBelop(forventet, kr(input));
    }

    public void assertBelop(int forventet, Belop belop) {
        assertEquals(Integer.valueOf(forventet), belop.toInteger());
    }
}