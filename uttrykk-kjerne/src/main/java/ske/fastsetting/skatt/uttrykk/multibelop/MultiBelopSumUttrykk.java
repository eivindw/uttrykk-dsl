package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.SumUttrykk;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @deprecated Bruk MultiBelopPlussMinusUttrykk i stedet
 */
@Deprecated
public class MultiBelopSumUttrykk<K> extends SumUttrykk<MultiBelop<K>, MultiBelopUttrykk<K>,
  MultiBelopSumUttrykk<K>> implements MultiBelopUttrykk<K> {

    protected MultiBelopSumUttrykk(Collection<MultiBelopUttrykk<K>> uttrykk) {
        super(uttrykk);
    }

    @Override
    protected MultiBelop<K> nullVerdi() {
        return MultiBelop.kr0();
    }

    @SafeVarargs
    public static <K> MultiBelopSumUttrykk<K> sum(MultiBelopUttrykk<K>... multiBelopUttrykk) {
        return new MultiBelopSumUttrykk<>(Stream.of(multiBelopUttrykk).collect(Collectors.toList()));
    }

    public static <K> MultiBelopSumUttrykk<K> sum(Collection<MultiBelopUttrykk<K>> stedbundneBelopUttrykk) {
        return new MultiBelopSumUttrykk<>(stedbundneBelopUttrykk);
    }

    public MultiBelopSumUttrykk<K> pluss(MultiBelopSumUttrykk<K> sumUttrykk) {
        if (this.navn() == null && sumUttrykk.navn() == null) {
            return summer(sumUttrykk.uttrykk.stream());
        } else {
            return MultiBelopSumUttrykk.sum(this, sumUttrykk);
        }
    }

//    @Override
//    public MultiBelopSumUttrykk<K> pluss(MultiBelopUttrykk<K> uttrykk) {
//        if (this.navn() == null && uttrykk.navn() == null) {
//            return summer(Stream.of(uttrykk));
//        } else {
//            return MultiBelopSumUttrykk.sum(this, uttrykk);
//        }
//    }

    private MultiBelopSumUttrykk<K> summer(Stream<MultiBelopUttrykk<K>> stream) {
        return new MultiBelopSumUttrykk<>(Stream.concat(this.uttrykk.stream(), stream).collect
          (Collectors.toList()));
    }



}
