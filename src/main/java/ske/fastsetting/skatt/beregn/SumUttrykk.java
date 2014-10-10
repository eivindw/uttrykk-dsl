package ske.fastsetting.skatt.beregn;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SumUttrykk extends AbstractUttrykk<Integer> {
    private final Uttrykk<Integer>[] uttrykk;

    @SafeVarargs
    public SumUttrykk(Uttrykk<Integer>... uttrykk) {
        this.uttrykk = uttrykk;
    }

    @Override
    public Integer eval() {
        return Stream.of(uttrykk)
            .mapToInt(Uttrykk::eval)
            .sum();
    }

    @Override
    public String beskrivUttrykk(UttrykkContext ctx) {
        return Stream.of(uttrykk)
            .map(u -> "<" + u.beskriv(ctx) + ">")
            .collect(Collectors.joining(",", "sum(", ")"));
    }

    @SafeVarargs
    public static SumUttrykk sum(Uttrykk<Integer>... uttrykk) {
        return new SumUttrykk(uttrykk);
    }
}
