package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel;

import org.apache.poi.ss.usermodel.Cell;

/**
* Created by jorn ola birkeland on 11.01.15.
*/
class ExcelVerdi {

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
        ExcelUtil.formaterCelleverdi(celle, format);

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
