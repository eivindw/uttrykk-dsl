package ske.fastsetting.skatt.uttrykk;

import java.util.Map;

public interface UttrykkResultat<T> {

    final String KEY_NAVN = "navn";
    final String KEY_VERDI = "verdi";
    final String KEY_UTTRYKK = "uttrykk";

    Map<String, Map> uttrykk();

    T verdi();

    String start();
}
