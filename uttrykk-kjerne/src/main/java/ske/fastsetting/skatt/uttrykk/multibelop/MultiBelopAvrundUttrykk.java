package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class MultiBelopAvrundUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>,
  MultiBelopAvrundUttrykk<K>> implements MultiBelopUttrykk<K> {

    private MultiBelopUttrykk<K> belopUttrykk;

    public MultiBelopAvrundUttrykk(MultiBelopUttrykk<K> belopUttrykk) {

        this.belopUttrykk = belopUttrykk;
    }

    public static <K> MultiBelopAvrundUttrykk<K> rundAvTilHeleKroner(MultiBelopUttrykk<K> belopUttrykk) {
        return new MultiBelopAvrundUttrykk<>(belopUttrykk);
    }


    @Override
    public StedbundetBelop<K> eval(UttrykkContext uttrykkContext) {
        return uttrykkContext.eval(belopUttrykk).rundAvTilHeleKroner();
    }

    @Override
    public String beskriv(UttrykkContext uttrykkContext) {
        return uttrykkContext.beskriv(belopUttrykk) + " avrundet til hele kroner";
    }
}
