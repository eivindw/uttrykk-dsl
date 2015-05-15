package ske.fastsetting.skatt.domene;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

public class Regel {

    private static final String PARAGRAF_TEGN = "§";
    private static final String HTML_PARAGRAF_TEGN = urlEncode(PARAGRAF_TEGN);

    enum RegelType {
        Skattevedtak(
          "SSV",
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
        ),
        Takseringsreglene(
          "Takseringsreglene",
          "Forskrift om takseringsregler 2014",
          "forskrift/2014-11-26-1474"
        ),
        ForskriftTilSkatteloven(
          "Forskrift til skatteloven",
            "FSFIN Forskrift til skatteloven",
            "forskrift/1999-11-19-1158"
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

    public static Regel takseringsreglene(String paragraf) {
        return new Regel(RegelType.Takseringsreglene, paragraf);
    }

    public static Regel forskriftTilSkatteloven(String paragraf) {
        return new Regel(RegelType.ForskriftTilSkatteloven, paragraf);
    }


    public static Regel[] regler(Regel... regel) {
        return regel;
    }

    Regel(RegelType regel, String paragraf) {
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

    public String kortnavnOgParagrafUtenTegn() {
        return regel.getKortnavn() + " " + paragraf;
    }

    public URI uri() {
        return URI.create("http://lovdata.no/" + regel.getLovdataRef() + "/" + HTML_PARAGRAF_TEGN + paragraf);
    }

    private static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Klarte ikke å url-encode: " + str, e);
        }
    }
}
