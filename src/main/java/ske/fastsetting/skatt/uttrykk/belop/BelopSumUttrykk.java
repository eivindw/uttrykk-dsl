package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.SumUttrykk;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BelopSumUttrykk<C>
    extends SumUttrykk<Belop, BelopUttrykk<C>, BelopSumUttrykk<C>,C>
    implements BelopUttrykk<C>
{

    private BelopSumUttrykk(Collection<BelopUttrykk<C>> uttrykk) {
        super(uttrykk);
    }

    @Override
    protected Belop nullVerdi() {
        return Belop.NULL;
    }

    @SafeVarargs
    public static <C> BelopSumUttrykk<C> sum(BelopUttrykk<C>... uttrykk) {
        return new BelopSumUttrykk<>(Stream.of(uttrykk).collect(Collectors.toList()));
    }

    public static <C> BelopSumUttrykk<C> sum(Collection<BelopUttrykk<C>> uttrykk) {
        return new BelopSumUttrykk<>(uttrykk);
    }
}
