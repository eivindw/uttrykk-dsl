package ske.fastsetting.skatt.beregn;

import ske.fastsetting.skatt.domene.KalkulerbarVerdi;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SumUttrykk<T extends KalkulerbarVerdi<T>> extends AbstractUttrykk<T> {
    private final Uttrykk<T>[] uttrykk;

    @SafeVarargs
    public SumUttrykk(Uttrykk<T>... uttrykk) {
        this.uttrykk = uttrykk;
    }

    @Override
    public T eval(UttrykkContext ctx) {
        return Stream.of(uttrykk)
            .map(ctx::eval)
            .reduce((verdi1, verdi2) -> verdi1.pluss(verdi2))
            .get();
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return Stream.of(uttrykk)
            .map(ctx::beskriv)
            .collect(Collectors.joining(",", "sum(", ")"));
    }

    @SafeVarargs
    public static <T extends KalkulerbarVerdi<T>> SumUttrykk sum(Uttrykk<T>... uttrykk) {
        return new SumUttrykk<>(uttrykk);
    }
}
