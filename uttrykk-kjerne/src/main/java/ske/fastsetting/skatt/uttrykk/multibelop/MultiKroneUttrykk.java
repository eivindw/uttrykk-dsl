package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class MultiKroneUttrykk<K> extends AbstractUttrykk<MultiBelop<K>, MultiKroneUttrykk<K>>
  implements MultiBelopUttrykk<K> {

    private final MultiBelop<K> belop;

    private MultiKroneUttrykk(MultiBelop<K> belop) {

        this.belop = belop;
    }

    public static <K> MultiKroneUttrykk<K> kr(int belop, K sted) {
        return new MultiKroneUttrykk<>(MultiBelop.kr(belop, sted));
    }

    public static <K> MultiKroneUttrykk<K> kr0() {
        return new MultiKroneUttrykk<>(MultiBelop.kr0());
    }


    @Override
    public MultiBelop<K> eval(UttrykkContext ctx) {
        return belop;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return belop.toString();
    }
}
