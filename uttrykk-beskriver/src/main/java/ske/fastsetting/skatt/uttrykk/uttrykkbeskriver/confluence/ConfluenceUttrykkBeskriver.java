package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.confluence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.util.IdUtil;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.UttrykkBeskriver;


@SuppressWarnings("unchecked")
public class ConfluenceUttrykkBeskriver implements UttrykkBeskriver<Map<String, ConfluenceUttrykkBeskriver
  .ConfluenceSide>> {

    private final Map<String, ConfluenceSide> innholdsfortegnelse = new HashMap<>();
    private final Map<String,String> idWikiMap = new HashMap<>();

    public ConfluenceUttrykkBeskriver(String tittel) {
        ConfluenceSide hovedside = new InnholdConfluenceSide(tittel);

        innholdsfortegnelse.put(tittel, hovedside);
    }

    @Override
    public Map<String, ConfluenceUttrykkBeskriver.ConfluenceSide> beskriv(UttrykkResultat<?> resultat) {
        leggTilUttrykk(resultat.start(), resultat);

        return innholdsfortegnelse;
    }

    private String leggTilUttrykk(String id, UttrykkResultat<?> resultat) {

        if(idWikiMap.containsKey(id)) {
            return idWikiMap.get(id);
        }

        final Map uttrykk = resultat.uttrykk(id);

        final String navn = (String) uttrykk.get(UttrykkResultat.KEY_NAVN);
        final List<Regel> regler =
          (List<Regel>) uttrykk.getOrDefault(UttrykkResultat.KEY_REGLER, Collections.emptyList());
        final Set<String> tags = (Set<String>) uttrykk.getOrDefault(UttrykkResultat.KEY_TAGS, Collections.emptySet());

        String tmpUttrykkString = (String) uttrykk.getOrDefault(UttrykkResultat.KEY_UTTRYKK, "");

        String vasketNavn = navn!=null ? navn.replace('/', '-') : null;

        final Set<String> subIder = IdUtil.parseIder(tmpUttrykkString);

        for (String subId : subIder) {
            tmpUttrykkString = tmpUttrykkString.replaceAll("<" + subId + ">", leggTilUttrykk(subId, resultat));
        }

        final String uttrykkString = tmpUttrykkString;

        if (vasketNavn != null) {
            innholdsfortegnelse.computeIfAbsent(vasketNavn, tittel -> new InnholdConfluenceSide(
                tittel,uttrykkString,tags,regler)
            );
        }

        String wikitekst;

        if (vasketNavn != null) {
            wikitekst = "[" + avkapitaliser(vasketNavn.toLowerCase()) + "]";
        } else if (subIder.size()>1) {
            wikitekst = "(" + uttrykkString + ")";
        } else {
            wikitekst = uttrykkString;
        }

        idWikiMap.put(id, wikitekst);

        return wikitekst;
    }

    private String avkapitaliser(String tekst) {
        if(tekst==null || tekst.length()<1) {
            return tekst;
        } else {
            return tekst.substring(0, 1).toLowerCase() + tekst.substring(1);
        }
    }

    public interface ConfluenceSide {
        public String getInnhold();

        public String getTittel();

        public Set<String> getTags();
    }

    public static class InnholdConfluenceSide implements ConfluenceSide {
        private final String tittel;
        private final StringBuilder innhold = new StringBuilder();
        private final Set<String> tags = new HashSet<>();
        private final List<Regel> regler = new ArrayList<>();

        private InnholdConfluenceSide(String tittel) {
            this.tittel = tittel.substring(0, 1).toUpperCase() + tittel.substring(1);
        }

        private InnholdConfluenceSide(String tittel, String innhold, Set<String> tags, List<Regel> regler) {
            this(tittel);
            this.innhold.append(innhold);
            this.tags.addAll(tags);
            this.regler.addAll(regler);
        }

        public String getInnhold() {
            return tags.contains("sats") ? satsInnhold() : innholdMedOverskrift();
        }

        public String getTittel() {
            return tittel;
        }

        public Set<String> getTags() {
            return tags;
        }

        private String innholdMedOverskrift() {
            return "h3. " + tittel
              + "\r\n"
              + innhold.toString()
              + lagReferanser();
        }

        private String satsInnhold() {
            return "{details:label=sats}\r\n" +
              "sats: " + innhold.toString() +
              "{details}" + lagReferanser();
        }

        private String lagReferanser() {
            StringBuilder sb = new StringBuilder();

            if (!regler.isEmpty()) {
                sb.append("\r\nh4. Hjemler \r\n");

                regler.stream().forEach(r -> sb
                    .append("* [")
                    .append(r.kortnavnOgParagraf())
                    .append("|SRK:")
                    .append(r.kortnavnOgParagrafUtenTegn())
                    .append("] ([lovdata|")
                    .append(r.uri())
                    .append("])\r\n")
                );
            }

            sb.append("\r\n\r\nh4. Referanser \r\n");
            sb.append("{incoming-links:mode=list}Ingen referanser{incoming-links} \r\n");

            return sb.toString();
        }

        @Override
        public String toString() {
            return getInnhold();
        }
    }
}
