package ske.fastsetting.skatt.uttrykk.belop;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.UttrykkException;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.TestUttrykkContext.beregne;
import static ske.fastsetting.skatt.uttrykk.belop.BelopBrukHvisUttrykk.bruk;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.bolsk.BolskKonstantUttrykk.SANN;
import static ske.fastsetting.skatt.uttrykk.bolsk.BolskKonstantUttrykk.USANN;

/**
 * Created by jorn ola birkeland on 23.10.15.
 */
public class BelopBrukHvisUttrykkTest {
    @Test
    public void skalHaandtereBareBruk() {
        BelopUttrykk a = bruk(kr(5)).navn("A");

        assertEquals(Belop.kr(5), beregne(a).verdi());
    }

    @Test(expected = UttrykkException.class)
    public void skalGiExceptionMedHvisUtenEllersBruk() {
        BelopUttrykk a = bruk(kr(5)).hvis(kr(3).er(kr(5)));

        beregne(a).verdi();
    }

    @Test(expected = IllegalStateException.class)
    public void skalGiExceptionMedDobbelBruk() {
        BelopUttrykk a = bruk(kr(5)).ellersBruk(kr(7));
    }

    @Test
    public void skalGiBrukHvisSann() {
        BelopUttrykk a =
                bruk(kr(5)).hvis(SANN)
                        .ellersBruk(kr(7));

        assertEquals(Belop.kr(5), beregne(a).verdi());
    }

    @Test
    public void skalGiEllersBrukHvisUsann() {
        BelopUttrykk a =
                bruk(kr(5)).hvis(USANN)
                        .ellersBruk(kr(7));

        assertEquals(Belop.kr(7), beregne(a).verdi());
    }

    @Test
    public void skalGiEllersBrukHvisMangeUsanne() {
        BelopUttrykk a =
                bruk(kr(1)).hvis(USANN)
                        .ellersBruk(kr(2)).hvis(USANN)
                .ellersBruk(kr(3)).hvis(USANN)
                .ellersBruk(kr(4)).hvis(USANN)
                .ellersBruk(kr(5));

        assertEquals(Belop.kr(5), beregne(a).verdi());
    }

    @Test
    public void skalGiEllersBrukHvisSannMidtI() {
        BelopUttrykk a =
                bruk(kr(1)).hvis(USANN)
                        .ellersBruk(kr(2)).hvis(USANN)
                        .ellersBruk(kr(3)).hvis(SANN)
                        .ellersBruk(kr(4)).hvis(USANN)
                        .ellersBruk(kr(5));

        assertEquals(Belop.kr(3), beregne(a).verdi());
    }

}
