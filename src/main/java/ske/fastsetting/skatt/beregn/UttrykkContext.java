package ske.fastsetting.skatt.beregn;

import java.util.HashMap;
import java.util.Map;

public class UttrykkContext<T> {

    private final Map<String, Map> uttrykksmap = new HashMap<>();
    private final String start;

    public UttrykkContext(String start) {
        this.start = start;
    }

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

    public T verdi() {
        return (T) uttrykksmap.get(start).get("verdi");
    }

    public String start() {
        return start;
    }
}
