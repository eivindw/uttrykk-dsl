package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.PlussMinusUttrykk;

import java.util.Collection;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * Created by jorn ola birkeland on 31.05.15.
 */
public class MultiBelopPlussMinusUttrykk<K>
        extends PlussMinusUttrykk<MultiBelop<K>,MultiBelopUttrykk<K>,MultiBelopPlussMinusUttrykk<K>>
        implements MultiBelopUttrykk<K> {

    @SafeVarargs
    public static <K> MultiBelopPlussMinusUttrykk<K> sum(MultiBelopUttrykk<K>... multiBelopUttrykk) {
        return new MultiBelopPlussMinusUttrykk<>(Stream.of(multiBelopUttrykk).collect(toList()),emptyList());
    }

    public static <K> MultiBelopPlussMinusUttrykk<K> sum(Collection<MultiBelopUttrykk<K>> stedbundneBelopUttrykk) {
        return new MultiBelopPlussMinusUttrykk<>(stedbundneBelopUttrykk,emptyList());
    }

    public static <K> MultiBelopPlussMinusUttrykk<K> sum(MultiBelopUttrykk<K> ledd1, MultiBelopUttrykk<K> ledd2) {
        return new MultiBelopPlussMinusUttrykk<>(Stream.of(ledd1, ledd2).collect(toList()), emptyList());
    }

    public static <K> MultiBelopPlussMinusUttrykk<K> diff(MultiBelopUttrykk<K> ledd1, MultiBelopUttrykk<K> ledd2) {
        return new MultiBelopPlussMinusUttrykk<K>(Stream.of(ledd1).collect(toList()), Stream.of(ledd2).collect(toList()));
    }


    protected MultiBelopPlussMinusUttrykk(Collection<MultiBelopUttrykk<K>> plussUttrykk,
      Collection<MultiBelopUttrykk<K>> minusUttrykk) {
        super(plussUttrykk, minusUttrykk);
    }

    @Override
    protected MultiBelop<K> nullVerdi() {
        return MultiBelop.kr0();
    }

    @Override
    protected MultiBelopPlussMinusUttrykk<K> ny(Collection<MultiBelopUttrykk<K>> plussUttrykk, Collection<MultiBelopUttrykk<K>> minusUttrykk) {
        return new MultiBelopPlussMinusUttrykk<>(plussUttrykk,minusUttrykk);
    }
}
