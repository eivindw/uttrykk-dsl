package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetBelopUttrykk;

public class BelopFordelSomStebundetBelopUttrykk<K>
  extends AbstractUttrykk<StedbundetBelop<K>,BelopFordelSomStebundetBelopUttrykk<K>>
  implements StedbundetBelopUttrykk<K> {
    private final BelopUttrykk belopUttrykk;
    private final StedbundetBelopUttrykk<K> stedbundetBelopUttrykk;

    public BelopFordelSomStebundetBelopUttrykk(BelopUttrykk belopUttrykk,
      StedbundetBelopUttrykk<K> stedbundetBelopUttrykk) {
        this.belopUttrykk = belopUttrykk;
        this.stedbundetBelopUttrykk = stedbundetBelopUttrykk;
    }

    @Override
    public StedbundetBelop<K> eval(UttrykkContext ctx) {
        Belop belop = ctx.eval(belopUttrykk);
        StedbundetBelop<K> stedbundetBelop = ctx.eval(stedbundetBelopUttrykk);

        return stedbundetBelop.nyMedSammeFordeling(belop);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return belopUttrykk.beskriv(ctx);
    }
}
