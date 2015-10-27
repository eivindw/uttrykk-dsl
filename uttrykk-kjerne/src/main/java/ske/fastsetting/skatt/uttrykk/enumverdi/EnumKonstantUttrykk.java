package ske.fastsetting.skatt.uttrykk.enumverdi;

import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class EnumKonstantUttrykk<T extends Enum<T>> extends AbstractUttrykk<T,EnumKonstantUttrykk<T>> implements EnumUttrykk<T> {

    private final T enumVerdi;

    public EnumKonstantUttrykk(T enumVerdi) {
        this.enumVerdi = enumVerdi;
    }

    public static <T extends Enum<T>> EnumKonstantUttrykk<T> valg(T enumVerdi) {
        return new EnumKonstantUttrykk<>(enumVerdi);
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
