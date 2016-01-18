package ske.fastsetting.skatt.uttrykk.belop;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.TestUttrykkContext.beregne;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;

import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;

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
    public void skalGiDiffAvToTallMedNavn() {
        BelopUttrykk verdi1 = kr(2).pluss(kr(3)).navn("5");
        BelopUttrykk verdi2 = kr(7);

        BelopUttrykk verdi = verdi1.minus(verdi2);

        assertEquals(Belop.kr(-2), beregne(verdi).verdi());
    }

    @Test
    public void skalTrekkeFraMangeTall() {
        BelopUttrykk verdi = kr(70).minus(kr(7)).minus(kr(9)).minus(kr(11));

        assertEquals(Belop.kr(43), beregne(verdi).verdi());
    }


}
