package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

public class StedbundetBelopForholdsmessigSumUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>,
  StedbundetBelopForholdsmessigSumUttrykk<K>> implements StedbundetBelopUttrykk<K> {
    private final StedbundetBelopUttrykk<K> stedbundetBelopUttrykk;
    private final BelopUttrykk belopUttrykk;

    public StedbundetBelopForholdsmessigSumUttrykk(StedbundetBelopUttrykk<K> stedbundetBelopUttrykk, BelopUttrykk
      belopUttrykk) {
        this.stedbundetBelopUttrykk = stedbundetBelopUttrykk;
        this.belopUttrykk = belopUttrykk;
    }

    @Override
    public StedbundetBelop<K> eval(UttrykkContext ctx) {
        return ctx.eval(stedbundetBelopUttrykk).fordelProporsjonalt(ctx.eval(belopUttrykk));
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(stedbundetBelopUttrykk) + " + " + ctx.beskriv(belopUttrykk);
    }
}
