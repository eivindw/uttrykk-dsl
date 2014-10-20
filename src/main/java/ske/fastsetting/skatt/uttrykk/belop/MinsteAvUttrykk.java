package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MinsteAvUttrykk<C> extends AbstractUttrykk<Belop, MinsteAvUttrykk<C>, C> implements BelopUttrykk<MinsteAvUttrykk<C>,C> {
    private final BelopUttrykk<?,C>[] uttrykk;

    private MinsteAvUttrykk(BelopUttrykk<?,C>[] uttrykk) {
        this.uttrykk = uttrykk;
    }

    public static <C> MinsteAvUttrykk minsteAv(BelopUttrykk<?,C>... uttrykk) {
        return new MinsteAvUttrykk<C>(uttrykk);
    }

    @Override
    public Belop eval(UttrykkContext<C> ctx) {
        return Stream.of(uttrykk)
            .map(ctx::eval)
            .min(Belop::sammenliknMed).get();
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return Stream.of(uttrykk)
            .map(ctx::beskriv)
            .collect(Collectors.joining(",", "minste av(", ")"));
    }
}
