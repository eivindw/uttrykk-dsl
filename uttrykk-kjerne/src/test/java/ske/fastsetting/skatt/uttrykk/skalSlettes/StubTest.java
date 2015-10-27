package ske.fastsetting.skatt.uttrykk.skalSlettes;

import static ske.fastsetting.skatt.uttrykk.belop.BelopSumUttrykk.sum;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

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
        stubKontekst.overstyrVerdi(alminneligInntekt, Belop.kr(200));
        System.out.println(stubKontekst.verdiAv(fellesskatt));
    }

    static class StubUttrykkContext extends UttrykkContextImpl {
        protected StubUttrykkContext(Object[] input) {
            super(input);
        }

        public static StubUttrykkContext ny(Object... input) {
            return new StubUttrykkContext(input);
        }

        public <T> T verdiAv(Uttrykk<T> uttrykk) {
            return kalkuler(uttrykk,true,false).verdi();
        }
    }
}
