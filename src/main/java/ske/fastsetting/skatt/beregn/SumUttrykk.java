package ske.fastsetting.skatt.beregn;

import ske.fastsetting.skatt.domene.KalkulerbarVerdi;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SumUttrykk<T extends KalkulerbarVerdi<T>> extends AbstractUttrykk<T> {
    private final List<Uttrykk<T>> uttrykk;

    private SumUttrykk(List<Uttrykk<T>> uttrykk) {
        this.uttrykk = uttrykk;
    }

    @Override
    public T eval(UttrykkContext ctx) {
        return uttrykk.stream()
            .map(ctx::eval)
            .reduce((verdi1, verdi2) -> verdi1.pluss(verdi2))
            .get();
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return uttrykk.stream()
            .map(ctx::beskriv)
            .collect(Collectors.joining(",", "sum(", ")"));
    }

    @SafeVarargs
    public static <T extends KalkulerbarVerdi<T>> SumUttrykk sum(Uttrykk<T>... uttrykk) {
        return sum(Arrays.asList(uttrykk));
    }

    public static SumUttrykk sum(List uttrykk) {
        return new SumUttrykk(uttrykk);
    }
}
