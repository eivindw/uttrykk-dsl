package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class MultiBelopMinusStedUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>, MultiBelopMinusStedUttrykk<K>> implements

  MultiBelopUttrykk<K> {

    private final MultiBelopUttrykk<K> uttrykk;
    private final MultiBelopUttrykk<K> ledd;

    public MultiBelopMinusStedUttrykk(MultiBelopUttrykk<K> multiBelopUttrykk,
      MultiBelopUttrykk<K> ledd) {

        this.uttrykk = multiBelopUttrykk;
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
