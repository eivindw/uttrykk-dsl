package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.WrapperUttrykk;

import java.util.Collection;
import java.util.stream.Collectors;

public class MultiBelopWrapperUttrykk<K> extends WrapperUttrykk<MultiBelop<K>, MultiBelopWrapperUttrykk<K>>
  implements MultiBelopUttrykk<K> {
    public static <K>MultiBelopWrapperUttrykk<K> pakkInn(Uttrykk<MultiBelop<K>> uttrykk) {
        return new MultiBelopWrapperUttrykk<>(uttrykk);
    }

    public static <T> Collection<MultiBelopUttrykk<T>> tilStedbundetBelopUttrykk
      (Collection<Uttrykk<MultiBelop<T>>> uttrykk) {
        return uttrykk.stream().map(MultiBelopWrapperUttrykk::tilStedbundetBelopUttrykk).collect(Collectors.toList());
    }

    public static <T> MultiBelopWrapperUttrykk<T> tilStedbundetBelopUttrykk(Uttrykk<MultiBelop<T>> uttrykk) {
        return new MultiBelopWrapperUttrykk<>(uttrykk);
    }

    public MultiBelopWrapperUttrykk(Uttrykk<MultiBelop<K>> uttrykk) {
        super(uttrykk);
    }

}
