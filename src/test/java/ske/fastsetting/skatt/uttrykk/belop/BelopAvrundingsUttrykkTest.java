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
        assertEquals(Belop.av(51), beregne(rundAvTilHeleKroner(kr(50.5))).verdi());
        assertEquals(Belop.av(50), beregne(rundAvTilHeleKroner(kr(50.4999999))).verdi());
    }

    @Test
    public void skalRundeAvTilNaermesteKrone() {
        assertEquals(Belop.av(68541).rundAvTilNaermeste(1), Belop.av(68541));
    }

    @Test
    public void skalRundeAvTilNaermesteHele100kr() {
        assertEquals(Belop.av(100).rundAvTilNaermeste(100), Belop.av(100));
        assertEquals(Belop.av(120).rundAvTilNaermeste(100), Belop.av(100));
        assertEquals(Belop.av(133).rundAvTilNaermeste(100), Belop.av(100));

        assertEquals(Belop.av(150).rundAvTilNaermeste(100), Belop.av(200));

        assertEquals(Belop.av(160).rundAvTilNaermeste(100), Belop.av(200));
        assertEquals(Belop.av(177).rundAvTilNaermeste(100), Belop.av(200));
        assertEquals(Belop.av(200).rundAvTilNaermeste(100), Belop.av(200));
    }

    @Test
    public void skalRundeAvTilNaermesteHele1000kr() {
        assertEquals(Belop.av(1000).rundAvTilNaermeste(1000), Belop.av(1000));
        assertEquals(Belop.av(1200).rundAvTilNaermeste(1000), Belop.av(1000));
        assertEquals(Belop.av(1330).rundAvTilNaermeste(1000), Belop.av(1000));

        assertEquals(Belop.av(1500).rundAvTilNaermeste(1000), Belop.av(2000));

        assertEquals(Belop.av(1600).rundAvTilNaermeste(1000), Belop.av(2000));
        assertEquals(Belop.av(1770).rundAvTilNaermeste(1000), Belop.av(2000));
        assertEquals(Belop.av(2000).rundAvTilNaermeste(1000), Belop.av(2000));
    }


}
