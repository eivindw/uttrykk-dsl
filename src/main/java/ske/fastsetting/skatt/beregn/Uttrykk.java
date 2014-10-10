package ske.fastsetting.skatt.beregn;

import java.util.HashMap;
import java.util.Map;

public interface Uttrykk<T> {

    T eval();

    String navn();

    String id();

    default String beskrivUttrykk(UttrykkContext ctx) {
        return null;
    }

    default String beskriv(UttrykkContext ctx) {
        ctx.leggTil(id(), map(ctx));
        return id();
    }

    default Map map(UttrykkContext ctx) {
        Map<String, Object> map = new HashMap<>();

        map.put("navn", navn());
        map.put("verdi", eval());
        map.computeIfAbsent("uttrykk", k -> beskrivUttrykk(ctx));

        return map;
    }
}
