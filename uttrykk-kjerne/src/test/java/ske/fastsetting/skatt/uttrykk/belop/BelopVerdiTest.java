package ske.fastsetting.skatt.uttrykk.belop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static ske.fastsetting.skatt.uttrykk.TestUttrykkContext.verdiAv;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;
import static ske.fastsetting.skatt.uttrykk.tall.TallKonstantUttrykk.heltall;

import java.math.BigInteger;

import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.UttrykkException;

public class BelopVerdiTest {

    @Test
    public void testVerdier() {
        System.out.println(verdiAv(kr(BigInteger.valueOf(10))));
        System.out.println(verdiAv(kr(10)));
        System.out.println(verdiAv(kr(10.0)));
    }



    @Test
    public void testHoeytBigDecimalBelop() {
        try {
            kr(BigInteger.valueOf(Long.MAX_VALUE));
            fail("Forventet ArithmeticException");
        } catch(ArithmeticException ignore) {}
    }

    @Test
    public void testLavtBigDecimalBelop() {
        try {
            kr(BigInteger.valueOf(Long.MIN_VALUE));
            fail("Forventet ArithmeticException");
        } catch(ArithmeticException ignore) {}
    }

    @Test
    public void testHoeytDoubleBelop() {
        try {
            kr(Double.MAX_VALUE);
            fail("Forventet ArithmeticException");
        } catch(ArithmeticException ignore) {}
    }

    @Test
    public void testLavtDoubleBelop() {
        try {
            System.out.println(verdiAv(kr(-Double.MAX_VALUE)));
            fail("Forventet ArithmeticException");
        } catch(ArithmeticException ignore) {}
    }


    @Test
    public void testPositivOverflowVedAddisjon() {
        assertArithmeticException(kr(BigInteger.valueOf(Belop.MAX_HELE_KRONER)).pluss(kr(1)));
    }

    @Test
    public void testNegativOverflowVedSubtraksjon() {
        assertArithmeticException(kr(BigInteger.valueOf(Belop.MIN_HELE_KRONER)).minus(kr(1)));
    }

    @Test
    public void testPositivOverflowVedMultplikasjon() {
        assertArithmeticException(kr(BigInteger.valueOf(Belop.MAX_HELE_KRONER)).multiplisertMed(heltall(2)));
        assertArithmeticException(kr(BigInteger.valueOf(Belop.MAX_HELE_KRONER)).multiplisertMed(prosent(100.0000001)));
    }

    @Test
    public void testNegativOverflowVedMultplikasjon() {
        assertArithmeticException(kr(BigInteger.valueOf(Belop.MIN_HELE_KRONER)).multiplisertMed(heltall(2)));
        assertArithmeticException(kr(BigInteger.valueOf(Belop.MIN_HELE_KRONER)).multiplisertMed(prosent(100.0000001)));
    }


    private static void assertArithmeticException(BelopUttrykk uttrykk) {
        try {
            verdiAv(uttrykk);
            fail("Forventet ArithmeticException");
        } catch(UttrykkException ignored) {
            assertEquals("Forventet ArithmeticException", ArithmeticException.class, ignored.getCause().getClass());
        }
    }
}
