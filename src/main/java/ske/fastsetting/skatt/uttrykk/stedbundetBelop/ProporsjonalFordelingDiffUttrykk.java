package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

/**
* Created by jorn ola birkeland on 01.03.15.
*/
public class ProporsjonalFordelingDiffUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>,ProporsjonalFordelingDiffUttrykk<K>> implements StedbundetBelopUttrykk<K> {
    private final StedbundetBelopUttrykk<K> stedbundetBelopUttrykk;
    private final BelopUttrykk belop;

    public ProporsjonalFordelingDiffUttrykk(StedbundetBelopUttrykk<K> stedbundetBelopUttrykk, BelopUttrykk belop) {
        this.stedbundetBelopUttrykk = stedbundetBelopUttrykk;
        this.belop = belop;
    }

    @Override
    public StedbundetBelop<K> eval(UttrykkContext ctx) {
        return ctx.eval(stedbundetBelopUttrykk).fordelProporsjonalt(ctx.eval(belop.byttFortegn()));
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(stedbundetBelopUttrykk)+ " - " +ctx.beskriv(belop);
    }
}
