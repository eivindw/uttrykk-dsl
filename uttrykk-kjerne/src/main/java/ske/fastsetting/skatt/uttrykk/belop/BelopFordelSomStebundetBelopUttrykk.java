package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.multibelop.MultiBelopUttrykk;

public class BelopFordelSomStebundetBelopUttrykk<K>
  extends AbstractUttrykk<MultiBelop<K>,BelopFordelSomStebundetBelopUttrykk<K>>
  implements MultiBelopUttrykk<K> {
    private final BelopUttrykk belopUttrykk;
    private final MultiBelopUttrykk<K> multiBelopUttrykk;

    public BelopFordelSomStebundetBelopUttrykk(BelopUttrykk belopUttrykk,
      MultiBelopUttrykk<K> multiBelopUttrykk) {
        this.belopUttrykk = belopUttrykk;
        this.multiBelopUttrykk = multiBelopUttrykk;
    }

    @Override
    public MultiBelop<K> eval(UttrykkContext ctx) {
        Belop belop = ctx.eval(belopUttrykk);
        MultiBelop<K> multiBelop = ctx.eval(multiBelopUttrykk);

        return multiBelop.nyMedSammeFordeling(belop);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return belopUttrykk.beskriv(ctx);
    }
}
