package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.uttrykk.UttrykkBeskriver;

import java.util.*;

public class ConfluenceUttrykkBeskriver implements UttrykkBeskriver<Map> {

    private final InnholdConfluenceSide gjeldendeSide;
    private final Map<String, ConfluenceSide> innholdsfortegnelse;
    private final int innrykk;

    public ConfluenceUttrykkBeskriver(String tittel) {
        gjeldendeSide = new InnholdConfluenceSide(tittel);
        innholdsfortegnelse = new HashMap<>();
        innholdsfortegnelse.put(tittel, gjeldendeSide);
        innrykk = 0;
    }

    private ConfluenceUttrykkBeskriver(InnholdConfluenceSide gjeldendeSide, Map<String, ConfluenceSide> innholdsfortegnelse) {
        this(gjeldendeSide, innholdsfortegnelse, 0);
    }

    private ConfluenceUttrykkBeskriver(InnholdConfluenceSide gjeldendeSide, Map<String, ConfluenceSide> innholdsfortegnelse, int innrykk) {
        this.innholdsfortegnelse = innholdsfortegnelse;
        this.gjeldendeSide = gjeldendeSide;
        this.innrykk = innrykk;
    }

    public ConfluenceSide getConfluenceSide() {
        return gjeldendeSide;
    }

    public UttrykkBeskriver overskrift(String overskrift) {
        skrivLink(overskrift);

        if (innholdsfortegnelse.containsKey(overskrift)) {
            return null;
        } else {
            InnholdConfluenceSide nySide = new InnholdConfluenceSide(overskrift);
            gjeldendeSide.undersider.add(nySide);
            innholdsfortegnelse.put(overskrift, nySide);

            return new ConfluenceUttrykkBeskriver(nySide, innholdsfortegnelse);
        }
    }

    public void skriv(String linje) {
        gjeldendeSide.innhold.append(new String(new char[innrykk]).replace("\0", "&nbsp; ") + linje).append("\r\n");
    }

    public UttrykkBeskriver rykkInn() {
        return new ConfluenceUttrykkBeskriver(gjeldendeSide, innholdsfortegnelse, innrykk + 3);
    }

    public void tags(String... strings) {
        gjeldendeSide.tags.addAll(Arrays.asList(strings));
    }

    public void regler(Regel... regler) {

        for(Regel regel : regler) {
            String overskrift = regel.getKortnavnOgParagraf();
            if(!innholdsfortegnelse.containsKey(overskrift)) {
                ConfluenceSide nySide = new HjemmelConfluenceSide(regel);
                gjeldendeSide.undersider.add(nySide);
                innholdsfortegnelse.put(overskrift, nySide);
            }

        }

        gjeldendeSide.regler.addAll(Arrays.asList(regler));

    }

    private void skrivLink(String link) {
        gjeldendeSide.innhold.append(new String(new char[innrykk]).replace("\0", "&nbsp; ") + "[" + link + "]").append("\r\n");
    }

    @Override
    public Map beskriv(UttrykkResultat<?> resultat) {
        return null;
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
            StringBuilder sb = new StringBuilder();
            sb.append("{details:label=hjemmel}\r\n");
            sb.append("lov: "+ regel.getTittel() + "\r\n");
            sb.append("paragraf: $"+regel.getParagraf() +"\r\n");
            sb.append("{details}\r\n");

            sb.append("\r\n[Lovdata|"+regel.getLovdataUri() +"]\r\n\r\n");

            sb.append(lagReferanser());

            return sb.toString();
        }


        @Override
        public List<ConfluenceSide> getUndersider() {
            return new ArrayList<>();
        }

        @Override
        public String getTittel() {
            return regel.getKortnavnOgParagraf();
        }

        private String lagReferanser() {
            StringBuilder sb = new StringBuilder();
            sb.append("\r\nh4. Referanser \r\n");
            sb.append("{incoming-links:mode=list}Ingen referanser{incoming-links} \r\n");

            return sb.toString();

        }


    }

    public static class InnholdConfluenceSide implements ConfluenceSide {
        private final String tittel;
        private final StringBuilder innhold = new StringBuilder();
        private final List<ConfluenceSide> undersider = new ArrayList<>();
        private final Set<String> tags = new HashSet<>();
        public List<Regel> regler = new ArrayList<>();

        private InnholdConfluenceSide(String tittel) {
            this.tittel = tittel.substring(0, 1).toUpperCase() + tittel.substring(1);
        }

        public String getInnhold() {
            if (tags.contains("sats"))
                return satsInnhold();
            else
                return innholdMedOverskrift();
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
                    "sats: "+innhold.toString() +
                    "{details}" + lagReferanser();
        }

        private String lagReferanser() {
            StringBuilder sb = new StringBuilder();

            if(regler!=null && regler.size()>0) {
                sb.append("\r\nh4. Hjemler \r\n");
                regler.stream().forEach(r->sb.append("* ["+r.getKortnavnOgParagraf()+"]\r\n"));
            }

            sb.append("\r\n\r\nh4. Referanser \r\n");
            sb.append("{incoming-links:mode=list}Ingen referanser{incoming-links} \r\n");

            return sb.toString();
        }
    }
}
