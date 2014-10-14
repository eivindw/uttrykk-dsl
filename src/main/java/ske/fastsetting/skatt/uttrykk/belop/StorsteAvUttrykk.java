package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.RegelUttrykk;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StorsteAvUttrykk extends RegelUttrykk<MinsteAvUttrykk, Belop> implements BelopUttrykk {
    private final BelopUttrykk[] uttrykk;

    private StorsteAvUttrykk(BelopUttrykk[] uttrykk) {
        this.uttrykk = uttrykk;
    }

    public static StorsteAvUttrykk storsteAv(BelopUttrykk... uttrykk) {
        return new StorsteAvUttrykk(uttrykk);
    }

    @Override
    public Belop eval(UttrykkContext ctx) {
        return Stream.of(uttrykk)
            .map(ctx::eval)
            .max(Belop::sammenliknMed).get();
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return Stream.of(uttrykk)
            .map(ctx::beskriv)
            .collect(Collectors.joining(",", "største av(", ")"));
    }
}
