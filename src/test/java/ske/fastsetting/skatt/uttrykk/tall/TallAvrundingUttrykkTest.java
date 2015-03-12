package ske.fastsetting.skatt.uttrykk.tall;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Avrunding;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

public class TallAvrundingUttrykkTest {

    @Test
    public void testRundOpp() {

        assertEquals(Tall.prosent(35), verdiAv(prosent(34.1).rundAv(2, Avrunding.Opp)));
        assertEquals(Tall.prosent(35), verdiAv(prosent(34.9).rundAv(2, Avrunding.Opp)));
        assertEquals(Tall.prosent(34), verdiAv(prosent(34.0).rundAv(2, Avrunding.Opp)));
        assertEquals(Tall.prosent(34), verdiAv(prosent(34).rundAv(2, Avrunding.Opp)));
    }

    @Test
    public void testRundNed() {

        assertEquals(Tall.prosent(34), verdiAv(prosent(34.1).rundAv(2, Avrunding.Ned)));
        assertEquals(Tall.prosent(34), verdiAv(prosent(34.9).rundAv(2,Avrunding.Ned)));
        assertEquals(Tall.prosent(34), verdiAv(prosent(34.0).rundAv(2,Avrunding.Ned)));
        assertEquals(Tall.prosent(34), verdiAv(prosent(34).rundAv(2,Avrunding.Ned)));
    }

    @Test
    public void testRundNaermest() {

        assertEquals(Tall.prosent(34), verdiAv(prosent(34.4999).rundAv(2, Avrunding.Naermeste)));
        assertEquals(Tall.prosent(35), verdiAv(prosent(34.5).rundAv(2,Avrunding.Naermeste)));
        assertEquals(Tall.prosent(34), verdiAv(prosent(34).rundAv(2,Avrunding.Naermeste)));
    }

    private Tall verdiAv(TallUttrykk avrundet) {
        return UttrykkContextImpl.beregne(avrundet).verdi();
    }


}
