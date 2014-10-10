package ske.fastsetting.skatt.beregn;

import java.util.HashMap;
import java.util.Map;

public class UttrykkContext {

    private final Map<String, Map> uttrykksmap = new HashMap<>();

    public void leggTil(String id, Map map) {
        uttrykksmap.computeIfAbsent(id, k -> map);
    }

    public Map<String, Map> uttrykk() {
        return uttrykksmap;
    }

    @Override
    public String toString() {
        return uttrykksmap.toString();
    }
}
