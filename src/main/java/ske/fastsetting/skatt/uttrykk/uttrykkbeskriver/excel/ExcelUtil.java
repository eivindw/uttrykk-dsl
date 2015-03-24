package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;

public class ExcelUtil {

    public static void formaterCelleverdi(Cell celle, String format) {
        final Workbook workbook = celle.getRow().getSheet().getWorkbook();
        CellStyle style = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        style.setDataFormat(dataFormat.getFormat(format));
        celle.setCellStyle(style);
    }

    public static void formaterCelleverdiSomKroner(Cell celle) {
        formaterCelleverdi(celle, "kr #,##0");
    }


    //TODO bruk noko anna enn _ her...
    public static String lagGyldigCellenavn(String navn) {
        return navn.replaceAll("[^A-Za-z0-9]", "_");
    }


    static void settCellenavn(Sheet sheet, int rad, int kolonne, String navn) {
        CellReference cellReference = new CellReference(sheet.getSheetName(), rad, kolonne, true, true);
        AreaReference areaReference = new AreaReference(cellReference, cellReference);

        Name name = sheet.getWorkbook().createName();
        name.setNameName(lagGyldigCellenavn(navn));
        name.setRefersToFormula(areaReference.formatAsString());
    }

    static void autotilpassKolonner(Workbook workbook, int... kolonner) {
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);

            for (int kolonne : kolonner) {
                try {
                    sheet.autoSizeColumn(kolonne);
                } catch (Exception e) {
                    // In case if graphical environment is not available, you must tell Java that you are running in
                    // headless mode and set the following system property: java.awt.headless=true
                }
            }
        }


    }

    static void lagOverskriftRad(Sheet sheet, String... kolonnenavn) {
        Row row = sheet.createRow(0);
        final Workbook workbook = sheet.getWorkbook();

        CellStyle cs = lagOverskriftStil(workbook);

        for (int i = 0; i < kolonnenavn.length; i++) {
            lagOverskrift(row, i, kolonnenavn[i], cs);
        }

    }

    private static void lagOverskrift(Row rad, int kolonne, String tekst, CellStyle style) {
        Cell celle = rad.createCell(kolonne);
        celle.setCellValue(tekst);
        celle.setCellStyle(style);
    }

    private static CellStyle lagOverskriftStil(Workbook workbook) {
        CellStyle cs = workbook.createCellStyle();
        Font f = workbook.createFont();

        f.setFontHeightInPoints((short) 12);
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        cs.setFont(f);
        return cs;
    }

}
