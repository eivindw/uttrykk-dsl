package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class StedbundetKroneUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>,StedbundetKroneUttrykk<K>> implements StedbundetBelopUttrykk<K>
{

    private final StedbundetBelop<K> belop;

    private StedbundetKroneUttrykk(StedbundetBelop<K> belop) {

        this.belop = belop;
    }

    public static <K> StedbundetKroneUttrykk<K> kr(int belop, K sted) {
        return new StedbundetKroneUttrykk<>(StedbundetBelop.kr(belop,sted));
    }

    public static <K> StedbundetKroneUttrykk<K> kr0() {
        return new StedbundetKroneUttrykk<>(StedbundetBelop.kr0());
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
