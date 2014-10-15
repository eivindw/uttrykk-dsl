package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import ske.fastsetting.skatt.uttrykk.UttrykkResultat;

import java.util.HashMap;
import java.util.Map;

public class MapUttrykkBeskriver implements UttrykkBeskriver<Map<String, ?>> {

    @Override
    public Map<String, ?> beskriv(UttrykkResultat<?> resultat) {
        return new HashMap<String, Object>() {{
            put("startId", resultat.start());
            put("verdi", resultat.verdi());
            put("uttrykk", resultat.uttrykk());
        }};
    }
}
