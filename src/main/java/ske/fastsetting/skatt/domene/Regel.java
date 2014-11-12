package ske.fastsetting.skatt.domene;

import java.net.URI;

public class Regel {

    private static final String PARAGRAF_TEGN = "§";

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
        ),
        Folketrygdloven(
            "Folketrygdloven",
            "Lov om folketrygd (folketrygdloven)",
            "lov/1997-02-28-19"
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

    public static Regel skattevedtak(String paragraf) {
        return new Regel(RegelType.Skattevedtak, paragraf);
    }

    public static Regel folketrygdloven(String paragraf) {
        return new Regel(RegelType.Folketrygdloven, paragraf);
    }

    public static Regel skatteloven(String paragraf) {
        return new Regel(RegelType.Skatteloven, paragraf);
    }

    public static Regel[] regler(Regel... regel) {
        return regel;
    }

    private Regel(RegelType regel, String paragraf) {
        this.regel = regel;
        this.paragraf = paragraf;
    }

    public String tittel() {
        return regel.getTittel();
    }

    public String paragraf() {
        return paragraf;
    }

    public String kortnavnOgParagraf() {
        return regel.getKortnavn() + " " + PARAGRAF_TEGN + paragraf;
    }

    public URI uri() {
        return URI.create("http://lovdata.no/" + regel.getLovdataRef() + "/" + PARAGRAF_TEGN + paragraf);
    }
}
