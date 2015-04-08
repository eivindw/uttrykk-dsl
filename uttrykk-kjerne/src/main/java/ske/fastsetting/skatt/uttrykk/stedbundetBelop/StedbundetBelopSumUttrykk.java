package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.SumUttrykk;

public class StedbundetBelopSumUttrykk<K> extends SumUttrykk<StedbundetBelop<K>, StedbundetBelopUttrykk<K>,
  StedbundetBelopSumUttrykk<K>> implements StedbundetBelopUttrykk<K> {

    protected StedbundetBelopSumUttrykk(Collection<StedbundetBelopUttrykk<K>> uttrykk) {
        super(uttrykk);
    }

    @Override
    protected StedbundetBelop<K> nullVerdi() {
        return StedbundetBelop.kr0();
    }

    @SafeVarargs
    public static <K> StedbundetBelopSumUttrykk<K> sum(StedbundetBelopUttrykk<K>... stedbundetBelopUttrykk) {
        return new StedbundetBelopSumUttrykk<>(Stream.of(stedbundetBelopUttrykk).collect(Collectors.toList()));
    }

    public static <K> StedbundetBelopSumUttrykk<K> sum(Collection<StedbundetBelopUttrykk<K>> stedbundneBelopUttrykk) {
        return new StedbundetBelopSumUttrykk<>(stedbundneBelopUttrykk);
    }

    public StedbundetBelopSumUttrykk<K> pluss(StedbundetBelopSumUttrykk<K> sumUttrykk) {
        if (this.navn() == null && sumUttrykk.navn() == null) {
            return summer(sumUttrykk.uttrykk.stream());
        } else {
            return StedbundetBelopSumUttrykk.sum(this, sumUttrykk);
        }
    }

    @Override
    public StedbundetBelopSumUttrykk<K> pluss(StedbundetBelopUttrykk<K> uttrykk) {
        if (this.navn() == null && uttrykk.navn() == null) {
            return summer(Stream.of(uttrykk));
        } else {
            return StedbundetBelopSumUttrykk.sum(this, uttrykk);
        }
    }

    private StedbundetBelopSumUttrykk<K> summer(Stream<StedbundetBelopUttrykk<K>> stream) {
        return new StedbundetBelopSumUttrykk<>(Stream.concat(this.uttrykk.stream(), stream).collect
          (Collectors.toList()));
    }



}
