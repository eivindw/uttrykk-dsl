package ske.fastsetting.skatt.uttrykk.util;

public final class RegelUtil {
    private RegelUtil() {}

    public static String formater(String regel) {
        if (regel!=null && !"".equals(regel)) {
            return " (" + regel + ")";
        } else {
            return "";
        }
    }
}
