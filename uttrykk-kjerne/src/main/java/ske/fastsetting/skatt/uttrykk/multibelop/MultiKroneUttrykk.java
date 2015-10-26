package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class MultiKroneUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>, MultiKroneUttrykk<K>>
  implements MultiBelopUttrykk<K> {

    private final StedbundetBelop<K> belop;

    private MultiKroneUttrykk(StedbundetBelop<K> belop) {

        this.belop = belop;
    }

    public static <K> MultiKroneUttrykk<K> kr(int belop, K sted) {
        return new MultiKroneUttrykk<>(StedbundetBelop.kr(belop, sted));
    }

    public static <K> MultiKroneUttrykk<K> kr0() {
        return new MultiKroneUttrykk<>(StedbundetBelop.kr0());
    }


    @Override
    public StedbundetBelop<K> eval(UttrykkContext ctx) {
        return belop;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return belop.toString();
    }
}
