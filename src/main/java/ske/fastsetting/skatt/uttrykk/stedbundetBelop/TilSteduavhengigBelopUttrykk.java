package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

import java.util.Map;

public class TilSteduavhengigBelopUttrykk extends AbstractUttrykk<Belop,TilSteduavhengigBelopUttrykk> implements BelopUttrykk {
    private StedbundetBelopUttrykk<?> stedbundetBelopUttrykk;

    public static TilSteduavhengigBelopUttrykk steduavhengig(StedbundetBelopUttrykk<?> stedbundetBelopUttrykk)  {
        return new TilSteduavhengigBelopUttrykk(stedbundetBelopUttrykk);
    }

    public TilSteduavhengigBelopUttrykk(StedbundetBelopUttrykk<?> stedbundetBelopUttrykk) {
        this.stedbundetBelopUttrykk = stedbundetBelopUttrykk;
    }

    public void test(Map<?,Belop> map) {
        map.get("asdas");
    }

    @Override
    public Belop eval(UttrykkContext ctx) {

        final StedbundetBelop<?> stedbundetBelop = ctx.eval(stedbundetBelopUttrykk);

        return stedbundetBelop.steder().stream()
                .map(stedbundetBelop::get)
                .reduce(Belop.NULL, Belop::pluss);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(stedbundetBelopUttrykk);
    }
}
