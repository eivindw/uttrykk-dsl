package ske.fastsetting.skatt.uttrykk.enumverdi;

import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jorn ola birkeland on 21.10.15.
 */
public class EnumKonstUttrykk<T extends Enum<T>> extends AbstractUttrykk<T,EnumKonstUttrykk<T>> implements EnumUttrykk<T> {

    private final T enumVerdi;

    private static final Map cache = new HashMap();

    public EnumKonstUttrykk(T enumVerdi) {
        this.enumVerdi = enumVerdi;
    }

    public static <T extends Enum<T>> EnumKonstUttrykk<T> valg(T enumVerdi) {
        Map<T,EnumKonstUttrykk<T>> map = (Map<T,EnumKonstUttrykk<T>>)cache.computeIfAbsent(enumVerdi.getClass(),k->new HashMap<T,EnumKonstUttrykk<T>>());

        return map.computeIfAbsent(enumVerdi,k->new EnumKonstUttrykk<>(k));
    }

    @Override
    public T eval(UttrykkContext ctx) {
        return enumVerdi;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return enumVerdi.name();
    }
}
