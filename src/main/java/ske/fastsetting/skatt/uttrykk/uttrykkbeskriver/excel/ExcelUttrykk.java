package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel;

import org.apache.poi.ss.usermodel.Cell;

public class ExcelUttrykk {

    protected enum Type {
        Prosent,
        Belop,
        Heltall,
        Tekst,
        Formel,
    }


    private final Object value;
    private final Type type;

    private ExcelUttrykk(Object value, Type type) {
        this.value = value;
        this.type = type;
    }

    public static ExcelUttrykk formel(String formel) {
        return new ExcelUttrykk(formel,Type.Formel);
    }

    public static ExcelUttrykk belop(long belop) {
        return new ExcelUttrykk(belop,Type.Belop);
    }

    public static ExcelUttrykk prosent(double prosent) {
        return new ExcelUttrykk(prosent,Type.Prosent);
    }

    public static ExcelUttrykk heltall(int heltall) {
        return new ExcelUttrykk(heltall,Type.Heltall);
    }

    public static ExcelUttrykk tekst(String tekst) {
        return new ExcelUttrykk(tekst,Type.Tekst);
    }


    void skrivTilCelle(Cell celle) {
        switch (type) {
            case Formel:
                try {
                    celle.setCellFormula((String)value);
                } catch (Throwable e) {
                    celle.setCellValue((String)value);
                }
                break;
            case Belop:
                ExcelUtil.formaterCelleverdi(celle, ExcelFormateringshint.BELOP_FORMATERING);
                celle.setCellValue(((Long) value).doubleValue());
                break;
            case Prosent:
                ExcelUtil.formaterCelleverdi(celle, ExcelFormateringshint.PROSENT_FORMATERING);
                celle.setCellValue((double) value);
                break;
            case Heltall:
                ExcelUtil.formaterCelleverdi(celle, ExcelFormateringshint.HELTALL_FORMATERING);
                celle.setCellValue((int) value);
                break;
            default:
                celle.setCellValue(value.toString());
                break;

        }
    }

    public String tilTekst() {
        return value.toString();
    }

    public String toString() {
        return type + " " + value;
    }


}
