package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel;

/**
 * Created by jorn ola birkeland on 10.01.15.
 */


import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.util.IdUtil;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.UttrykkBeskriver;

import java.util.*;
import java.util.stream.Collectors;

public class ExcelUttrykkBeskriver implements UttrykkBeskriver<Workbook> {

    public static final String DEFAULT_ARK = "uklassifisert";
    private final XSSFWorkbook workbook;
    private final Map<String, ExcelArk> excelArk;
    private final Set<String> registrerteUttrykk;
    private UttrykkResultat<?> resultat;

    public ExcelUttrykkBeskriver() {
        workbook = new XSSFWorkbook();
        excelArk = new HashMap<>();
        registrerteUttrykk = new HashSet<>();

        ExcelArk skattyterArk = ExcelArk.nytt(workbook, "skattyter");
        skattyterArk.leggTilVerdi("alder","45","");
        skattyterArk.leggTilVerdi("bostedkommune","Lørenskog","");

        excelArk.put(skattyterArk.navn(),skattyterArk);

    }

    @Override
    public Workbook beskriv(UttrykkResultat<?> resultat) {

        this.resultat = resultat;



        new RekursivUttrykkBeskriver(resultat.start()).beskriv();

        //ExcelUtil.autotilpassKolonner(workbook, 0, 1, 2);

        return workbook;
    }

    private static String finnHjemler(Map map) {
        final List<Regel> hjemler = (List<Regel>) map.getOrDefault(UttrykkResultat.KEY_REGLER, Collections.emptyList());

        return hjemler.stream().map(Regel::kortnavnOgParagraf).collect(Collectors.joining(", "));
    }

    private static String finnUttrykk(Map map) {
        String tmpUttrykkString = "";
        if (map.containsKey(UttrykkResultat.KEY_UTTRYKK)) {
            tmpUttrykkString = (String) map.get(UttrykkResultat.KEY_UTTRYKK);
        }
        return tmpUttrykkString;
    }

    private static String finnNavn(Map map) {
        String navn = null;

        if (map.containsKey(UttrykkResultat.KEY_NAVN)) {
            navn = (String) map.get(UttrykkResultat.KEY_NAVN);
        }
        return navn;
    }

    private ExcelArk finnExcelArk(Map map) {
        final Set<String> tags = (Set<String>) map.getOrDefault(UttrykkResultat.KEY_TAGS, Collections.emptySet());

        String arkNavn = tags.size() > 0 ? new ArrayList<>(tags).get(0) : DEFAULT_ARK;

        return excelArk.computeIfAbsent(arkNavn, navn -> ExcelArk.nytt(workbook, navn));
    }

    private class RekursivUttrykkBeskriver {

        private final String navn;
        private final String uttrykk;
        private final String hjemmel;
        private final Set<String> subIder;
        private final ExcelArk ark;

        public RekursivUttrykkBeskriver(String id) {

            Map map = resultat.uttrykk(id);

            navn = finnNavn(map);
            uttrykk = finnUttrykk(map);
            hjemmel = finnHjemler(map);
            ark = finnExcelArk(map);

            subIder = IdUtil.parseIder(uttrykk);
        }

        public String beskriv() {

            String resultatUttrykk = uttrykk;

            for (String subId : subIder) {
                resultatUttrykk = resultatUttrykk.replaceAll("<" + subId + ">", new RekursivUttrykkBeskriver(subId).beskriv());
            }

            return harNavn() ? beskrivNavngittUttrykk(resultatUttrykk) : beskriveAnonymtUttrykk(resultatUttrykk);

        }

        private boolean harNavn() {
            return navn != null;
        }

        private String beskriveAnonymtUttrykk(String resultatUttrykk) {

            String uttrykkString;

            if (resultatUttrykk==null || resultatUttrykk.length() == 0) {
                uttrykkString = "";
            } else if (subIder.size() > 0) {
                uttrykkString = "(" + ExcelFormel.parse(resultatUttrykk).tilTekst() + ")";
            } else {
                uttrykkString = ExcelVerdi.parse(resultatUttrykk).tilTekst();
            }

            return uttrykkString;
        }

        private String beskrivNavngittUttrykk(String resultatUttrykk) {
            if (!registrerteUttrykk.contains(navn)) {
                registrerNavngittUttrykk(resultatUttrykk);
            }
            return ExcelUtil.excelNavn(navn);
        }

        private void registrerNavngittUttrykk(String resultatUttrykk) {
            if (subIder.size() > 0) {
                ark.leggTilFunksjon(navn, resultatUttrykk, hjemmel);
            } else {
                ark.leggTilVerdi(navn, resultatUttrykk, hjemmel);
            }

            registrerteUttrykk.add(navn);
        }
    }

}
