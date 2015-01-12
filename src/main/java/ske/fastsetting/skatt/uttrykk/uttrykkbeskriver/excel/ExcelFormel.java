package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Created by jorn ola birkeland on 11.01.15.
 */
public class ExcelFormel implements ExcelUttrykk {

    private final String formel;
    public static final String HVIS_MATCH = "^hvis (.*) bruk da (.*) ellers bruk (.*)$";
    public static final String HVIS_OUTPUT = "if($1,$2,$3)";

    public static final String SUM_MATCH = "^Summen av (.*)$";
    public static final String SUM_OUTPUT = "sum($1)";

    public static final String BEGRENSET_NEDAD_MATCH = "^(.*) begrenset nedad til (.*)$";
    public static final String BEGRENSET_NEDAD_OUTPUT = "max($1,$2)";

    public static final String BEGRENSET_OPPAD_MATCH = "^(.*) begrenset oppad til (.*)$";
    public static final String BEGRENSET_OPPAD_OUTPUT = "min($1,$2)";

    public static final String MINSTE_AV_MATCH = "^minste av (.*)$";
    public static final String MINSTE_AV_OUTPUT = "min $1";

    public static final String STOERSTE_AV_MATCH = "^største av (.*)$";
    public static final String STOERSTE_AV_OUTPUT = "max($1)";


    public ExcelFormel(String uttrykkStreng) {

        this.formel = uttrykkStreng;
    }

    public static ExcelFormel parse(String uttrykkStreng) {

        uttrykkStreng = uttrykkStreng.replaceAll(HVIS_MATCH, HVIS_OUTPUT);
        uttrykkStreng = uttrykkStreng.replaceAll(SUM_MATCH, SUM_OUTPUT);
        uttrykkStreng = uttrykkStreng.replaceAll(BEGRENSET_NEDAD_MATCH, BEGRENSET_NEDAD_OUTPUT);
        uttrykkStreng = uttrykkStreng.replaceAll(BEGRENSET_OPPAD_MATCH, BEGRENSET_OPPAD_OUTPUT);
        uttrykkStreng = uttrykkStreng.replaceAll(MINSTE_AV_MATCH, MINSTE_AV_OUTPUT);
        uttrykkStreng = uttrykkStreng.replaceAll(STOERSTE_AV_MATCH, STOERSTE_AV_OUTPUT);

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

    @Override
    public String tilTekst() {
        return formel;
    }

    public String toString() {
        return formel;
    }

}
