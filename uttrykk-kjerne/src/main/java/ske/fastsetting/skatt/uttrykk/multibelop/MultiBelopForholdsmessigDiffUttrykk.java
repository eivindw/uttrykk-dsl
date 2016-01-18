package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class MultiBelopForholdsmessigDiffUttrykk<K> extends AbstractUttrykk<MultiBelop<K>,
  MultiBelopForholdsmessigDiffUttrykk<K>> implements MultiBelopUttrykk<K> {
    private final Uttrykk<MultiBelop<K>> stedbundetBelopUttrykk;
    private final Uttrykk<Belop> belop;

    public MultiBelopForholdsmessigDiffUttrykk(Uttrykk<MultiBelop<K>> stedbundetBelopUttrykk,
      Uttrykk<Belop> belop) {
        this.stedbundetBelopUttrykk = stedbundetBelopUttrykk;
        this.belop = belop;
    }

    @Override
    public MultiBelop<K> eval(UttrykkContext ctx) {
        return ctx.eval(stedbundetBelopUttrykk).fordelProporsjonalt(ctx.eval(belop).byttFortegn());
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(stedbundetBelopUttrykk) + " - " + ctx.beskriv(belop);
    }
}
