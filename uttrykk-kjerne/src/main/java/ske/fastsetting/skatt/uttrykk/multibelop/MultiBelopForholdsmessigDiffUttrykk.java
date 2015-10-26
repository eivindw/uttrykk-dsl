package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class MultiBelopForholdsmessigDiffUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>,
  MultiBelopForholdsmessigDiffUttrykk<K>> implements MultiBelopUttrykk<K> {
    private final Uttrykk<StedbundetBelop<K>> stedbundetBelopUttrykk;
    private final Uttrykk<Belop> belop;

    public MultiBelopForholdsmessigDiffUttrykk(Uttrykk<StedbundetBelop<K>> stedbundetBelopUttrykk,
      Uttrykk<Belop> belop) {
        this.stedbundetBelopUttrykk = stedbundetBelopUttrykk;
        this.belop = belop;
    }

    @Override
    public StedbundetBelop<K> eval(UttrykkContext ctx) {
        return ctx.eval(stedbundetBelopUttrykk).fordelProporsjonalt(ctx.eval(belop).byttFortegn());
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(stedbundetBelopUttrykk) + " - " + ctx.beskriv(belop);
    }
}
