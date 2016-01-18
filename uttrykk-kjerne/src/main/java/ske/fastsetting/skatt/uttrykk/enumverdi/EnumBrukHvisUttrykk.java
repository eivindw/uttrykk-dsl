package ske.fastsetting.skatt.uttrykk.enumverdi;

import ske.fastsetting.skatt.uttrykk.BrukHvisUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;

public class EnumBrukHvisUttrykk<T extends Enum<T>> extends BrukHvisUttrykk<T,EnumBrukHvisUttrykk<T>> implements EnumUttrykk<T> {
    public EnumBrukHvisUttrykk(Uttrykk<T> uttrykk) {
        super(uttrykk);
    }

    public static <T extends Enum<T>> EnumBrukHvisUttrykk<T> bruk(Uttrykk<T> uttrykk) {
        return new EnumBrukHvisUttrykk<>(uttrykk);
    }
}