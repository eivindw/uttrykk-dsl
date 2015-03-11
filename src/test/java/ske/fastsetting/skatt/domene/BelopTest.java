package ske.fastsetting.skatt.domene;

import org.junit.Test;

import static org.junit.Assert.*;

public class BelopTest {

    @Test
    public void rundesAvTilOrer() {
        assertDoubleToBelop(10, 10.49);
        assertDoubleToBelop(11, 10.499);
        assertDoubleToBelop(11, 10.50);
    }

    public void assertDoubleToBelop(int forventet, double input) {
        assertEquals(Integer.valueOf(forventet), Belop.kr(input).toInteger());
    }
}