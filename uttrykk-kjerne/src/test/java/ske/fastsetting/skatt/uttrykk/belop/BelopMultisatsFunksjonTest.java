package ske.fastsetting.skatt.uttrykk.belop;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.belop.BelopMultisatsFunksjon.multisatsFunksjonAv;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.TestUttrykkContext;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConsoleUttrykkBeskriver;

public class BelopMultisatsFunksjonTest {

    @Test
    public void testRettLinje() {
        BelopUttrykk multisats = multisatsFunksjonAv(kr(100)).medSats(prosent(10));

        assertEquals(Belop.kr(10), TestUttrykkContext.beregne(multisats).verdi());
    }

    @Test
    public void testRettLinjeMedOevreGrense() {
        BelopUttrykk multisats = multisatsFunksjonAv(kr(100)).medSats(prosent(10), kr(50));

        assertEquals(Belop.kr(5), TestUttrykkContext.beregne(multisats).verdi());
    }

    @Test
    public void testMedToSatserUtenOevreGrense() {
        BelopUttrykk multisats = multisatsFunksjonAv(kr(100)).medSats(prosent(10), kr(50)).medSats(prosent(20));

        assertEquals(Belop.kr(15), TestUttrykkContext.beregne(multisats).verdi());
    }

    @Test
    public void testMedToSatserMedOevreGrense() {
        BelopUttrykk multisats = multisatsFunksjonAv(kr(500)).medSats(prosent(10), kr(50)).medSats(prosent(20), kr
          (100));

        assertEquals(Belop.kr(15), TestUttrykkContext.beregne(multisats).verdi());
    }

    @Test
    public void testMedTreSatser() throws IOException {
        BelopUttrykk multisats = multisatsFunksjonAv(kr(200)).medSats(prosent(10), kr(50)).medSats(prosent(20), kr
          (100)).medSats(prosent(7));

        assertEquals(Belop.kr(22), TestUttrykkContext.beregne(multisats).verdi());

    }


    @Test
    @Ignore
    public void skriveTest() throws IOException {
        BelopUttrykk multisats = multisatsFunksjonAv(kr(200)).medSats(prosent(10), kr(50)).medSats(prosent(20), kr
          (100)).medSats(prosent(7)).navn("multisats");

        ConsoleUttrykkBeskriver beskriver = new ConsoleUttrykkBeskriver();

        String s = beskriver.beskriv(TestUttrykkContext.beskrive(multisats));

        System.out.println(s);
    }


}
