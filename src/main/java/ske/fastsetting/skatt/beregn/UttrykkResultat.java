package ske.fastsetting.skatt.beregn;

import java.util.Map;

public interface UttrykkResultat<T> {
    Map<String, Map> uttrykk();

    T verdi();

    String start();
}
