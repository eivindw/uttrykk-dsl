package ske.fastsetting.skatt.uttrykk.belop;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.TestUttrykkContext.beregne;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;

/**
 * Created by jorn ola birkeland on 31.05.15.
 */
public class BelopPlussMinusUttrykkTest {


    @Test
    public void skalGiSumAvToTall() {
        BelopUttrykk verdi = kr(5).pluss(kr(7));

        assertEquals(Belop.kr(12), beregne(verdi).verdi());
    }

    @Test
    public void skalGiDiffAvToTall() {
        BelopUttrykk verdi = kr(5).minus(kr(7));

        assertEquals(Belop.kr(-2), beregne(verdi).verdi());
    }

    @Test
    public void skalTrekkeFraMangeTall() {
        BelopUttrykk verdi = kr(70).minus(kr(7)).minus(kr(9)).minus(kr(11));

        assertEquals(Belop.kr(43), beregne(verdi).verdi());
    }

}
