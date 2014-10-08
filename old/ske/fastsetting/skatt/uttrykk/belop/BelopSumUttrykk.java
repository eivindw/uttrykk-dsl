package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.SumUttrykk;

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
