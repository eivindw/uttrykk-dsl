package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import jdk.nashorn.internal.ir.annotations.Ignore;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.neo4j.Neo4JUttrykkBeskriver;

/**
 * Created by jorn ola birkeland on 08.06.15.
 */
public class Neo4JUttrykkBeskriverTest {
    @Ignore
    public void testBeskriv() throws Exception {
        final Neo4JUttrykkBeskriver beskriver = new Neo4JUttrykkBeskriver();
        final UttrykkResultat<Belop> resultat = BasisTest.lagEnkeltUttrykkResultat();
        beskriver.beskriv(resultat);
    }

}
