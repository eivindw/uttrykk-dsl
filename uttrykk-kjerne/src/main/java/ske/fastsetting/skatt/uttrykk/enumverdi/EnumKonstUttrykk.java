package ske.fastsetting.skatt.uttrykk.enumverdi;

import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

/**
 * Created by jorn ola birkeland on 21.10.15.
 */
public class EnumKonstUttrykk<T extends Enum<T>> extends AbstractUttrykk<T,EnumKonstUttrykk<T>> implements EnumUttrykk<T> {

    private final T enumVerdi;

    public EnumKonstUttrykk(T enumVerdi) {
        this.enumVerdi = enumVerdi;
    }

    public static <T extends Enum<T>> EnumKonstUttrykk<T> valg(T enumVerdi) {
        return new EnumKonstUttrykk<>(enumVerdi);
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
