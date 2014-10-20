package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.SumUttrykk;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SumTallUttrykk<C>
    extends SumUttrykk<Tall, TallUttrykk<?,C>, SumTallUttrykk<C>, C>
    implements TallUttrykk<SumTallUttrykk<C>,C>
{
    private SumTallUttrykk(Collection<TallUttrykk<?,C>> uttrykk) {
        super(uttrykk);
    }

    @Override
    protected Tall nullVerdi() {
        return Tall.ZERO;
    }

    public static <C> SumTallUttrykk sum(TallUttrykk<?,C>... uttrykk) {
        return new SumTallUttrykk<C>(Stream.of(uttrykk).collect(Collectors.toList()));
    }

    public static <C> SumTallUttrykk sum(Collection<TallUttrykk<?,C>> uttrykk) {
        return new SumTallUttrykk(uttrykk);
    }
}
