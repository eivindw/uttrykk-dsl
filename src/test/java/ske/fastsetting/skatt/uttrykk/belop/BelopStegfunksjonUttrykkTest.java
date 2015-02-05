package ske.fastsetting.skatt.uttrykk.belop;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.belop.BelopStegfunksjonUttrykk.stegfunksjonAv;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

public class BelopStegfunksjonUttrykkTest {
    @Test
    public void test() {
        BelopUttrykk stegfunksjon = stegfunksjonAv(kr(100)).medSats(prosent(10)).til(kr(50)).deretterMedSats(prosent(20));

        assertEquals(Belop.kr(15), UttrykkContextImpl.beregne(stegfunksjon).verdi());
    }


}
