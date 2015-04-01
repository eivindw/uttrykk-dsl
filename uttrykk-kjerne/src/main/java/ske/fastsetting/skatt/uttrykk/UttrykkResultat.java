package ske.fastsetting.skatt.uttrykk;

import java.util.Map;

public interface UttrykkResultat<V> {

    final String KEY_NAVN = "navn";
    final String KEY_REGLER = "regler";
    final String KEY_TAGS = "tags";
    final String KEY_VERDI = "verdi";
    final String KEY_UTTRYKK = "uttrykk";

    Map<String, Map> uttrykk();

    Map uttrykk(String id);

    V verdi();

    String start();
}
