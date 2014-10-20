package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StorsteAvUttrykk<C> extends AbstractUttrykk<Belop, StorsteAvUttrykk<C>,C> implements BelopUttrykk<C> {
    private final BelopUttrykk<C>[] uttrykk;

    private StorsteAvUttrykk(BelopUttrykk<C>[] uttrykk) {
        this.uttrykk = uttrykk;
    }

    public static <C> StorsteAvUttrykk storsteAv(BelopUttrykk<C>... uttrykk) {
        return new StorsteAvUttrykk<C>(uttrykk);
    }

    @Override
    public Belop eval(UttrykkContext<C> ctx) {
        return Stream.of(uttrykk)
            .map(ctx::eval)
            .max(Belop::sammenliknMed).get();
    }

    @Override
    public String beskriv(UttrykkContext<C> ctx) {
        return Stream.of(uttrykk)
            .map(ctx::beskriv)
            .collect(Collectors.joining(",", "største av(", ")"));
    }
}
