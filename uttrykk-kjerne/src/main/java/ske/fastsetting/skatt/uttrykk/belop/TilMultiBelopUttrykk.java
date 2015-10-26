package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.multibelop.MultiBelopUttrykk;

public class TilMultiBelopUttrykk<T> extends AbstractUttrykk<StedbundetBelop<T>,TilMultiBelopUttrykk<T>> implements MultiBelopUttrykk<T> {


    private final Uttrykk<Belop> belopUttrykk;
    private final T sted;

    public static <T> TilMultiBelopUttrykk<T> tilStedbundet(Uttrykk<Belop> belopUttrykk, T sted) {
        return new TilMultiBelopUttrykk<>(belopUttrykk,sted);
    }

    public TilMultiBelopUttrykk(Uttrykk<Belop> belopUttrykk, T sted) {
        this.belopUttrykk = belopUttrykk;
        this.sted = sted;
    }

    @Override
    public StedbundetBelop<T> eval(UttrykkContext ctx) {
        Belop belop = ctx.eval(belopUttrykk);
        return StedbundetBelop.kr(belop,sted);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(belopUttrykk)+"->"+sted;
    }
}
