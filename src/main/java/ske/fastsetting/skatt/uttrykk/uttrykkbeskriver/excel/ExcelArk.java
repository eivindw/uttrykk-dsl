package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel;

import org.apache.poi.ss.usermodel.*;

public class ExcelArk {

    private final Sheet ark;
    private ExcelFormateringshint formateringshint;
    private int gjeldendeRadNummer;

    private ExcelArk(Sheet sheet, ExcelFormateringshint formateringshint) {
        this.ark = sheet;
        this.formateringshint = formateringshint;

        ExcelUtil.lagOverskriftRad(sheet, "Begrep", "Definisjon", "Hjemmel");

        gjeldendeRadNummer = 1;
    }

    public static ExcelArk nytt(Workbook workbook, String navn, ExcelFormateringshint formateringshint) {

        return new ExcelArk(workbook.createSheet(navn), formateringshint);
    }


    public void leggTilVerdi(String celleId, String navn, String verdi, String hjemmel) {
        Cell uttrykkCelle = opprettUttrykkCelle(celleId, navn, hjemmel);

        ExcelUttrykk excelVerdi = ExcelVerdi.parse(verdi);

        excelVerdi.skrivTilCelle(uttrykkCelle);
    }

    public void leggTilFunksjon(String celleId, String navn, String excelFormel, String hjemmel) {
        Cell uttrykkCelle = opprettUttrykkCelle(celleId, navn, hjemmel);

        ExcelFormel formel = new ExcelFormel(excelFormel);

        formel.skrivTilCelle(uttrykkCelle);
    }

    private Cell opprettUttrykkCelle(String celleId, String navn, String hjemmel) {
        int rad = gjeldendeRadNummer++;

        Row row = ark.createRow(rad);

        row.createCell(0).setCellValue(storForbokstav(navn));
        row.createCell(2).setCellValue(hjemmel);

        Cell uttrykkCelle = row.createCell(1);
        ExcelUtil.formaterCelleverdi(uttrykkCelle, formateringshint.finnFormat(navn));

        ExcelUtil.settCellenavn(ark, rad, 1, celleId);

        return uttrykkCelle;
    }


    public String navn() {
        return ark.getSheetName();
    }

    private static String storForbokstav(String navn) {
        return navn.substring(0, 1).toUpperCase() + navn.substring(1);
    }

}
