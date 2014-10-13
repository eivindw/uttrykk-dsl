package ske.fastsetting.skatt.beregn.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class IdUtil {

    private static final Pattern ID_PATTERN = Pattern.compile("<([^>]*)>");

    private IdUtil() {}

    public static String idLink(String id) {
        return "<" + id + ">";
    }

    public static Set<String> parseIder(String uttrykk) {
        final Set<String> ider = new HashSet<>();
        final Matcher matcher = ID_PATTERN.matcher(uttrykk);

        while(matcher.find()) {
            ider.add(matcher.group(1));
        }

        return ider;
    }
}
