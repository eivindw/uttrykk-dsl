package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class ProporsjonalFordelingDiffUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>,ProporsjonalFordelingDiffUttrykk<K>> implements StedbundetBelopUttrykk<K> {
    private final Uttrykk<StedbundetBelop<K>> stedbundetBelopUttrykk;
    private final Uttrykk<Belop> belop;

    public ProporsjonalFordelingDiffUttrykk(Uttrykk<StedbundetBelop<K>> stedbundetBelopUttrykk, Uttrykk<Belop> belop) {
        this.stedbundetBelopUttrykk = stedbundetBelopUttrykk;
        this.belop = belop;
    }

    @Override
    public StedbundetBelop<K> eval(UttrykkContext ctx) {
        return ctx.eval(stedbundetBelopUttrykk).fordelProporsjonalt(ctx.eval(belop).byttFortegn());
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(stedbundetBelopUttrykk)+ " - " +ctx.beskriv(belop);
    }
}
