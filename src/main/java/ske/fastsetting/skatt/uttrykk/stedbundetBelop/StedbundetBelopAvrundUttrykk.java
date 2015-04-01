package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class StedbundetBelopAvrundUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>,
  StedbundetBelopAvrundUttrykk<K>> implements StedbundetBelopUttrykk<K> {

    private StedbundetBelopUttrykk<K> belopUttrykk;

    public StedbundetBelopAvrundUttrykk(StedbundetBelopUttrykk<K> belopUttrykk) {

        this.belopUttrykk = belopUttrykk;
    }

    public static <K> StedbundetBelopAvrundUttrykk<K> rundAvTilHeleKroner(StedbundetBelopUttrykk<K> belopUttrykk) {
        return new StedbundetBelopAvrundUttrykk<>(belopUttrykk);
    }


    @Override
    public StedbundetBelop<K> eval(UttrykkContext uttrykkContext) {
        return uttrykkContext.eval(belopUttrykk).rundAvTilHeleKroner();
    }

    @Override
    public String beskriv(UttrykkContext uttrykkContext) {
        return "rund av til hele kroner (" + uttrykkContext.beskriv(belopUttrykk) + ")";
    }
}
