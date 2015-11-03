package ske.fastsetting.skatt.uttrykk.skalSlettes;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jorn ola birkeland on 28.10.15.
 */
public class ForskuddstrekkValidering {

    public static class ForskuddXml {

        public enum Feilkode {
            MARGINALPROSENT_LAV, TREKKPROSENT_HOY, TREKKPROSENT_LAV, MARGINALPROSENT_HOY;

            public ValideringXml toXml() {return null;}
        }
        public enum ForskuddsformXml { INGEN, TABELLKORT, PROSENTKORT }
        public ForskuddsformXml getForskuddsform() { return ForskuddsformXml.INGEN; }
        public BigDecimal getTrekkprosent() { return BigDecimal.ZERO; }
    }
    public static class ValideringXml {}


    public static List<ValideringXml> valider(ForskuddXml forskuddXml) {

        Set<ForskuddXml.Feilkode> feilkoder = new HashSet<>();

        if(forskuddXml.getForskuddsform().equals(ForskuddXml.ForskuddsformXml.TABELLKORT)) {
            if(forskuddXml.getTrekkprosent().compareTo(BigDecimal.valueOf(47))>0) {
                feilkoder.add(ForskuddXml.Feilkode.MARGINALPROSENT_HOY);
            } else if(forskuddXml.getTrekkprosent().compareTo(BigDecimal.valueOf(23))<0) {
                feilkoder.add(ForskuddXml.Feilkode.MARGINALPROSENT_LAV);
            }
        } else if(forskuddXml.getForskuddsform().equals(ForskuddXml.ForskuddsformXml.PROSENTKORT))  {
            if(forskuddXml.getTrekkprosent().compareTo(BigDecimal.valueOf(70))>0) {
                feilkoder.add(ForskuddXml.Feilkode.TREKKPROSENT_HOY);
            } else if(forskuddXml.getTrekkprosent().compareTo(BigDecimal.valueOf(2))<0) {
                feilkoder.add(ForskuddXml.Feilkode.TREKKPROSENT_LAV);
            }

        }

        return feilkoder.stream()
                .map(ForskuddXml.Feilkode::toXml)
                .collect(Collectors.toList());

    }
}
