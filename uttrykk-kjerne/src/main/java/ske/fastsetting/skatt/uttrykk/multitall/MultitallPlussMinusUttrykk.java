package ske.fastsetting.skatt.uttrykk.multitall;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.stream.Stream;

import ske.fastsetting.skatt.domene.MultiTall;
import ske.fastsetting.skatt.uttrykk.PlussMinusUttrykk;

public class MultitallPlussMinusUttrykk<T>
    extends PlussMinusUttrykk<MultiTall<T>,MultiTallUttrykk<T>,MultitallPlussMinusUttrykk<T>>
    implements MultiTallUttrykk<T> {

    protected MultitallPlussMinusUttrykk(
      Collection<MultiTallUttrykk<T>> plussUttrykk,
      Collection<MultiTallUttrykk<T>> minusUttrykk) {
        super(plussUttrykk, minusUttrykk);
    }

    @Override
    protected MultiTall<T> nullVerdi() {
        return MultiTall.NULL();
    }

    @Override
    protected MultitallPlussMinusUttrykk<T> ny(Collection<MultiTallUttrykk<T>> plussUttrykk,
      Collection<MultiTallUttrykk<T>> minusUttrykk) {
        return new MultitallPlussMinusUttrykk<>(plussUttrykk,minusUttrykk);
    }

    public static <T> MultitallPlussMinusUttrykk<T> sum(MultiTallUttrykk<T> ledd1, MultiTallUttrykk<T> ledd2) {
        return new MultitallPlussMinusUttrykk<>(Stream.of(ledd1, ledd2).collect(toList()), emptyList());
    }
}
