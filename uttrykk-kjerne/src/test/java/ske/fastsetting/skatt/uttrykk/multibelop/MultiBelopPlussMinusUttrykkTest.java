package ske.fastsetting.skatt.uttrykk.multibelop;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.TestUttrykkContext;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.multibelop.MultiKroneUttrykk.kr;

public class MultiBelopPlussMinusUttrykkTest {
    @Test
    public void test() {
        MultiBelopUttrykk<String> verdi =
                kr(5,"Oslo")
                .pluss(kr(7, "Oslo"))
                .pluss(kr(9, "Mysen"))
                .minus(kr(4, "Alta"))
                .minus(kr(1, "Oslo"))
                .pluss(kr(3, "Alta"));

        final MultiBelop<String> faktisk = TestUttrykkContext.beregne(verdi).verdi();

        assertEquals(Belop.kr(11),faktisk.get("Oslo"));
        assertEquals(Belop.kr(9),faktisk.get("Mysen"));
        assertEquals(Belop.kr(-1),faktisk.get("Alta"));
    }
}
