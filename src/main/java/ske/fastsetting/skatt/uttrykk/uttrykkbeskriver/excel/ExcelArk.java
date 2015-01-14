package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel;

import org.apache.poi.ss.usermodel.*;

/**
* Created by jorn ola birkeland on 11.01.15.
*/
public class ExcelArk {

    private final Sheet ark;
    private int gjeldendeRadNummer;

    private ExcelArk(Sheet sheet) {
        this.ark = sheet;

        ExcelUtil.lagOverskriftRad(sheet, "Begrep", "Definisjon", "Hjemmel");

        gjeldendeRadNummer=1;
    }

    public static ExcelArk nytt(Workbook workbook, String navn) {

        return new ExcelArk(workbook.createSheet(navn));
    }


    public void leggTilVerdi(String navn, String verdi, String hjemmel) {
        Cell uttrykkCelle = opprettUttrykkCelle(navn, hjemmel);

        ExcelUttrykk excelVerdi = ExcelVerdi.parse(verdi);

        excelVerdi.skrivTilCelle(uttrykkCelle);
    }

    public void leggTilFunksjon(String navn, String excelFormel, String hjemmel) {
        Cell uttrykkCelle = opprettUttrykkCelle(navn, hjemmel);

        ExcelFormel formel = new ExcelFormel(excelFormel);

        formel.skrivTilCelle(uttrykkCelle);
    }

    private Cell opprettUttrykkCelle(String navn, String hjemmel) {
        int rad = gjeldendeRadNummer++;

        Row row = ark.createRow(rad);

        row.createCell(0).setCellValue(storForbokstav(navn));
        row.createCell(2).setCellValue(hjemmel);

        Cell cell = row.createCell(1);

        ExcelUtil.settCellenavn(ark, rad, 1, navn);

        return cell;
    }


    public String navn() {
        return ark.getSheetName();
    }

    private static String storForbokstav(String navn) {
        return navn.substring(0,1).toUpperCase() + navn.substring(1);
    }

}
