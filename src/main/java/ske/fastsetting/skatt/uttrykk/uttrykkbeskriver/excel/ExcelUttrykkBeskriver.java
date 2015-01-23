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

    private final String standardArkNavn;
    private final XSSFWorkbook workbook;
    private final Map<String, ExcelArk> excelArk;
    private final Set<String> registrerteUttrykk;
    private final ExcelFormateringshint formateringshint;
    private UttrykkResultat<?> resultat;

    public ExcelUttrykkBeskriver() {
        this(new XSSFWorkbook(), "uklassifisert");

    }

    public ExcelUttrykkBeskriver(XSSFWorkbook workbook, String standardArkNavn) {
        this.standardArkNavn = standardArkNavn;
        this.workbook = workbook;
        excelArk = new HashMap<>();
        registrerteUttrykk = new HashSet<>();
        formateringshint = new ExcelFormateringshint();
    }

    @Override
    public Workbook beskriv(UttrykkResultat<?> resultat) {

        this.resultat = resultat;

        new RekursivUttrykkBeskriver(resultat.start()).beskriv();

        ExcelUtil.autotilpassKolonner(workbook, 0, 1, 2);

        return workbook;
    }

    public ExcelArk opprettArk(String arkNavn) {
        return excelArk.computeIfAbsent(arkNavn, n -> ExcelArk.nytt(workbook, n, formateringshint));
    }

    public void leggTilFormateringshint(String navnPattern, String excelFormat) {
        formateringshint.leggTilHint(navnPattern, excelFormat);
    }

    public void settStandardFormatering(String excelFormat) {
        formateringshint.settStandardFormatering(excelFormat);
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

    private static String finnId(Map map) {
        String id = "";
        if (map.containsKey(ExcelEktefelleUttrykkResultatKonverterer.KEY_EXCEL_ID)) {
            id = (String) map.get(ExcelEktefelleUttrykkResultatKonverterer.KEY_EXCEL_ID);
        } else {
            final String navn = finnNavn(map);
            id = navn != null ? ExcelUtil.lagGyldigCellenavn(navn) : null;
        }
        return id;
    }


    private static String finnNavn(Map map) {
        String navn = null;

        if (map.containsKey(UttrykkResultat.KEY_NAVN)) {
            navn = (String) map.get(UttrykkResultat.KEY_NAVN);
        }
        return navn;
    }

    private ExcelArk finnExcelArk(Map map) {
        String arkNavn = finnArknavn(map);

        return opprettArk(arkNavn);
    }

    private String finnArknavn(Map map) {
        final Set<String> tags = (Set<String>) map.getOrDefault(UttrykkResultat.KEY_TAGS, Collections.emptySet());

        return (String) (tags.size() > 0 ? new ArrayList<>(tags).get(0) : standardArkNavn);
    }


    private class RekursivUttrykkBeskriver {

        private final String celleId;
        private final String navn;
        private final String uttrykk;
        private final String hjemmel;
        private final Set<String> subIder;
        private final ExcelArk ark;

        public RekursivUttrykkBeskriver(String id) {

            Map map = resultat.uttrykk(id);

            celleId = finnId(map);
            navn = finnNavn(map);
            uttrykk = finnUttrykk(map);
            hjemmel = finnHjemler(map);
            ark = finnExcelArk(map);

            subIder = IdUtil.parseIder(uttrykk);
        }

        public String beskriv() {

            String resultatUttrykk = uttrykk;

            if (harSubIder()) {
                resultatUttrykk = ExcelFormel.parse(resultatUttrykk).tilTekst();
            }

            for (String subId : subIder) {
                resultatUttrykk = resultatUttrykk.replaceAll("<" + subId + ">", new RekursivUttrykkBeskriver(subId).beskriv());
            }

            return harNavn() ? beskrivNavngittUttrykk(resultatUttrykk) : beskrivAnonymtUttrykk(resultatUttrykk);

        }


        private String beskrivAnonymtUttrykk(String resultatUttrykk) {

            String uttrykkString;

            if (resultatUttrykk == null || resultatUttrykk.length() == 0) {
                uttrykkString = "";
            } else if (harSubIder()) {
                uttrykkString = "(" + resultatUttrykk + ")";
            } else {
                uttrykkString = ExcelVerdi.parse(resultatUttrykk).tilTekst();
            }

            return uttrykkString;
        }

        private String beskrivNavngittUttrykk(String resultatUttrykk) {
            if (!registrerteUttrykk.contains(celleId)) {
                if (harSubIder()) {
                    ark.leggTilFunksjon(celleId, navn, resultatUttrykk, hjemmel);
                } else {
                    ark.leggTilVerdi(celleId, navn, resultatUttrykk, hjemmel);
                }

                registrerteUttrykk.add(celleId);
            }
            return celleId;
        }

        private boolean harSubIder() {
            return subIder.size() > 0;
        }

        private boolean harNavn() {
            return navn != null;
        }

    }

}
