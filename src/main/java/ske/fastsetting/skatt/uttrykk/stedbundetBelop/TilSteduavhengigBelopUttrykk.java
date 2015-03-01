package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

/**
* Created by jorn ola birkeland on 01.03.15.
*/
public class TilSteduavhengigBelopUttrykk<K> extends AbstractUttrykk<Belop,TilSteduavhengigBelopUttrykk<K>> implements BelopUttrykk {
    private StedbundetBelopUttrykk<K> stedbundetBelopUttrykk;

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
