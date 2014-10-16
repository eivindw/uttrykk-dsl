package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import ske.fastsetting.skatt.uttrykk.UttrykkResultat;

import java.util.HashMap;
import java.util.Map;

public class MapUttrykkBeskriver implements UttrykkBeskriver<Map<String, ?>> {

    @Override
    public Map<String, ?> beskriv(UttrykkResultat<?> resultat) {
        final HashMap<String, Object> map = new HashMap<>();

        map.computeIfAbsent("startId", k -> resultat.start());
        map.computeIfAbsent("verdi", k -> resultat.verdi());
        map.computeIfAbsent("uttrykk", k -> resultat.uttrykk());

        return map;
    }
}
