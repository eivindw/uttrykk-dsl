package ske.fastsetting.skatt.uttrykk.tall;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

/**
 * Created by x00jen on 09.02.15.
 */
public class TallAvrundingUttrykkTest {
    @Test
    public void testRundOppProsent() {

        assertEquals(Tall.prosent(35), verdiAv(prosent(34.1).rundOpp()));
        assertEquals(Tall.prosent(35), verdiAv(prosent(34.9).rundOpp()));
        assertEquals(Tall.prosent(34), verdiAv(prosent(34.0).rundOpp()));
        assertEquals(Tall.prosent(34), verdiAv(prosent(34).rundOpp()));
    }

    private Tall verdiAv(TallUttrykk avrundet) {
        return UttrykkContextImpl.beregne(avrundet).verdi();
    }


}
