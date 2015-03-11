package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

/**
 * Created by x00mbf on 22.01.15.
 */
public class StedbundetBelopAvrundingsUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>,StedbundetBelopAvrundingsUttrykk<K>> implements StedbundetBelopUttrykk<K> {

    private StedbundetBelopUttrykk<K> belopUttrykk;

    public StedbundetBelopAvrundingsUttrykk(StedbundetBelopUttrykk<K> belopUttrykk) {

        this.belopUttrykk = belopUttrykk;
    }

    public static <K> StedbundetBelopAvrundingsUttrykk<K> rundAvTilHeleKroner(StedbundetBelopUttrykk<K> belopUttrykk) {
        return new StedbundetBelopAvrundingsUttrykk<>(belopUttrykk);
    }


    @Override
    public StedbundetBelop<K> eval(UttrykkContext uttrykkContext) {
        return uttrykkContext.eval(belopUttrykk).rundAvTilHeleKroner();
    }

    @Override
    public String beskriv(UttrykkContext uttrykkContext) {
        return "rund av til hele kroner ("+uttrykkContext.beskriv(belopUttrykk)+")";
    }
}
