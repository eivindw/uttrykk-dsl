package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MinsteAvUttrykk extends AbstractUttrykk<Belop, MinsteAvUttrykk> implements BelopUttrykk {
    private final BelopUttrykk[] uttrykk;

    private MinsteAvUttrykk(BelopUttrykk[] uttrykk) {
        this.uttrykk = uttrykk;
    }

    public static MinsteAvUttrykk minsteAv(BelopUttrykk... uttrykk) {
        return new MinsteAvUttrykk(uttrykk);
    }

    @Override
    public Belop eval(UttrykkContext ctx) {
        return Stream.of(uttrykk)
          .map(ctx::eval)
          .min(Belop::sammenliknMed).get();
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return Stream.of(uttrykk)
          .map(ctx::beskriv)
          .collect(Collectors.joining(",", "minste av (", ")"));
    }
}
