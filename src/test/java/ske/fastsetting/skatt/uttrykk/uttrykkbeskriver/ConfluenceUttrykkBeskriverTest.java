package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import org.junit.Test;
import ske.fastsetting.skatt.uttrykk.BasisTest;

import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class ConfluenceUttrykkBeskriverTest {

    @Test
    public void testBeskriv() throws Exception {
        final ConfluenceUttrykkBeskriver beskriver = new ConfluenceUttrykkBeskriver("Hovedside");
        final Map<String, ConfluenceUttrykkBeskriver.ConfluenceSide> sider =
            beskriver.beskriv(BasisTest.lagEnkeltUttrykkResultat());

        assertNotNull("Sider er null", sider);

        sider.forEach((tittel, side) -> {
            System.out.println("### " + tittel + " ###");
            System.out.println(side);
        });
    }
}