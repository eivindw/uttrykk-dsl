package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.excel;

import java.util.HashMap;
import java.util.Map;

public class ExcelFormateringshint {
    public static final String BELOP_FORMATERING = "kr ###,###,###,##0";
    public static final String PROSENT_FORMATERING = "0.00%";
    public static final String HELTALL_FORMATERING = "##0";
    public static final String KILOMETER_FORMATERING = "###,###,###,##0\" km\"";
    private final Map<String, String> patternFormatMap = new HashMap<>();
    private String standardFormat = "";

    public void leggTilHint(String navnPattern, String excelFormat) {
        patternFormatMap.put(navnPattern, excelFormat);
    }

    public void settStandardFormatering(String excelFormat) {
        standardFormat = excelFormat;
    }

    public String finnFormat(String navn) {
        for (String pattern : patternFormatMap.keySet()) {
            if (navn.matches(pattern)) {
                return patternFormatMap.get(pattern);
            }
        }

        return standardFormat;
    }

}
