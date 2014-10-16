package ske.fastsetting.skatt.domene;

import java.net.URI;

public class Regel {

    private enum RegelType {
        Skattevedtak(
            "Stortingets skattevedtak",
            "Stortingets skattevedtak for inntektsåret 2014",
            "forskrift/2013-12-05-1499"
        ),
        Skatteloven(
            "Skatteloven",
            "Lov om skatt av formue og inntekt (skatteloven)",
            "lov/1999-03-26-14"
        );

        private final String tittel;
        private final String lovdataRef;
        private final String kortnavn;

        RegelType(String kortnavn, String tittel, String lovdataRef) {
            this.kortnavn = kortnavn;
            this.lovdataRef = lovdataRef;
            this.tittel = tittel;
        }

        public String getTittel() {
            return tittel;
        }

        public String getLovdataRef() {
            return lovdataRef;
        }

        public String getKortnavn() {
            return kortnavn;
        }
    }

    private final RegelType regel;
    private final String paragraf;
    private final String[] underpunkt;

    public static Regel skattevedtak(String paragraf) {
        return new Regel(RegelType.Skattevedtak, paragraf);
    }

    public static Regel skatteloven(String paragraf, String... underpunkt) {
        return new Regel(RegelType.Skatteloven, paragraf, underpunkt);
    }

    public Regel(RegelType regel, String paragraf, String... underpunkt) {
        this.regel = regel;
        this.paragraf = paragraf;
        this.underpunkt = underpunkt;
    }

    public String tittel() {
        return regel.getTittel();
    }

    public String paragraf() {
        return paragraf;
    }

    public String kortnavnOgParagraf() {
        return regel.getKortnavn() + " §" + paragraf;
    }

    public URI uri() {
        return URI.create("http://lovdata.no/" + regel.getLovdataRef() + "/§" + paragraf);
    }
}
