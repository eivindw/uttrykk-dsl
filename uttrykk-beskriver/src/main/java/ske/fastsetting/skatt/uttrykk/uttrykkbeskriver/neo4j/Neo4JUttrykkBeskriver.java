package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.neo4j;

import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.util.IdUtil;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.UttrykkBeskriver;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.neo4j.rest.ObjectBuilder;

import java.net.URI;
import java.util.*;

import static ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.neo4j.rest.Neo4JRest.*;

public class Neo4JUttrykkBeskriver implements UttrykkBeskriver<URI> {

    private final Map<String, URI> idNodeMap = new HashMap<>();
    private final Map<String, URI> regelNodeMap = new HashMap<>();

    @Override
    public URI beskriv(UttrykkResultat<?> resultat) {
        checkDatabaseIsRunning();

        return leggTilUttrykk(resultat.start(), resultat);
    }

    private URI leggTilUttrykk(String id, UttrykkResultat<?> resultat) {

        if (idNodeMap.containsKey(id)) {
            return idNodeMap.get(id);
        }

        final Map uttrykk = resultat.uttrykk(id);

        URI nodeUri = createNode(ObjectBuilder.ny().entry("navn", navn(uttrykk)).build());
        addTags(nodeUri, uttrykk);
        addHjemler(nodeUri, uttrykk);

        for (String subId : getIds(uttrykk)) {
            URI childUri = leggTilUttrykk(subId, resultat);
            addRelationship(nodeUri, childUri, "avhenger_av", ObjectBuilder.ny().build());
        }

        idNodeMap.put(id, nodeUri);
        return nodeUri;
    }

    private Set<String> getIds(Map uttrykk) {
        String uttrykkString = (String) uttrykk.getOrDefault(UttrykkResultat.KEY_UTTRYKK, "");
        return IdUtil.parseIder(uttrykkString);
    }

    private void addTags(URI nodeUri, Map uttrykk) {

        final Set<String> tags = (Set<String>) uttrykk.getOrDefault(UttrykkResultat.KEY_TAGS, Collections.emptySet());

        if(tags.size()>0) {
            addLabels(nodeUri, tags.toArray(new String[tags.size()]));
        } else {
            String navn = (String) uttrykk.get(UttrykkResultat.KEY_NAVN);
            String uttrykkString = (String) uttrykk.getOrDefault(UttrykkResultat.KEY_UTTRYKK, "");

            final Set<String> subIder = IdUtil.parseIder(uttrykkString);

            if(navn==null && subIder.size()>0) {
                addLabels(nodeUri,"mellomregning");
            } else if(navn==null) {
                addLabels(nodeUri,"verdi");
            }
        }
    }

    private void addHjemler(URI nodeUri, Map uttrykk) {
        final List<Regel> regler =
                (List<Regel>) uttrykk.getOrDefault(UttrykkResultat.KEY_REGLER, Collections.emptyList());

        for (Regel regel : regler) {
            String navn = regel.kortnavnOgParagraf();

            Object properties = ObjectBuilder.ny().entry("navn", navn).build();

            URI regelNode = regelNodeMap.computeIfAbsent(navn, n -> createNode(properties));

            addRelationship(nodeUri, regelNode, "har_hjemmel", ObjectBuilder.ny().build());
            addLabels(regelNode, "hjemmel");
        }

    }

    private String navn(Map uttrykk) {
        String navn = (String) uttrykk.get(UttrykkResultat.KEY_NAVN);
        String uttrykkString = (String) uttrykk.getOrDefault(UttrykkResultat.KEY_UTTRYKK, "");

        final Set<String> subIder = IdUtil.parseIder(uttrykkString);

        if(navn==null && subIder.size()==0) {
            navn = uttrykkString;
        } else if (navn==null) {
            navn = "<anonym>";
        }

        return navn;
    }


}
