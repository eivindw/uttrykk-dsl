package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.SumOldUttrykk;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BelopSumOldUttrykk extends SumOldUttrykk<Belop, BelopOldUttrykk> implements BelopOldUttrykk {

    private BelopSumOldUttrykk(Collection<BelopOldUttrykk> uttrykk) {
        super(uttrykk);
    }

    public static BelopSumOldUttrykk sum(BelopOldUttrykk... uttrykk) {
        return new BelopSumOldUttrykk(Stream.of(uttrykk).collect(Collectors.toList()));
    }

    public static BelopSumOldUttrykk sum(Collection<BelopOldUttrykk> uttrykk) {
        return new BelopSumOldUttrykk(uttrykk);
    }

    @Override
    protected Belop nullVerdi() {
        return Belop.NULL;
    }

}
