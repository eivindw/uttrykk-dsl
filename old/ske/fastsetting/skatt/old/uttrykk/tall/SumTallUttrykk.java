package ske.fastsetting.skatt.old.uttrykk.tall;

import ske.fastsetting.skatt.old.domene.Tall;
import ske.fastsetting.skatt.old.uttrykk.SumUttrykk;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SumTallUttrykk extends SumUttrykk<Tall, TallUttrykk> implements TallUttrykk {
    private SumTallUttrykk(Collection<TallUttrykk> uttrykk) {
        super(uttrykk);
    }

    @SafeVarargs
    public static SumTallUttrykk sum(TallUttrykk... uttrykk) {
        return new SumTallUttrykk(Stream.of(uttrykk).collect(Collectors.toList()));
    }

    public static SumTallUttrykk sum(Collection<TallUttrykk> uttrykk) {
        return new SumTallUttrykk(uttrykk);
    }

    @Override
    protected Tall nullVerdi() {
        return Tall.ZERO;
    }

}
