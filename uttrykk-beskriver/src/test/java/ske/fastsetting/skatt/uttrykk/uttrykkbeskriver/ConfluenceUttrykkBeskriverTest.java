package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.confluence.ConfluenceUttrykkBeskriver;

public class ConfluenceUttrykkBeskriverTest {

    @Test
    public void testBeskriv() throws Exception {
        final ConfluenceUttrykkBeskriver beskriver = new ConfluenceUttrykkBeskriver("Hovedside");
        final UttrykkResultat<Belop> resultat = BasisTest.lagEnkeltUttrykkResultat();
        final Map<String, ConfluenceUttrykkBeskriver.ConfluenceSide> sider = beskriver.beskriv(resultat);

        assertNotNull("Sider er null", sider);

        sider.forEach((tittel, side) -> {
            System.out.println("### " + tittel + " ###");
            System.out.println(side);
        });
    }
}