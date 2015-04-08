package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class StedbundetBelopByttFortegnUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>, StedbundetBelopByttFortegnUttrykk<K>> implements StedbundetBelopUttrykk<K> {
    private StedbundetBelopUttrykk<K> belopUttrykk;

    public static <K> StedbundetBelopByttFortegnUttrykk<K> byttFortegn(StedbundetBelopUttrykk<K> uttrykk) {
        return new StedbundetBelopByttFortegnUttrykk<>(uttrykk);
    }

    public StedbundetBelopByttFortegnUttrykk(StedbundetBelopUttrykk<K> belopUttrykk) {
        this.belopUttrykk = belopUttrykk;
    }

    @Override
    public StedbundetBelop<K> eval(UttrykkContext ctx) {
        return ctx.eval(belopUttrykk).byttFortegn();
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return " - " + ctx.beskriv(belopUttrykk);
    }
}
