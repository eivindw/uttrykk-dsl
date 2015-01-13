package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel;

import org.apache.poi.ss.usermodel.Cell;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jorn ola birkeland on 11.01.15.
 */
class ExcelVerdi implements ExcelUttrykk {

    private static final String SKATTYTERS_ALDER_REGEX = "skattyters alder er mellom (.*) og (.*) år";
    private static final String SKATTYTERS_BOSTEDKOMMUNE_REGEX = "bostedkommune er en av \\((.*)\\)";
    private static final String TABELLNUMMER_REGEX = "Tabellnummer: (.*)";
    private static final String TABELLNUMMER_OUTPUT = "\"$1\"";



    private final Type type;
    private final Object value;

    private enum Type {
        Prosent,
        Belop,
        Tekst,
    }

    public static ExcelUttrykk parse(String text) {
        if (text.startsWith("kr ")) {
            return new ExcelVerdi(Type.Belop, Long.parseLong(text.replace("kr ", "").replace(" ", "")));
        } else if (text.endsWith("%")) {
            return new ExcelVerdi(Type.Prosent, Double.parseDouble(text.replace("%", "")) / 100);
        } else if (text.matches(SKATTYTERS_ALDER_REGEX)) {
            return new ExcelFormel(text.replaceFirst(SKATTYTERS_ALDER_REGEX,"alder=median(alder,$1,$2)"));
        } else if (text.matches(SKATTYTERS_BOSTEDKOMMUNE_REGEX)) {
            Matcher matcher = Pattern.compile(SKATTYTERS_BOSTEDKOMMUNE_REGEX).matcher(text);
            matcher.find();
            String kommuneString = "\""+Stream.of(matcher.group(1).split(", ")).collect(Collectors.joining("\"&\""))+"\"";
            return new ExcelFormel("NOT(ISERROR(FIND(bostedkommune,"+kommuneString+")))");

        } else if(text.matches(TABELLNUMMER_REGEX)) {
            return new ExcelVerdi(Type.Tekst,text.replaceAll(TABELLNUMMER_REGEX,TABELLNUMMER_OUTPUT));
        } else if(text.matches("Hvis-uttrykk mangler en verdi for ellersBruk")) {
            return new ExcelVerdi(Type.Tekst,"\""+text+"\"");
        } else if(text.startsWith("Post")) {
            return new ExcelVerdi(Type.Belop,0L);
        }
        else {
            return new ExcelVerdi(Type.Tekst, text);
        }

    }

    private ExcelVerdi(Type type, Object value) {
        this.type = type;
        this.value = value;
    }

    public String tilTekst() {

        return value.toString();
    }

    public void skrivTilCelle(Cell celle) {

        switch (type) {
            case Belop:
                ExcelUtil.formaterCelleverdi(celle, "kr ###,###,###,##0");
                celle.setCellValue(((Long) value).doubleValue());
                break;
            case Prosent:
                ExcelUtil.formaterCelleverdi(celle, "0.00%");
                celle.setCellValue((double) value);
                break;
            default:
                celle.setCellValue(value.toString());
                break;
        }
    }

    public String toString() {
        return type + " " + value;
    }
}
