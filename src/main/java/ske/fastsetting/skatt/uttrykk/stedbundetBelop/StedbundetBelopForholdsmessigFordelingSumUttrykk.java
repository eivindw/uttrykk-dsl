package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

public class StedbundetBelopForholdsmessigFordelingSumUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>,StedbundetBelopForholdsmessigFordelingSumUttrykk<K>> implements StedbundetBelopUttrykk<K> {
    private final StedbundetBelopUttrykk<K> stedbundetBelopUttrykk;
    private final BelopUttrykk belopUttrykk;

    public StedbundetBelopForholdsmessigFordelingSumUttrykk(StedbundetBelopUttrykk<K> stedbundetBelopUttrykk, BelopUttrykk belopUttrykk) {
        this.stedbundetBelopUttrykk = stedbundetBelopUttrykk;
        this.belopUttrykk = belopUttrykk;
    }

    @Override
    public StedbundetBelop<K> eval(UttrykkContext ctx) {
        return ctx.eval(stedbundetBelopUttrykk).fordelProporsjonalt(ctx.eval(belopUttrykk));

//            final StedbundetBelop<K> stedbundetBelop = ctx.eval(stedbundetBelopUttrykk);
//            final Belop belop = ctx.eval(belopUttrykk);
//
//            Belop absSum = stedbundetBelop.steder().stream()
//                    .map(s -> stedbundetBelop.get(s).abs())
//                    .reduce(Belop.NULL, Belop::pluss);
//
//            StedbundetBelop<K> resultat = StedbundetBelop.kr0();
//
//            if(absSum.erStorreEnn(Belop.NULL)) {
//                resultat = stedbundetBelop.steder().stream()
//                        .map(s -> StedbundetBelop.kr(stedbundetBelop.get(s).pluss(belop.multiplisertMed(stedbundetBelop.get(s).abs().dividertMed(absSum))), s))
//                        .reduce(StedbundetBelop.kr0(), StedbundetBelop::pluss);
//
//            } else if(stedbundetBelop.steder().size()>0) {
//                Belop andel = belop.dividertMed(stedbundetBelop.steder().size());
//                resultat = stedbundetBelop.steder().stream()
//                        .map(s -> StedbundetBelop.kr(stedbundetBelop.get(s).pluss(andel),s))
//                        .reduce(StedbundetBelop.kr0(), StedbundetBelop::pluss);
//            }
//            return resultat;

    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(stedbundetBelopUttrykk)+ " + " +ctx.beskriv(belopUttrykk);
    }
}
