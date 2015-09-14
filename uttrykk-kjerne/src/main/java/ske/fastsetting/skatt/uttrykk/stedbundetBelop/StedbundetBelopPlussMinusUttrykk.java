package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.PlussMinusUttrykk;

import java.util.Collection;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * Created by jorn ola birkeland on 31.05.15.
 */
public class StedbundetBelopPlussMinusUttrykk<K>
        extends PlussMinusUttrykk<StedbundetBelop<K>,StedbundetBelopUttrykk<K>,StedbundetBelopPlussMinusUttrykk<K>>
        implements StedbundetBelopUttrykk<K> {

    @SafeVarargs
    public static <K> StedbundetBelopPlussMinusUttrykk<K> sum(StedbundetBelopUttrykk<K>... stedbundetBelopUttrykk) {
        return new StedbundetBelopPlussMinusUttrykk<>(Stream.of(stedbundetBelopUttrykk).collect(toList()),emptyList());
    }

    public static <K> StedbundetBelopPlussMinusUttrykk<K> sum(Collection<StedbundetBelopUttrykk<K>> stedbundneBelopUttrykk) {
        return new StedbundetBelopPlussMinusUttrykk<>(stedbundneBelopUttrykk,emptyList());
    }

    public static <K> StedbundetBelopPlussMinusUttrykk<K> sum(StedbundetBelopUttrykk<K> ledd1, StedbundetBelopUttrykk<K> ledd2) {
        return new StedbundetBelopPlussMinusUttrykk<>(Stream.of(ledd1, ledd2).collect(toList()), emptyList());
    }

    public static <K> StedbundetBelopPlussMinusUttrykk<K> diff(StedbundetBelopUttrykk<K> ledd1, StedbundetBelopUttrykk<K> ledd2) {
        return new StedbundetBelopPlussMinusUttrykk<K>(Stream.of(ledd1).collect(toList()), Stream.of(ledd2).collect(toList()));
    }


    protected StedbundetBelopPlussMinusUttrykk(Collection<StedbundetBelopUttrykk<K>> plussUttrykk, Collection<StedbundetBelopUttrykk<K>> minusUttrykk) {
        super(plussUttrykk, minusUttrykk);
    }

    @Override
    protected StedbundetBelop<K> nullVerdi() {
        return StedbundetBelop.kr0();
    }

    @Override
    protected StedbundetBelopPlussMinusUttrykk<K> ny(Collection<StedbundetBelopUttrykk<K>> plussUttrykk, Collection<StedbundetBelopUttrykk<K>> minusUttrykk) {
        return new StedbundetBelopPlussMinusUttrykk<>(plussUttrykk,minusUttrykk);
    }
}
