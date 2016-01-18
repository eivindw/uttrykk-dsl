package ske.fastsetting.skatt.uttrykk;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

public abstract class StorsteAvUttrykk<T extends Comparable<T>,B extends StorsteAvUttrykk<T,B>> extends AbstractUttrykk<T,B> {
    private final CompareableUttrykk<T>[] uttrykk;

    protected StorsteAvUttrykk(CompareableUttrykk<T>[] uttrykk) {
        this.uttrykk = uttrykk;
    }


    @Override
    public T eval(UttrykkContext ctx) {
        return Stream.of(uttrykk)
          .map(ctx::eval)
          .max((a, b) -> a.compareTo(b)).get();
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return Stream.of(uttrykk)
          .map(ctx::beskriv)
          .collect(Collectors.joining(",", "største av (", ")"));
    }
}