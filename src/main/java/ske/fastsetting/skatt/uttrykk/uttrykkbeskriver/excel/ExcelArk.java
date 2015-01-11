package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel;

import org.apache.poi.ss.usermodel.*;

/**
* Created by jorn ola birkeland on 11.01.15.
*/
class ExcelArk {

    private final Sheet ark;
    private int gjeldendeRadNummer;

    private ExcelArk(Sheet sheet) {
        this.ark = sheet;

        lagOverskriftRad(sheet);

        gjeldendeRadNummer=1;
    }

    public static ExcelArk nytt(Workbook workbook, String navn) {

        return new ExcelArk(workbook.createSheet(navn));
    }


    public void leggTilVerdi(String navn, String verdi, String hjemmel) {
        Cell uttrykkCelle = opprettUttrykkCelle(navn, hjemmel);

        ExcelVerdi excelVerdi = ExcelVerdi.parse(verdi);

        excelVerdi.skrivTilCelle(uttrykkCelle);

        System.out.println("Ark: " + ark.getSheetName() + ", navn: " + navn + ", verdi: " + excelVerdi + " ,hjemmel: " + hjemmel);
    }

    public void leggTilFunksjon(String navn, String uttrykk, String hjemmel) {
        Cell uttrykkCelle = opprettUttrykkCelle(navn, hjemmel);

        uttrykkCelle.setCellFormula(uttrykk);

        ExcelUtil.formaterCelleverdiSomKroner(uttrykkCelle);

        System.out.println("Ark: " + ark.getSheetName() + ", navn: " + navn + ", uttrykk: " + uttrykk + " ,hjemmel: " + hjemmel);
    }

    private Cell opprettUttrykkCelle(String navn, String hjemmel) {
        int rad = gjeldendeRadNummer++;

        Row row = ark.createRow(rad);

        row.createCell(0).setCellValue(navn);
        row.createCell(2).setCellValue(hjemmel);

        Cell cell = row.createCell(1);

        ExcelUtil.settCellenavn(ark, rad, 1, navn);

        return cell;
    }

    private static void lagOverskriftRad(Sheet sheet) {
        Row row = sheet.createRow(0);
        final Workbook workbook = sheet.getWorkbook();

        CellStyle cs = ExcelUtil.lagOverskriftStil(workbook);

        lagOverskrift(row, 0, "Navn",cs);
        lagOverskrift(row, 1, "Uttrykk",cs);
        lagOverskrift(row, 2, "Hjemmel",cs );

    }


    private static void lagOverskrift(Row rad, int kolonne, String tekst, CellStyle style) {
        Cell celle = rad.createCell(kolonne);
        celle.setCellValue(tekst);
        celle.setCellStyle(style);
    }


}
