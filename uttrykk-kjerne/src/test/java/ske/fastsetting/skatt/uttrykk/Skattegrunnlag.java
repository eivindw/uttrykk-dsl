package ske.fastsetting.skatt.uttrykk;

import java.util.HashMap;
import java.util.Map;

import ske.fastsetting.skatt.domene.Belop;

public class Skattegrunnlag {


    public static final String SKATTEGRUNNLAGOBJEKT_TYPE__INNTEKT_JORDBRUK = "inntekt_jordbruk";
    private Map<String, Belop> poster = new HashMap<>();

    public Skattegrunnlag post(String postnr, Belop belop) {
        poster.put(postnr, belop);
        return this;
    }

    public Belop getPostBelop(String postnummer) {
        return poster.computeIfAbsent(postnummer, p -> Belop.kr(45));
    }
}
