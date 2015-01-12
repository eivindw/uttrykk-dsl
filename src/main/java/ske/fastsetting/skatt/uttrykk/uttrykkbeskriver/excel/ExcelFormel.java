package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Created by jorn ola birkeland on 11.01.15.
 */
public class ExcelFormel {

    private final String formel;
    public static final String HVIS_MATCH = "^hvis (.*) bruk da (.*) ellers bruk (.*)$";
    public static final String HVIS_OUTPUT = "if($1,$2,$3)";

    public ExcelFormel(String uttrykkStreng) {

        this.formel = uttrykkStreng;
    }

    public static ExcelFormel parse(String uttrykkStreng) {

        uttrykkStreng = uttrykkStreng.replaceAll(HVIS_MATCH, HVIS_OUTPUT);

        return new ExcelFormel(uttrykkStreng);
    }

    public void skrivTilCelle(Cell celle) {
        ExcelUtil.formaterCelleverdiSomKroner(celle);

        try {
            celle.setCellFormula(formel);
        } catch (Throwable e) {
            celle.setCellValue(formel);
        }
    }

    public String toString() {
        return formel;
    }

}
