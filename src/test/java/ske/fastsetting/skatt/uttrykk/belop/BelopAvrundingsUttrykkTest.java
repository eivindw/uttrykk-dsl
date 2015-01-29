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
        assertEquals(Belop.fra(51), beregne(rundAvTilHeleKroner(kr(50.5))).verdi());
        assertEquals(Belop.fra(50), beregne(rundAvTilHeleKroner(kr(50.4999999))).verdi());
    }

    @Test
    public void skalRundeAvTilNaermesteKrone() {
        assertEquals(Belop.fra(0).rundAvTilNaermeste(1), Belop.fra(0));
        assertEquals(Belop.fra(68541).rundAvTilNaermeste(1), Belop.fra(68541));
    }

    @Test
    public void skalRundeAvTilNaermesteHele100kr() {
        assertEquals(Belop.fra(100).rundAvTilNaermeste(100), Belop.fra(100));
        assertEquals(Belop.fra(120).rundAvTilNaermeste(100), Belop.fra(100));
        assertEquals(Belop.fra(133).rundAvTilNaermeste(100), Belop.fra(100));

        assertEquals(Belop.fra(150).rundAvTilNaermeste(100), Belop.fra(200));

        assertEquals(Belop.fra(160).rundAvTilNaermeste(100), Belop.fra(200));
        assertEquals(Belop.fra(177).rundAvTilNaermeste(100), Belop.fra(200));
        assertEquals(Belop.fra(200).rundAvTilNaermeste(100), Belop.fra(200));
    }

    @Test
    public void skalRundeAvTilNaermesteHele1000kr() {
        assertEquals(Belop.fra(1000).rundAvTilNaermeste(1000), Belop.fra(1000));
        assertEquals(Belop.fra(1200).rundAvTilNaermeste(1000), Belop.fra(1000));
        assertEquals(Belop.fra(1330).rundAvTilNaermeste(1000), Belop.fra(1000));

        assertEquals(Belop.fra(1500).rundAvTilNaermeste(1000), Belop.fra(2000));

        assertEquals(Belop.fra(1600).rundAvTilNaermeste(1000), Belop.fra(2000));
        assertEquals(Belop.fra(1770).rundAvTilNaermeste(1000), Belop.fra(2000));
        assertEquals(Belop.fra(2000).rundAvTilNaermeste(1000), Belop.fra(2000));
    }


}
