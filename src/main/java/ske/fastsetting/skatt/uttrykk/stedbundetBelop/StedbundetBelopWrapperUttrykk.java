package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

import java.util.Collection;
import java.util.stream.Collectors;

/**
<<<<<<< HEAD:src/main/java/ske/fastsetting/skatt/uttrykk/stedbundetBelop/TilStedbundetBelopUttrykk.java
 * Created by jorn ola birkeland on 21.03.15.
 */
class StedbundetBelopWrapperUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>, StedbundetBelopWrapperUttrykk<K>>
  implements StedbundetBelopUttrykk<K> {
    private Uttrykk<StedbundetBelop<K>> uttrykk;

    public static <T> Collection<StedbundetBelopUttrykk<T>> tilStedbundetBelopUttrykk
      (Collection<Uttrykk<StedbundetBelop<T>>> uttrykk) {
        return uttrykk.stream().map(StedbundetBelopWrapperUttrykk::tilStedbundetBelopUttrykk).collect(Collectors.toList());
    }

    public static <T> StedbundetBelopWrapperUttrykk<T> tilStedbundetBelopUttrykk(Uttrykk<StedbundetBelop<T>> uttrykk) {
        return new StedbundetBelopWrapperUttrykk<>(uttrykk);
    }

    public StedbundetBelopWrapperUttrykk(Uttrykk<StedbundetBelop<K>> uttrykk) {
        this.uttrykk = uttrykk;
    }

    @Override
    public StedbundetBelop<K> eval(UttrykkContext ctx) {
        return uttrykk.eval(ctx);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return uttrykk.beskriv(ctx);
    }
}
