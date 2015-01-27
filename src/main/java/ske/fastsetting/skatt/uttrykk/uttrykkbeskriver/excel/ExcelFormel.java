package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel;

import org.apache.poi.ss.usermodel.Cell;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jorn ola birkeland on 11.01.15.
 */
public class ExcelFormel implements ExcelUttrykk {

    private final String formel;

    public static final String SUM_MATCH = "^Summen av (.*)$";
    public static final String SUM_OUTPUT = "sum($1)";

    public static final String BEGRENSET_NEDAD_OPPAD_MATCH = "^(.*) begrenset nedad til (.*) begrenset oppad til (.*)$";
    public static final String BEGRENSET_NEDAD_OPPAD_OUTPUT = "MIN(MAX($1,$2),$3)";

    public static final String BEGRENSET_NEDAD_MATCH = "^(.*) begrenset nedad til (.*)$";
    public static final String BEGRENSET_NEDAD_OUTPUT = "MAX($1,$2)";

    public static final String BEGRENSET_OPPAD_MATCH = "^(.*) begrenset oppad til (.*)$";
    public static final String BEGRENSET_OPPAD_OUTPUT = "MIN($1,$2)";

    public static final String MINSTE_AV_MATCH = "^minste av \\((.*)\\)$";
    public static final String MINSTE_AV_OUTPUT = "MIN($1)";

    public static final String STOERSTE_AV_MATCH = "^største av \\((.*)\\)$";
    public static final String STOERSTE_AV_OUTPUT = "MAX($1)";

    public static final String FRA_TILOGMED_MATCH = "^(.*?) < (.*?) <= (.*?)$";
    public static final String FRA_TILOGMED_OUTPUT = "AND(($1<$2),($2<=$3))";

    public static final String FRAOGMED_TIL_MATCH = "^(.*?) <= (.*?) < (.*?)$";
    public static final String FRAOGMED_TIL_OUTPUT = "AND(($1<=$2),($2<$3))";

    public static final String FRA_TIL_ÅR_MATCH = "^(.*?) < (.*?) < (.*?)$";
    public static final String FRA_TIL_ÅR_OUTPUT = "AND(($1<$2),($2<$3))";

    public static final String OG_MATCH = "^(.*) og (.*)$";
    public static final String OG_OUTPUT = "AND($1,$2)";

    private static final String ER_EN_AV_REGEX = "^(.*) er en av \\((.*)\\)$";


    public ExcelFormel(String uttrykkStreng) {

        this.formel = uttrykkStreng;
    }

    public static ExcelFormel parse(String uttrykkStreng) {

        uttrykkStreng = HvisParser.parse(uttrykkStreng);
        uttrykkStreng = uttrykkStreng.replaceAll(SUM_MATCH, SUM_OUTPUT);
        uttrykkStreng = uttrykkStreng.replaceAll(BEGRENSET_NEDAD_OPPAD_MATCH, BEGRENSET_NEDAD_OPPAD_OUTPUT);
        uttrykkStreng = uttrykkStreng.replaceAll(BEGRENSET_NEDAD_MATCH, BEGRENSET_NEDAD_OUTPUT);
        uttrykkStreng = uttrykkStreng.replaceAll(BEGRENSET_OPPAD_MATCH, BEGRENSET_OPPAD_OUTPUT);
        uttrykkStreng = uttrykkStreng.replaceAll(MINSTE_AV_MATCH, MINSTE_AV_OUTPUT);
        uttrykkStreng = uttrykkStreng.replaceAll(STOERSTE_AV_MATCH, STOERSTE_AV_OUTPUT);
        uttrykkStreng = uttrykkStreng.replaceAll(FRA_TIL_ÅR_MATCH, FRA_TIL_ÅR_OUTPUT);
        uttrykkStreng = uttrykkStreng.replaceAll(FRA_TILOGMED_MATCH, FRA_TILOGMED_OUTPUT);
        uttrykkStreng = uttrykkStreng.replaceAll(FRAOGMED_TIL_MATCH, FRAOGMED_TIL_OUTPUT);
        uttrykkStreng = uttrykkStreng.replaceAll(OG_MATCH, OG_OUTPUT);

        if (uttrykkStreng.matches(ER_EN_AV_REGEX)) {
            Matcher matcher = Pattern.compile(ER_EN_AV_REGEX).matcher(uttrykkStreng);
            matcher.find();
            String liste = "\"" + Stream.of(matcher.group(2).split(", ")).collect(Collectors.joining("\"&\"")) + "\"";
            uttrykkStreng = "NOT(ISERROR(FIND(" + matcher.group(1) + "," + liste + ")))";
        }

        return new ExcelFormel(uttrykkStreng);
    }


    public void skrivTilCelle(Cell celle) {
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


    public static class HvisParser {

        private static Pattern HVIS_PATTERN = Pattern.compile("^hvis (.+?) bruk da (.+?)(?: ellers(?: bruk)? (.*))?$");

        public static String parse(String uttrykk) {
            Matcher m = HVIS_PATTERN.matcher(uttrykk);

            if (m.find())
                if (m.group(3) == null)
                    return String.format("if(%s,%s)", m.group(1), m.group(2));
                else
                    return String.format("if(%s,%s,%s)", m.group(1), m.group(2), parse(m.group(3)));
            else
                return uttrykk;
        }
    }

}
