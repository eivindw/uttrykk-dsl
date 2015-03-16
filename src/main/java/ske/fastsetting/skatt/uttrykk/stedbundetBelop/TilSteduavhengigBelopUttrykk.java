package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

public class TilSteduavhengigBelopUttrykk<K> extends AbstractUttrykk<Belop,TilSteduavhengigBelopUttrykk<K>> implements BelopUttrykk {
    private StedbundetBelopUttrykk<K> stedbundetBelopUttrykk;

    public static <T> TilSteduavhengigBelopUttrykk<T> steduavhengig(StedbundetBelopUttrykk<T> stedbundetBelopUttrykk)  {
        return new TilSteduavhengigBelopUttrykk<>(stedbundetBelopUttrykk);
    }

    public TilSteduavhengigBelopUttrykk(StedbundetBelopUttrykk<K> stedbundetBelopUttrykk) {
        this.stedbundetBelopUttrykk = stedbundetBelopUttrykk;
    }

    @Override
    public Belop eval(UttrykkContext ctx) {

        final StedbundetBelop<K> stedbundetBelop = ctx.eval(stedbundetBelopUttrykk);

        return stedbundetBelop.steder().stream()
                .map(stedbundetBelop::get)
                .reduce(Belop.NULL, Belop::pluss);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(stedbundetBelopUttrykk);
    }
}
