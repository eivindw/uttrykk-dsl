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
    private final Map<String,ExcelArk> excelArk;
    private final Set<String> registrerteUttrykk;

    public ExcelUttrykkBeskriver() {
        workbook = new XSSFWorkbook();
        excelArk = new HashMap<>();
        registrerteUttrykk = new HashSet<>();
    }

    @Override
    public Workbook beskriv(UttrykkResultat<?> resultat) {

        beskriv(resultat.start(), resultat);

        ExcelUtil.autotilpassKolonner(workbook,0,1,2);

        return workbook;
    }

    private String beskriv(String id, UttrykkResultat<?> resultat) {
        Map map = resultat.uttrykk().get(id);


        String navn = finnNavn(map);
        String uttrykk = finnUttrykk(map);
        String hjemmel = finnHjemler(map);

        Set<String> subIder = IdUtil.parseIder(uttrykk);

        for (String subId : subIder) {
            uttrykk = uttrykk.replaceAll("<" + subId + ">", beskriv(subId, resultat));
        }

        String uttrykkString = "";
        ExcelArk ark = finnExcelArk(map);

        if (navn != null) {
            if (!registrerteUttrykk.contains(navn)) {
                if (subIder.size() > 0) {
                    ark.leggTilFunksjon(navn, uttrykk, hjemmel);
                } else {
                    ark.leggTilVerdi(navn, uttrykk, hjemmel);
                }

                registrerteUttrykk.add(navn);
            }
            uttrykkString = ExcelUtil.excelNavn(navn);
        } else if (uttrykk.length() > 0) {
            if (subIder.size() > 0) {
                uttrykkString = "(" + uttrykk + ")";
            } else {
                uttrykkString = ExcelVerdi.parse(uttrykk).verdi();
            }
        }

        return uttrykkString;


    }

    private String finnHjemler(Map map) {
        final List<Regel> hjemler = (List<Regel>) map.getOrDefault(UttrykkResultat.KEY_REGLER, Collections.emptyList());

        return hjemler.stream().map(Regel::kortnavnOgParagraf).collect(Collectors.joining(", "));
    }

    private String finnUttrykk(Map map) {
        String tmpUttrykkString = "";
        if (map.containsKey(UttrykkResultat.KEY_UTTRYKK)) {
            tmpUttrykkString = (String) map.get(UttrykkResultat.KEY_UTTRYKK);
        }
        return tmpUttrykkString;
    }

    private String finnNavn(Map map) {
        String navn = null;

        if (map.containsKey(UttrykkResultat.KEY_NAVN)) {
            navn = (String) map.get(UttrykkResultat.KEY_NAVN);
        }
        return navn;
    }

    private ExcelArk finnExcelArk(Map map) {
        final Set<String> tags = (Set<String>) map.getOrDefault(UttrykkResultat.KEY_TAGS, Collections.emptySet());

        String arkNavn = tags.size() > 0 ? new ArrayList<>(tags).get(0) : DEFAULT_ARK;

        return excelArk.computeIfAbsent(arkNavn,navn->ExcelArk.nytt(workbook,navn));
    }


}
