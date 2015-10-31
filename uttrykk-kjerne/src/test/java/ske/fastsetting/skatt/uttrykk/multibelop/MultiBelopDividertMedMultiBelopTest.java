package ske.fastsetting.skatt.uttrykk.multibelop;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.multibelop.MultiKroneUttrykk.kr;

import java.math.BigDecimal;

import org.junit.Test;

import ske.fastsetting.skatt.domene.MultiTall;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.TestUttrykkContext;
import ske.fastsetting.skatt.uttrykk.UttrykkException;
import ske.fastsetting.skatt.uttrykk.multitall.MultiTallUttrykk;

public class MultiBelopDividertMedMultiBelopTest {
    @Test
    public void skalDividereLiktAntall() {
        MultiBelopUttrykk<String> divdent = kr(30, "A").pluss(kr(60, "B"));
        MultiBelopUttrykk<String> divisor = kr(3, "A").pluss(kr(10, "B"));

        MultiTallUttrykk<String> kvotientUttrykk = divdent.hverDividertMed(divisor);

        MultiTall<String> kvotient = TestUttrykkContext.verdiAv(kvotientUttrykk);

        assertEquals(Tall.ukjent(BigDecimal.valueOf(10.0)), kvotient.get("A"));
        assertEquals(Tall.ukjent(BigDecimal.valueOf(6.0)), kvotient.get("B"));
    }

    @Test(expected = UttrykkException.class)
    public void skalFeileNaarDividentHarFlere() {
        MultiBelopUttrykk<String> divdent = kr(30, "A").pluss(kr(60, "B"));
        MultiBelopUttrykk<String> divisor = kr(3, "A");

        MultiTallUttrykk<String> kvotientUttrykk = divdent.hverDividertMed(divisor);

        TestUttrykkContext.verdiAv(kvotientUttrykk);
    }

    @Test
    public void skalDividereNaarDivisorHarFlere() {
        MultiBelopUttrykk<String> divdent = kr(30, "A");
        MultiBelopUttrykk<String> divisor = kr(3, "A").pluss(kr(10, "B"));

        MultiTallUttrykk<String> kvotientUttrykk = divdent.hverDividertMed(divisor);

        MultiTall<String> kvotient = TestUttrykkContext.verdiAv(kvotientUttrykk);

        assertEquals(Tall.ukjent(BigDecimal.valueOf(10.0)), kvotient.get("A"));
        assertEquals(Tall.ZERO, kvotient.get("B"));
    }

}
