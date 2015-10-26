package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

public class MultiBelopForholdsmessigSumUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>,
  MultiBelopForholdsmessigSumUttrykk<K>> implements MultiBelopUttrykk<K> {
    private final MultiBelopUttrykk<K> multiBelopUttrykk;
    private final BelopUttrykk belopUttrykk;

    public MultiBelopForholdsmessigSumUttrykk(MultiBelopUttrykk<K> multiBelopUttrykk, BelopUttrykk
      belopUttrykk) {
        this.multiBelopUttrykk = multiBelopUttrykk;
        this.belopUttrykk = belopUttrykk;
    }

    @Override
    public StedbundetBelop<K> eval(UttrykkContext ctx) {
        return ctx.eval(multiBelopUttrykk).fordelProporsjonalt(ctx.eval(belopUttrykk));
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(multiBelopUttrykk) + " + " + ctx.beskriv(belopUttrykk);
    }
}
