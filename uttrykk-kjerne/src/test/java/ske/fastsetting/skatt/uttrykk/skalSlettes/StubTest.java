package ske.fastsetting.skatt.uttrykk.skalSlettes;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.test.StubUttrykkContext;

import static ske.fastsetting.skatt.uttrykk.belop.BelopSumUttrykk.sum;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

/**
 * Created by jorn ola birkeland on 11.05.15.
 */
public class StubTest {

    private final static BelopUttrykk loenn = kr(25);
    private final static BelopUttrykk renteinntekt = kr(12);

    private final static BelopUttrykk alminneligInntekt = sum(loenn,renteinntekt);

    private final static BelopUttrykk fellesskatt = alminneligInntekt.multiplisertMed(prosent(13.5));

    @Test
    public void test() {
        // Benytt orignalt uttrykk for alminnelig inntekt
        StubUttrykkContext kontekst = StubUttrykkContext.ny();
        System.out.println(kontekst.verdiAv(fellesskatt));

        // Benytt stub'et verdi for alminnelig inntekt
        StubUttrykkContext stubKontekst = StubUttrykkContext.ny();
        stubKontekst.stub(alminneligInntekt, Belop.kr(200));
        System.out.println(stubKontekst.verdiAv(fellesskatt));
    }
}
