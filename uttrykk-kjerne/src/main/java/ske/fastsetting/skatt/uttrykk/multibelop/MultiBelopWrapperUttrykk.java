package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.WrapperUttrykk;

import java.util.Collection;
import java.util.stream.Collectors;

public class MultiBelopWrapperUttrykk<K> extends WrapperUttrykk<StedbundetBelop<K>, MultiBelopWrapperUttrykk<K>>
  implements MultiBelopUttrykk<K> {
    public static <K>MultiBelopWrapperUttrykk<K> pakkInn(Uttrykk<StedbundetBelop<K>> uttrykk) {
        return new MultiBelopWrapperUttrykk<>(uttrykk);
    }

    public static <T> Collection<MultiBelopUttrykk<T>> tilStedbundetBelopUttrykk
      (Collection<Uttrykk<StedbundetBelop<T>>> uttrykk) {
        return uttrykk.stream().map(MultiBelopWrapperUttrykk::tilStedbundetBelopUttrykk).collect(Collectors.toList());
    }

    public static <T> MultiBelopWrapperUttrykk<T> tilStedbundetBelopUttrykk(Uttrykk<StedbundetBelop<T>> uttrykk) {
        return new MultiBelopWrapperUttrykk<>(uttrykk);
    }

    public MultiBelopWrapperUttrykk(Uttrykk<StedbundetBelop<K>> uttrykk) {
        super(uttrykk);
    }

}
