package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel;

import org.apache.poi.ss.usermodel.*;

/**
* Created by jorn ola birkeland on 11.01.15.
*/
public class ExcelArk {

    private final Sheet ark;
    private ExcelFormateringshint formateringshint;
    private int gjeldendeRadNummer;

    private ExcelArk(Sheet sheet, ExcelFormateringshint formateringshint) {
        this.ark = sheet;
        this.formateringshint = formateringshint;

        ExcelUtil.lagOverskriftRad(sheet, "Begrep", "Definisjon", "Hjemmel");

        gjeldendeRadNummer=1;
    }

    public static ExcelArk nytt(Workbook workbook, String navn, ExcelFormateringshint formateringshint) {

        return new ExcelArk(workbook.createSheet(navn),formateringshint);
    }


    public void leggTilUttrykk(String celleId, String beskrivelse, ExcelUttrykk uttrykk, String hjemmel) {
        int rad = gjeldendeRadNummer++;

        Row row = ark.createRow(rad);

        row.createCell(0).setCellValue(storForbokstav(beskrivelse));
        row.createCell(2).setCellValue(hjemmel);

        Cell uttrykkCelle = row.createCell(1);
        ExcelUtil.formaterCelleverdi(uttrykkCelle, formateringshint.finnFormat(beskrivelse));

        ExcelUtil.settCellenavn(ark, rad, 1, celleId);


        uttrykk.skrivTilCelle(uttrykkCelle);
    }


    public String navn() {
        return ark.getSheetName();
    }

    private static String storForbokstav(String navn) {
        return navn.substring(0,1).toUpperCase() + navn.substring(1);
    }

}
