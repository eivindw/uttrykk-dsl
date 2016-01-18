package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.neo4j;

import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.util.IdUtil;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.UttrykkBeskriver;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.neo4j.rest.NeoRestUtil;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.neo4j.rest.ObjectBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.neo4j.rest.NeoRestUtil.*;

public class Neo4JUttrykkBeskriver implements UttrykkBeskriver<URI> {

    private final Map<String, URI> idNodeMap = new HashMap<>();
    private final Map<String, URI> regelNodeMap = new HashMap<>();


    public static void main(String[] args) throws URISyntaxException {
        NeoRestUtil.checkDatabaseIsRunning();

        // START SNIPPET: nodesAndProps
        URI firstNode = createNode();
        addProperty(firstNode, "name", "Joe Strummer");
        URI secondNode = createNode();
        addProperty(secondNode, "band", "The Clash");

        addLabels(firstNode, "label1", "label2");

//        URI relationshipUri = addRelationship(firstNode, secondNode, "singer",
//                ObjectBuilder.ny().entry("from", "1986").entry("until", "1992").build());

        URI relationshipUri = addRelationship(firstNode, secondNode, "singer", null);

        addProperty(relationshipUri, "stars", "5");

        NeoRestUtil.find(firstNode, "singer");

        NeoRestUtil.sendTransactionalCypherQuery("MATCH (n) WHERE has(n.name) RETURN n.name AS name");
    }

    @Override
    public URI beskriv(UttrykkResultat<?> resultat) {
        return leggTilUttrykk(resultat.start(), resultat);
    }

    private URI leggTilUttrykk(String id, UttrykkResultat<?> resultat) {

        if (idNodeMap.containsKey(id)) {
            return idNodeMap.get(id);
        }

        final Map uttrykk = resultat.uttrykk(id);

        final String navn = (String) uttrykk.get(UttrykkResultat.KEY_NAVN);
        final List<Regel> regler =
                (List<Regel>) uttrykk.getOrDefault(UttrykkResultat.KEY_REGLER, Collections.emptyList());
        final Set<String> tags = (Set<String>) uttrykk.getOrDefault(UttrykkResultat.KEY_TAGS, Collections.emptySet());

        String uttrykkString = (String) uttrykk.getOrDefault(UttrykkResultat.KEY_UTTRYKK, "");

        URI nodeUri = createNode(ObjectBuilder.ny().entry("navn", navn == null ? uttrykkString : navn).build());
        addLabels(nodeUri, tags.toArray(new String[tags.size()]));
        addHjemler(nodeUri, regler);

        final Set<String> subIder = IdUtil.parseIder(uttrykkString);

        for (String subId : subIder) {
            URI childUri = leggTilUttrykk(subId, resultat);
            addRelationship(nodeUri, childUri, "avhenger_av", ObjectBuilder.ny().build());
        }

        idNodeMap.put(id, nodeUri);
        return nodeUri;
    }


    private void addHjemler(URI nodeUri, List<Regel> regler) {
        for (Regel regel : regler) {
            String navn = regel.kortnavnOgParagraf();

            Object properties = ObjectBuilder.ny().entry("navn", navn).build();

            URI regelNode = regelNodeMap.computeIfAbsent(navn, n -> createNode(properties));

            addRelationship(nodeUri, regelNode, "har_hjemmel", ObjectBuilder.ny().build());
            addLabels(regelNode, "hjemmel");
        }

    }


}
