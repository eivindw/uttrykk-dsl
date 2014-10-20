package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.util.IdUtil;

import java.util.*;

@SuppressWarnings("unchecked")
public class ConfluenceUttrykkBeskriver implements UttrykkBeskriver<Map<String, ConfluenceUttrykkBeskriver.ConfluenceSide>> {

    private final Map<String, ConfluenceSide> innholdsfortegnelse = new HashMap<>();

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
        final Map uttrykk = resultat.uttrykk(id);

        final String navn = (String) uttrykk.get(UttrykkResultat.KEY_NAVN);
        final List<Regel> regler =
            (List<Regel>) uttrykk.getOrDefault(UttrykkResultat.KEY_REGLER, Collections.emptyList());
        final Set<String> tags = (Set<String>) uttrykk.getOrDefault(UttrykkResultat.KEY_TAGS, Collections.emptySet());

        String tmpUttrykkString = (String) uttrykk.getOrDefault(UttrykkResultat.KEY_UTTRYKK, "");

        final Set<String> subIder = IdUtil.parseIder(tmpUttrykkString);

        for (String subId : subIder) {
            tmpUttrykkString = tmpUttrykkString.replaceAll("<" + subId + ">", leggTilUttrykk(subId, resultat));
        }

        final String uttrykkString = tmpUttrykkString;

        if (navn != null) {
            innholdsfortegnelse.computeIfAbsent(navn, tittel -> {
                final InnholdConfluenceSide side = new InnholdConfluenceSide(
                    tittel,
                    uttrykkString,
                    tags,
                    regler
                );

                leggReglerPaaSide(regler, side);

                return side;
            });

            return "[" + navn + "]";
        } else if (!subIder.isEmpty()) {
            return "(" + uttrykkString + ")";
        } else {
            return uttrykkString;
        }
    }

    private void leggReglerPaaSide(List<Regel> regler, InnholdConfluenceSide side) {
        for (Regel regel : regler) {
            String overskrift = regel.kortnavnOgParagraf();
            if (!innholdsfortegnelse.containsKey(overskrift)) {
                ConfluenceSide nySide = new HjemmelConfluenceSide(regel);
                side.undersider.add(nySide);
                innholdsfortegnelse.put(overskrift, nySide);
            }
        }
    }

    public interface ConfluenceSide {
        public String getInnhold();

        public List<ConfluenceSide> getUndersider();

        public String getTittel();
    }

    public static class HjemmelConfluenceSide implements ConfluenceSide {

        private final Regel regel;

        public HjemmelConfluenceSide(Regel regel) {
            this.regel = regel;
        }

        @Override
        public String getInnhold() {
            return "{details:label=hjemmel}\r\n" +
                "lov: " + regel.tittel() + "\r\n" +
                "paragraf: $" + regel.paragraf() + "\r\n" +
                "{details}\r\n" +
                "\r\n[Lovdata|" + regel.uri() + "]\r\n\r\n" +
                lagReferanser();
        }

        @Override
        public List<ConfluenceSide> getUndersider() {
            return new ArrayList<>();
        }

        @Override
        public String getTittel() {
            return regel.kortnavnOgParagraf();
        }

        private String lagReferanser() {
            return "\r\nh4. Referanser \r\n" +
                "{incoming-links:mode=list}Ingen referanser{incoming-links} \r\n";
        }

        @Override
        public String toString() {
            return getInnhold();
        }
    }

    public static class InnholdConfluenceSide implements ConfluenceSide {
        private final String tittel;
        private final StringBuilder innhold = new StringBuilder();
        private final List<ConfluenceSide> undersider = new ArrayList<>();
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

        public List<ConfluenceSide> getUndersider() {
            return undersider;
        }

        public String getTittel() {
            return tittel;
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
                regler.stream().forEach(r -> sb.append("* [").append(r.kortnavnOgParagraf()).append("]\r\n"));
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
