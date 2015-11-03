package ske.fastsetting.skatt.uttrykk;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.BrukHvisUttrykkTest.TestBrukHvisUttrykkk.bruk;
import static ske.fastsetting.skatt.uttrykk.TestUttrykkContext.beregne;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.bolsk.BolskKonstantUttrykk.SANN;
import static ske.fastsetting.skatt.uttrykk.bolsk.BolskKonstantUttrykk.USANN;

/**
 * Created by jorn ola birkeland on 23.10.15.
 */
public class BrukHvisUttrykkTest {

    static class TestBrukHvisUttrykkk<T> extends BrukHvisUttrykk<T,TestBrukHvisUttrykkk<T>> {

        public static <T> TestBrukHvisUttrykkk<T> bruk(Uttrykk<T> uttrykk) {
            return new TestBrukHvisUttrykkk<>(uttrykk);
        }

        public TestBrukHvisUttrykkk(Uttrykk<T> uttrykk) {
            super(uttrykk);
        }
    }

    @Test
    public void skalHaandtereBareBruk() {
        Uttrykk<Belop> a = bruk(kr(5)).navn("A");

        assertEquals(Belop.kr(5), beregne(a).verdi());
    }

    @Test(expected = UttrykkException.class)
    public void skalGiExceptionMedHvisUtenEllersBruk() {
        Uttrykk<Belop> a = bruk(kr(5)).hvis(kr(3).er(kr(5)));

        beregne(a).verdi();
    }

    @Test(expected = IllegalStateException.class)
    public void skalGiExceptionMedDobbelBruk() {
        Uttrykk<Belop> a = bruk(kr(5)).ellersBruk(kr(7));
    }

    @Test
    public void skalGiBrukHvisSann() {
        Uttrykk<Belop> a =
                bruk(kr(5)).hvis(SANN)
                        .ellersBruk(kr(7));

        assertEquals(Belop.kr(5), beregne(a).verdi());
    }

    @Test
    public void skalGiEllersBrukHvisUsann() {
        Uttrykk<Belop> a =
                bruk(kr(5)).hvis(USANN)
                        .ellersBruk(kr(7));

        assertEquals(Belop.kr(7), beregne(a).verdi());
    }

    @Test
    public void skalGiEllersBrukHvisMangeUsanne() {
        Uttrykk<Belop> a =
                bruk(kr(1)).hvis(USANN)
                        .ellersBruk(kr(2)).hvis(USANN)
                .ellersBruk(kr(3)).hvis(USANN)
                .ellersBruk(kr(4)).hvis(USANN)
                .ellersBruk(kr(5));

        assertEquals(Belop.kr(5), beregne(a).verdi());
    }

    @Test
    public void skalGiEllersBrukHvisSannMidtI() {
        Uttrykk<Belop> a =
                bruk(kr(1)).hvis(USANN)
                        .ellersBruk(kr(2)).hvis(USANN)
                        .ellersBruk(kr(3)).hvis(SANN)
                        .ellersBruk(kr(4)).hvis(USANN)
                        .ellersBruk(kr(5));

        assertEquals(Belop.kr(3), beregne(a).verdi());
    }

}
