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
    public Integer eval(UttrykkContext ctx) {
        return Stream.of(uttrykk)
            .mapToInt(u -> (int) ctx.eval(u))
            .sum();
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return Stream.of(uttrykk)
            .map(ctx::beskriv)
            .collect(Collectors.joining(",", "sum(", ")"));
    }

    @SafeVarargs
    public static SumUttrykk sum(Uttrykk<Integer>... uttrykk) {
        return new SumUttrykk(uttrykk);
    }
}
