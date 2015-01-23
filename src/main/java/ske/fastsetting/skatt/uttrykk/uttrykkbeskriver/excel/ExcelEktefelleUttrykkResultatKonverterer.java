package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel;

import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.util.IdUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jorn ola birkeland on 22.01.15.
 */
public class ExcelEktefelleUttrykkResultatKonverterer<V>  {

    public final static String KEY_ID = "id";
    public static final String EKTEFELLE_REGEX = "^ektefelles (.*)$";
    public static final String EKTEFELLE_OUTPUT = "$1";

    private final Set<String> fellesTags;

    private final String standardTag;

    public ExcelEktefelleUttrykkResultatKonverterer(String standardTag, String... fellesTags) {

        this.standardTag = standardTag;
        this.fellesTags = Stream.of(fellesTags).collect(Collectors.toSet());
    }

    public UttrykkResultat<V> konverterResultat(UttrykkResultat<V> uttrykkResultat, boolean erHovedperson) {
        final Map<String,Map> uttrykksmap = new HashMap<>();

        String startId = byggRekursivt(uttrykksmap,erHovedperson,uttrykkResultat,uttrykkResultat.start());

        return new IndreUttrykkResultat<>(uttrykksmap,startId);
    }

    private String byggRekursivt(Map<String,Map> uttrykksmap, boolean erHovedPerson, UttrykkResultat<V> resultat, String id) {
        Map map = resultat.uttrykk(id);

        String prefix = erHovedPerson ? "hp_" : "ef_";
        String nyId = (erFellesTag(map) ? "": prefix)+id;

        Map<String,Object> nyMap = new HashMap<>();
        uttrykksmap.put(nyId,nyMap);

        final String navn = (String)map.getOrDefault(UttrykkResultat.KEY_NAVN, null);

        nyMap.put(KEY_ID,navn != null ? (erFellesTag(map) ? "" : prefix)+ExcelUtil.lagGyldigCellenavn(navn):null);
        nyMap.put(UttrykkResultat.KEY_NAVN, navn);
        nyMap.put(UttrykkResultat.KEY_REGLER,map.getOrDefault(UttrykkResultat.KEY_REGLER, Collections.emptyList()));

        final Set<String> tags = (Set<String>) map.getOrDefault(UttrykkResultat.KEY_TAGS, standardTags());
        nyMap.put(UttrykkResultat.KEY_TAGS, tags.stream().map(t -> fellesTags.contains(t) ? t : prefix + t).collect(Collectors.toSet()));

        String uttrykk = (String)map.getOrDefault(UttrykkResultat.KEY_UTTRYKK,"");
        if(uttrykk.matches(EKTEFELLE_REGEX)) {
            erHovedPerson=!erHovedPerson;
            uttrykk = uttrykk.replaceFirst(EKTEFELLE_REGEX, EKTEFELLE_OUTPUT);
        }

        Set<String> subIder = IdUtil.parseIder(uttrykk);

        for(String subId : subIder) {
            uttrykk = uttrykk.replaceAll(subId, byggRekursivt(uttrykksmap,erHovedPerson,resultat,subId));
        }

        nyMap.put(UttrykkResultat.KEY_UTTRYKK,uttrykk);

        return nyId;
    }

    private boolean erFellesTag(Map map) {
        final Set<String> tags = (Set<String>) map.getOrDefault(UttrykkResultat.KEY_TAGS, standardTags());

        return fellesTags.contains((tags.size() > 0 ? new ArrayList<String>(tags).get(0) : null));
    }


    private Set<String> standardTags() {
        HashSet<String> tags = new HashSet<>();
        tags.add(standardTag);
        return tags;
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
