package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel;

import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.util.IdUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jorn ola birkeland on 22.01.15.
 */
public class ExcelEktefelleUttrykkResultatKonverterer<V>  {

    public final static String KEY_EXCEL_ID = "excel_id";
    public static final String EKTEFELLE_REGEX = "^ektefelles (.*)$";
    public static final String EKTEFELLE_OUTPUT = "$1";
    public static final String HOVERPEERSON_PREFIKS = "hp_";
    public static final String EKTEFELLE_PREFIKS = "ef_";

    private final Set<String> fellesTags;

    private final Set<String> standardTags;

    public ExcelEktefelleUttrykkResultatKonverterer(String standardTag, String... fellesTags) {

        this.standardTags = new HashSet<>();
        this.standardTags.add(standardTag);
        this.fellesTags = Stream.of(fellesTags).collect(Collectors.toSet());
    }

    public UttrykkResultat<V> konverterResultat(UttrykkResultat<V> uttrykkResultat, boolean erHovedperson) {
        final Map<String,Map> uttrykksmap = new HashMap<>();

        String startId = byggRekursivt(uttrykksmap,erHovedperson,uttrykkResultat,uttrykkResultat.start());

        return new IndreUttrykkResultat<>(uttrykksmap,startId);
    }

    private String byggRekursivt(Map<String,Map> uttrykksmap, boolean erHovedPerson, UttrykkResultat<V> resultat, String id) {
        Map map = resultat.uttrykk(id);
        final Set<String> tags = (Set<String>) map.getOrDefault(UttrykkResultat.KEY_TAGS, standardTags);

        final String prefiks = erHovedPerson ? HOVERPEERSON_PREFIKS : EKTEFELLE_PREFIKS;
        final String nyId = kvalifisertPrefiks(tags, prefiks) +id;

        final String navn = (String)map.getOrDefault(UttrykkResultat.KEY_NAVN, null);
        final String excelID = navn != null ? kvalifisertPrefiks(tags, prefiks) + ExcelUtil.lagGyldigCellenavn(navn) : null;
        final List<Regel> regler = (List< Regel>)map.getOrDefault(UttrykkResultat.KEY_REGLER, null);
        final Set<String> nyeTags = tags.stream().map(t -> fellesTags.contains(t) ? t : prefiks + t).collect(Collectors.toSet());

        String uttrykk = (String)map.getOrDefault(UttrykkResultat.KEY_UTTRYKK,"");

        if(uttrykk.matches(EKTEFELLE_REGEX)) {
            erHovedPerson=!erHovedPerson;
            uttrykk = uttrykk.replaceFirst(EKTEFELLE_REGEX, EKTEFELLE_OUTPUT);
        }

        for(String subId : IdUtil.parseIder(uttrykk)) {
            uttrykk = uttrykk.replaceAll(subId, byggRekursivt(uttrykksmap,erHovedPerson,resultat,subId));
        }

        Map<String,Object> nyMap = new HashMap<>();
        uttrykksmap.put(nyId,nyMap);

        nyMap.computeIfAbsent(KEY_EXCEL_ID, k -> excelID);
        nyMap.computeIfAbsent(UttrykkResultat.KEY_NAVN, k -> navn);
        nyMap.computeIfAbsent(UttrykkResultat.KEY_REGLER,k-> regler);
        nyMap.computeIfAbsent(UttrykkResultat.KEY_TAGS,k-> nyeTags);
        nyMap.put(UttrykkResultat.KEY_UTTRYKK,uttrykk);

        return nyId;
    }

    private String kvalifisertPrefiks(Set<String> tags, String prefiks) {
        final String tag1 = tags.size() > 0 ? new ArrayList<String>(tags).get(0) : null;
        return fellesTags.contains(tag1) ? "": prefiks;
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
