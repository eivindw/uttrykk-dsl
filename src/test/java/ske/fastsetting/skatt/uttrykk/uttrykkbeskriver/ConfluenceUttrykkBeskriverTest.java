package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import org.junit.Test;
import ske.fastsetting.skatt.uttrykk.BasisTest;

import java.util.Map;

import static org.junit.Assert.*;

public class ConfluenceUttrykkBeskriverTest {

    @Test
    public void testBeskriv() throws Exception {
        final Map confluenceSider =
            new ConfluenceUttrykkBeskriver().beskriv(BasisTest.lagEnkeltUttrykkResultat());

        System.out.println(confluenceSider);

        assertNotNull("Sidemap er null", confluenceSider);
    }
}