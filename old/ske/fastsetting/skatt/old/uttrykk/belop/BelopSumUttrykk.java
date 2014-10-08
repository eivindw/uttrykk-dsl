package ske.fastsetting.skatt.old.uttrykk.belop;

import ske.fastsetting.skatt.old.domene.Belop;
import ske.fastsetting.skatt.old.uttrykk.SumUttrykk;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BelopSumUttrykk extends SumUttrykk<Belop, BelopUttrykk> implements BelopUttrykk {

    private BelopSumUttrykk(Collection<BelopUttrykk> uttrykk) {
        super(uttrykk);
    }

    public static BelopSumUttrykk sum(BelopUttrykk... uttrykk) {
        return new BelopSumUttrykk(Stream.of(uttrykk).collect(Collectors.toList()));
    }

    public static BelopSumUttrykk sum(Collection<BelopUttrykk> uttrykk) {
        return new BelopSumUttrykk(uttrykk);
    }

    @Override
    protected Belop nullVerdi() {
        return Belop.NULL;
    }

}
