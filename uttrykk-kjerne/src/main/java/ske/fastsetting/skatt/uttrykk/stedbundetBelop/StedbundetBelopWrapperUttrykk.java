package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.WrapperUttrykk;

import java.util.Collection;
import java.util.stream.Collectors;

public class StedbundetBelopWrapperUttrykk<K> extends WrapperUttrykk<StedbundetBelop<K>, StedbundetBelopWrapperUttrykk<K>>
  implements StedbundetBelopUttrykk<K> {
    public static <K>StedbundetBelopWrapperUttrykk<K> pakkInn(Uttrykk<StedbundetBelop<K>> uttrykk) {
        return new StedbundetBelopWrapperUttrykk<>(uttrykk);
    }

    public static <T> Collection<StedbundetBelopUttrykk<T>> tilStedbundetBelopUttrykk
      (Collection<Uttrykk<StedbundetBelop<T>>> uttrykk) {
        return uttrykk.stream().map(StedbundetBelopWrapperUttrykk::tilStedbundetBelopUttrykk).collect(Collectors.toList());
    }

    public static <T> StedbundetBelopWrapperUttrykk<T> tilStedbundetBelopUttrykk(Uttrykk<StedbundetBelop<T>> uttrykk) {
        return new StedbundetBelopWrapperUttrykk<>(uttrykk);
    }

    public StedbundetBelopWrapperUttrykk(Uttrykk<StedbundetBelop<K>> uttrykk) {
        super(uttrykk);
    }

}
