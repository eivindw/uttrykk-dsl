package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.confluence;

import ske.fastsetting.skatt.uttrykk.UttrykkResultat;

import java.util.HashMap;
import java.util.Map;

public class ConfluenceResultatKonverterer {


    public static final String UTTRYKK = UttrykkResultat.KEY_UTTRYKK;


    public static <V> UttrykkResultat<V> konverterResultat(UttrykkResultat<V> uttrykkResultat) {
        final Map<String, Map> uttrykksmap = new HashMap<>();

        uttrykkResultat.uttrykk().entrySet()
          .forEach(e -> uttrykksmap.put(e.getKey(), kopierOgErstattUttrykk(e.getValue())));

        return new IndreUttrykkResultat<V>(uttrykksmap, uttrykkResultat.start());
    }

    private static Map<String, Object> kopierOgErstattUttrykk(Map<String, Object> map) {
        Map<String, Object> nyMap = new HashMap<>();

        //Copy
        map.entrySet().forEach(e -> nyMap.put(e.getKey(), e.getValue()));

        //Replace "ektefelles" with 0
        if (nyMap.containsKey(UTTRYKK)) {
            String uttrykk = erstattUttrykk((String) nyMap.get(UTTRYKK));
            nyMap.put(UTTRYKK, uttrykk);
        }

        return nyMap;
    }

    private static String erstattUttrykk(String uttrykk) {

        String resultat = uttrykk;

        resultat = resultat.startsWith("multisats") ? resultat.replace(",", ", ") : resultat;
        resultat = resultat.replaceAll("multisats\\((.*)\\)", "$1");
        resultat = resultat.replaceAll("satsFraTil\\((.*),(.*),(.*),(.*)\\)", "$2 av $1 over $3 og inntil $4");
        resultat = resultat.replaceAll("satsFra\\((.*),(.*),(.*)\\)", "$2 av $1 over $3");

        return resultat;
    }


    private static class IndreUttrykkResultat<V> implements UttrykkResultat<V> {
        private final Map<String, Map> uttrykksmap;
        private final String start;

        public IndreUttrykkResultat(Map<String, Map> uttrykksmap, String start) {
            this.uttrykksmap = uttrykksmap;
            this.start = start;
        }

        @Override
        public Map<String, Map> uttrykk() {
            return uttrykksmap;
        }

        @Override
        public Map uttrykk(String id) {
            return uttrykksmap.get(id);
        }

        @Override
        public V verdi() {
            return null;
        }

        @Override
        public String start() {
            return start;
        }
    }


}
