package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.Belop;

import java.util.HashMap;
import java.util.Map;

public class Skattegrunnlag {


    private Map<String,Belop> poster = new HashMap<>();

    public Skattegrunnlag post(String postnr, Belop belop) {
        poster.put(postnr,belop);
        return this;
    }

    public Belop getPostBelop(String postnummer) {
        return poster.computeIfAbsent(postnummer,p->new Belop(45));
    }
}
