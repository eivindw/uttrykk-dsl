package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.SumOldUttrykk;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SumTallOldUttrykk extends SumOldUttrykk<Tall, TallOldUttrykk> implements TallOldUttrykk {
    private SumTallOldUttrykk(Collection<TallOldUttrykk> uttrykk) {
        super(uttrykk);
    }

    @SafeVarargs
    public static SumTallOldUttrykk sum(TallOldUttrykk... uttrykk) {
        return new SumTallOldUttrykk(Stream.of(uttrykk).collect(Collectors.toList()));
    }

    public static SumTallOldUttrykk sum(Collection<TallOldUttrykk> uttrykk) {
        return new SumTallOldUttrykk(uttrykk);
    }

    @Override
    protected Tall nullVerdi() {
        return Tall.ZERO;
    }

}
