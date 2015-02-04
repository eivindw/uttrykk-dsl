package ske.fastsetting.skatt.uttrykk.belop;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.UttrykkContextImpl.beregne;
import static ske.fastsetting.skatt.uttrykk.belop.BelopAvrundingsUttrykk.rundAvTilHeleKroner;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;

public class BelopAvrundingsUttrykkTest {
    @Test
    public void skalRundeAvOereTilNaermesteHeleKrone() {
        assertEquals(Belop.kr(51), beregne(rundAvTilHeleKroner(kr(50.5))).verdi());
        assertEquals(Belop.kr(50), beregne(rundAvTilHeleKroner(kr(50.4999999))).verdi());
    }

    @Test
    public void skalRundeAvTilNaermesteKrone() {
        assertEquals(Belop.kr(0).rundAvTilNaermeste(1), Belop.kr(0));
        assertEquals(Belop.kr(68541).rundAvTilNaermeste(1), Belop.kr(68541));
    }

    @Test
    public void skalRundeAvTilNaermesteHele100kr() {
        assertEquals(Belop.kr(100).rundAvTilNaermeste(100), Belop.kr(100));
        assertEquals(Belop.kr(120).rundAvTilNaermeste(100), Belop.kr(100));
        assertEquals(Belop.kr(133).rundAvTilNaermeste(100), Belop.kr(100));

        assertEquals(Belop.kr(150).rundAvTilNaermeste(100), Belop.kr(200));

        assertEquals(Belop.kr(160).rundAvTilNaermeste(100), Belop.kr(200));
        assertEquals(Belop.kr(177).rundAvTilNaermeste(100), Belop.kr(200));
        assertEquals(Belop.kr(200).rundAvTilNaermeste(100), Belop.kr(200));
    }

    @Test
    public void skalRundeAvTilNaermesteHele1000kr() {
        assertEquals(Belop.kr(1000).rundAvTilNaermeste(1000), Belop.kr(1000));
        assertEquals(Belop.kr(1200).rundAvTilNaermeste(1000), Belop.kr(1000));
        assertEquals(Belop.kr(1330).rundAvTilNaermeste(1000), Belop.kr(1000));

        assertEquals(Belop.kr(1500).rundAvTilNaermeste(1000), Belop.kr(2000));

        assertEquals(Belop.kr(1600).rundAvTilNaermeste(1000), Belop.kr(2000));
        assertEquals(Belop.kr(1770).rundAvTilNaermeste(1000), Belop.kr(2000));
        assertEquals(Belop.kr(2000).rundAvTilNaermeste(1000), Belop.kr(2000));
    }


}
