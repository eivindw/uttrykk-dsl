package ske.fastsetting.skatt.uttrykk.util;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class IdUtil {

    private static final String LINK_START = "<";
    private static final String LINK_END = ">";
    private static final Pattern ID_PATTERN =
        Pattern.compile(String.format("%1$s([_A-Za-z0-9]*)%2$s", LINK_START, LINK_END));

    private IdUtil() {}

    public static String idLink(String id) {
        return LINK_START + id + LINK_END;
    }

    public static Set<String> parseIder(String uttrykk) {
        final Set<String> ider = new LinkedHashSet<>();
        if(uttrykk != null) {
            final Matcher matcher = ID_PATTERN.matcher(uttrykk);

            while (matcher.find()) {
                ider.add(matcher.group(1));
            }
        }
        return ider;
    }

    public static String lagTilfeldigId() {
        // TODO - denne er ikke garantert unik - for korthet - kom opp med noe bedre :)
        return Integer.toHexString(UUID.randomUUID().hashCode());
    }
}
