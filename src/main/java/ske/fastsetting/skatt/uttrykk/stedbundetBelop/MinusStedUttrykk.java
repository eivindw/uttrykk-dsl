package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class MinusStedUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>, MinusStedUttrykk<K>> implements
  StedbundetBelopUttrykk<K> {

    private final StedbundetBelopUttrykk<K> uttrykk;
    private final StedbundetBelopUttrykk<K> ledd;

    public MinusStedUttrykk(StedbundetBelopUttrykk<K> stedbundetBelopUttrykk, StedbundetBelopUttrykk<K> ledd) {

        this.uttrykk = stedbundetBelopUttrykk;
        this.ledd = ledd;
    }

    @Override
    public StedbundetBelop<K> eval(UttrykkContext ctx) {

        StedbundetBelop<K> ledd1 = ctx.eval(uttrykk);
        StedbundetBelop<K> ledd2 = ctx.eval(ledd);

        return ledd1.steder().stream()
          .filter(s -> !ledd2.harSted(s))
          .map(s -> StedbundetBelop.kr(ledd1.get(s), s))
          .reduce(StedbundetBelop.kr0(), StedbundetBelop::pluss);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(uttrykk) + " - sted(" + ctx.beskriv(ledd) + ")";
    }
}
