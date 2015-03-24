package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel;

import ske.fastsetting.skatt.uttrykk.UttrykkResultat;

import java.util.HashMap;
import java.util.Map;

public class ExcelEnsligUttrykkResultatKonverterer {


    public final static String KEY_EXCEL_ID = "excel_id";
    public static final String UTTRYKK = UttrykkResultat.KEY_UTTRYKK;


    public static <V> UttrykkResultat<V> konverterResultat(UttrykkResultat<V> uttrykkResultat) {
        final Map<String, Map> uttrykksmap = new HashMap<>();

        uttrykkResultat.uttrykk().entrySet()
          .forEach(e -> uttrykksmap.put(e.getKey(), kopierOgErstattEktefellesMed0(e.getValue())));

        return new IndreUttrykkResultat<V>(uttrykksmap, uttrykkResultat.start());
    }

    private static Map<String, Object> kopierOgErstattEktefellesMed0(Map<String, Object> map) {
        Map<String, Object> nyMap = new HashMap<>();

        //Copy
        map.entrySet().forEach(e -> nyMap.put(e.getKey(), e.getValue()));

        //Fix excel_ID
        nyMap.put(KEY_EXCEL_ID,
          ExcelUtil.lagGyldigCellenavn((String) map.getOrDefault(UttrykkResultat.KEY_NAVN, ""))
        );

        //Replace "ektefelles" with 0
        if (nyMap.containsKey(UTTRYKK))
            if (((String) nyMap.get(UTTRYKK)).contains("ektefelles"))
                nyMap.put(UTTRYKK, "0");

        return nyMap;
    }


    public static void main(String[] args) {

        Map<String, Integer> m = new HashMap<>();

        m.put("k", null);

        System.out.println(m.get("z"));
        System.out.println(m.get("k"));


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
