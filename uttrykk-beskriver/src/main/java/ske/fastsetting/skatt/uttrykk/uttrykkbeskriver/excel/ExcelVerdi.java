package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel;

import org.apache.poi.ss.usermodel.Cell;

class ExcelVerdi implements ExcelUttrykk {

    private static final String TABELLNUMMER_REGEX = "Tabellnummer: (.*)";
    private static final String TABELLNUMMER_OUTPUT = "\"$1\"";


    private final Type type;
    private final Object value;

    protected enum Type {
        Prosent,
        Belop,
        Heltall,
        Kilometer,
        Tekst
    }

    public static ExcelUttrykk parse(String text) {
        if (text.startsWith("kr ")) {
            return new ExcelVerdi(Type.Belop, Double.parseDouble(text.replace("kr ", "").replace(" ", "").replace("," +
              "", ".")));
        } else if (text.endsWith("%")) {
            return new ExcelVerdi(Type.Prosent, Double.parseDouble(text.replace("%", "")) / 100);
        } else if (text.endsWith(" år")) {
            return new ExcelVerdi(Type.Heltall, Integer.parseInt(text.replace(" år", "")));
        } else if (text.endsWith(" km")) {
            return new ExcelVerdi(Type.Kilometer, Double.parseDouble(text.replace(" km", "")));
        } else if (text.matches("Hvis-uttrykk mangler en verdi for ellersBruk")) {
            return new ExcelVerdi(Type.Tekst, "\"" + text + "\"");
        } else if (text.matches(TABELLNUMMER_REGEX)) {
            return new ExcelVerdi(Type.Tekst, text.replaceAll(TABELLNUMMER_REGEX, TABELLNUMMER_OUTPUT));
        } else if (text.startsWith("Post 3.2.8 (inntektsfradragReiseutgifterReiseHjemArbeid)")) {
            return new ExcelVerdi(Type.Kilometer, 0d);
        } else if (text.startsWith("Post 3.2.9 (inntektsfradragReiseutgifterBesoeksreise)")) {
            return new ExcelVerdi(Type.Kilometer, 0d);
        } else if (text.startsWith("Post")) {
            return new ExcelVerdi(Type.Belop, 0d);
        } else if (text.startsWith("skattyters alder")) {
            return new ExcelVerdi(Type.Heltall, 34);
        } else if (text.startsWith("skattyters bostedkommune")) {
            return new ExcelVerdi(Type.Tekst, "Lørenskog");
        } else if (text.matches("\\{\\}")) {
            return new ExcelVerdi(Type.Heltall, 0);
        } else if (text.matches("-?\\d+")) {
            return new ExcelVerdi(Type.Heltall, Integer.parseInt(text));
        } else {
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
            ExcelUtil.formaterCelleverdi(celle, ExcelFormateringshint.BELOP_FORMATERING);
            celle.setCellValue(((double) value));
            break;
        case Prosent:
            ExcelUtil.formaterCelleverdi(celle, ExcelFormateringshint.PROSENT_FORMATERING);
            celle.setCellValue((double) value);
            break;
        case Kilometer:
            ExcelUtil.formaterCelleverdi(celle, ExcelFormateringshint.KILOMETER_FORMATERING);
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

    public String toString() {
        return type + " " + value;
    }
}
