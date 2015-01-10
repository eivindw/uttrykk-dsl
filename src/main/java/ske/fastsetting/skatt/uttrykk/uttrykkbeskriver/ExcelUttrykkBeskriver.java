package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

/**
 * Created by jorn ola birkeland on 10.01.15.
 */


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.util.IdUtil;

import java.util.*;

public class ExcelUttrykkBeskriver implements UttrykkBeskriver<Workbook> {

    public static final String DEFAULT_ARK = "uklassifisert";
    private final XSSFWorkbook workbook;
    private final ExcelArk excelArk;
    private final Set<String> registrerteUttrykk;

    public ExcelUttrykkBeskriver() {
        workbook = new XSSFWorkbook();
        excelArk = new ExcelArk(workbook);
        registrerteUttrykk = new HashSet<>();
    }

    @Override
    public Workbook beskriv(UttrykkResultat<?> resultat) {

        beskriv(resultat.start(), resultat);


        return workbook;
    }

    private String beskriv(String id, UttrykkResultat<?> resultat) {
        Map map = resultat.uttrykk().get(id);

        final Set<String> tags = (Set<String>) map.getOrDefault(UttrykkResultat.KEY_TAGS, Collections.emptySet());

        String arkNavn = tags.size() > 0 ? new ArrayList<>(tags).get(0) : DEFAULT_ARK;
        String navn = null;
        String tmpUttrykkString = "";


        Set<String> subIder = new HashSet<>();

        if (map.containsKey(UttrykkResultat.KEY_NAVN)) {
            navn = (String) map.get(UttrykkResultat.KEY_NAVN);
        }

        if (map.containsKey(UttrykkResultat.KEY_UTTRYKK)) {
            tmpUttrykkString = (String) map.get(UttrykkResultat.KEY_UTTRYKK);
            subIder = IdUtil.parseIder(tmpUttrykkString);
        }

        for (String subId : subIder) {
            tmpUttrykkString = tmpUttrykkString.replaceAll("<" + subId + ">", beskriv(subId, resultat));
        }

        if (navn != null) {
            if (!registrerteUttrykk.contains(navn)) {
                if (subIder.size() > 0) {
                    excelArk.leggTilFunksjon(arkNavn, navn, tmpUttrykkString, "");
                } else {
                    excelArk.leggTilVerdi(arkNavn, navn, tmpUttrykkString, "");
                }

                registrerteUttrykk.add(navn);
            }
            return excelNavn(navn);
        } else if (tmpUttrykkString.length() > 0) {
            if (subIder.size() > 0) {
                return "(" + tmpUttrykkString + ")";
            } else {
                return ExcelVerdi.parse(tmpUttrykkString).verdi();
            }
        } else {
            return "";
        }


    }


    private static String excelNavn(String navn) {
        return navn.replace(' ', '_');
    }

    private static class ExcelArk {

        private final Workbook workbook;
        private final Map<String, Integer> sisteArkRad = new HashMap<>();
        private final CellStyle overskriftStil;

        public ExcelArk(Workbook workbook) {
            this.workbook = workbook;
            overskriftStil = overskriftStil(workbook);
        }

        public void leggTilVerdi(String arkNavn, String navn, String verdi, String hjemmel) {
            Cell uttrykkCelle = opprettUttrykkCelle(arkNavn, navn, hjemmel);

            ExcelVerdi excelVerdi = ExcelVerdi.parse(verdi);

            excelVerdi.skrivTilCelle(uttrykkCelle);

            System.out.println("Ark: " + arkNavn + ", navn: " + navn + ", verdi: " + excelVerdi + " ,hjemmel: " + hjemmel);
        }

        public void leggTilFunksjon(String arkNavn, String navn, String uttrykk, String hjemmel) {
            Cell uttrykkCelle = opprettUttrykkCelle(arkNavn, navn, hjemmel);

            uttrykkCelle.setCellFormula(uttrykk);

            ExcelUtil.formaterCelleSomKroner(uttrykkCelle);

            System.out.println("Ark: " + arkNavn + ", navn: " + navn + ", uttrykk: " + uttrykk + " ,hjemmel: " + hjemmel);
        }

        private Cell opprettUttrykkCelle(String arkNavn, String navn, String hjemmel) {
            sjekkArk(arkNavn);

            int rad = sisteArkRad.computeIfPresent(arkNavn, (s, i) -> i++);
            Sheet ark = workbook.getSheet(arkNavn);

            Row row = ark.createRow(rad);

            row.createCell(0).setCellValue(navn);
            row.createCell(2).setCellValue(hjemmel);

            Cell cell = row.createCell(1);

            CellReference cellReference = new CellReference(arkNavn, rad, 1, true, true);
            AreaReference areaReference = new AreaReference(cellReference, cellReference);

            Name name = workbook.createName();
            name.setNameName(ExcelUttrykkBeskriver.excelNavn(navn));
            name.setRefersToFormula(areaReference.formatAsString());

            return cell;
        }


        private void sjekkArk(String arkNavn) {
            if (!sisteArkRad.containsKey(arkNavn)) {
                Sheet ark = workbook.createSheet(arkNavn);
                Row row = ark.createRow(0);

                lagOverskrift(row, 0, "Navn");
                lagOverskrift(row, 1, "Uttrykk");
                lagOverskrift(row, 2, "Hjemmel");

                sisteArkRad.put(arkNavn, 0);
            }
        }

        private void lagOverskrift(Row rad, int kolonne, String tekst) {
            Cell celle = rad.createCell(kolonne);
            celle.setCellValue(tekst);
            celle.setCellStyle(overskriftStil);
        }

        private static CellStyle overskriftStil(Workbook workbook) {
            CellStyle cs = workbook.createCellStyle();
            Font f = workbook.createFont();

            // Set font 1 to 12 point type, blue and bold
            f.setFontHeightInPoints((short) 12);
            f.setBoldweight(Font.BOLDWEIGHT_BOLD);

            cs.setFont(f);

            return cs;
        }
    }

    private static class ExcelVerdi {

        private final Type type;
        private final Object value;
        private final String format;

        private enum Type {
            Numeric,
            Text,
        }

        public static ExcelVerdi parse(String text) {
            if (text.startsWith("kr ")) {
                return new ExcelVerdi(Type.Numeric, Double.parseDouble(text.replace("kr ", "").replace(" ","")), "kr ###,###,###,##0");
            } else if (text.endsWith("%")) {
                return new ExcelVerdi(Type.Numeric, Double.parseDouble(text.replace("%", "")) / 100, "0.00%");
            } else {
                return new ExcelVerdi(Type.Text, text, "");
            }

        }

        private ExcelVerdi(Type type, Object value, String format) {
            this.type = type;
            this.value = value;
            this.format = format;
        }

        public String verdi() {
            return value.toString();
        }

        public void skrivTilCelle(Cell celle) {
            ExcelUtil.formaterCelle(celle, format);

            switch (type) {
                case Numeric:
                    celle.setCellValue((double) value);
                    break;
                default:
                    celle.setCellValue(value.toString());
                    break;
            }
        }

        public String toString() {
            return type+" "+value+", "+format;
        }
    }

    public static class ExcelUtil {

        private static void formaterCelle(Cell celle, String format) {
            final Workbook workbook = celle.getRow().getSheet().getWorkbook();
            CellStyle style = workbook.createCellStyle();
            DataFormat dataFormat = workbook.createDataFormat();
            style.setDataFormat(dataFormat.getFormat(format));
            celle.setCellStyle(style);
        }

        private static void formaterCelleSomKroner(Cell celle) {
            formaterCelle(celle,"kr #,##0");
        }
    }
}
