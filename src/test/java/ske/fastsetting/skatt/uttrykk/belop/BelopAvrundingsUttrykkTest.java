package ske.fastsetting.skatt.uttrykk.belop;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.UttrykkContextImpl.beregne;
import static ske.fastsetting.skatt.uttrykk.belop.BelopAvrundingsUttrykk.rundAvTilHeleKroner;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;

public class BelopAvrundingsUttrykkTest {
    @Test
    public void skalRundeAvTilNaermesteHeleKrone() {
        assertEquals(new Belop(51), beregne(rundAvTilHeleKroner(kr(50.5))).verdi());
        assertEquals(new Belop(50), beregne(rundAvTilHeleKroner(kr(50.4999999))).verdi());
    }



}
