package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.TestUttrykkContext;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetKroneUttrykk.kr;

/**
 * Created by jorn ola birkeland on 01.06.15.
 */
public class StedbundetBelopPlussMinusUttrykkTest {
    @Test
    public void test() {
        StedbundetBelopUttrykk<String> verdi =
                kr(5,"Oslo")
                .pluss(kr(7, "Oslo"))
                .pluss(kr(9, "Mysen"))
                .minus(kr(4, "Alta"))
                .minus(kr(1, "Oslo"))
                .pluss(kr(3, "Alta"));

        final StedbundetBelop<String> faktisk = TestUttrykkContext.beregne(verdi).verdi();

        assertEquals(Belop.kr(11),faktisk.get("Oslo"));
        assertEquals(Belop.kr(9),faktisk.get("Mysen"));
        assertEquals(Belop.kr(-1),faktisk.get("Alta"));
    }
}
