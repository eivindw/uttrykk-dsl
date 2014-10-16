package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import org.junit.Test;
import ske.fastsetting.skatt.uttrykk.BasisTest;

import static org.junit.Assert.assertNotNull;

public class ConfluenceUttrykkBeskriverTest {

    @Test
    public void testBeskriv() throws Exception {
        final ConfluenceUttrykkBeskriver.ConfluenceSide confluenceSide =
            new ConfluenceUttrykkBeskriver("Hovedside").beskriv(BasisTest.lagEnkeltUttrykkResultat());

        System.out.println(confluenceSide);

        assertNotNull("Side er null", confluenceSide);
    }
}