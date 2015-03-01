package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.SumUttrykk;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
* Created by jorn ola birkeland on 24.02.15.
*/
public class StedbundetBelopSumUttrykk<K> extends SumUttrykk<StedbundetBelop<K>,StedbundetBelopUttrykk<K>,StedbundetBelopSumUttrykk<K>> implements StedbundetBelopUttrykk<K>{

    protected StedbundetBelopSumUttrykk(Collection<StedbundetBelopUttrykk<K>> uttrykk) {
        super(uttrykk);
    }

    @Override
    protected StedbundetBelop<K> nullVerdi() {
        return StedbundetBelop.kr0();
    }

    @SafeVarargs
    public static <K> StedbundetBelopSumUttrykk<K> sum(StedbundetBelopUttrykk<K>... stedbundetBelopUttrykk) {
        return new StedbundetBelopSumUttrykk<K>(Stream.of(stedbundetBelopUttrykk).collect(Collectors.toList()));
    }

    public static <K> StedbundetBelopSumUttrykk<K> sum(Collection<StedbundetBelopUttrykk<K>> stedbundneBelopUttrykk) {
        return new StedbundetBelopSumUttrykk<>(stedbundneBelopUttrykk);
    }

}
