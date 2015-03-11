package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class BelopAvrundingsUttrykk extends AbstractUttrykk<Belop,BelopAvrundingsUttrykk> implements BelopUttrykk {

    private BelopUttrykk belopUttrykk;

    public BelopAvrundingsUttrykk(BelopUttrykk belopUttrykk) {

        this.belopUttrykk = belopUttrykk;
    }

    public static BelopAvrundingsUttrykk rundAvTilHeleKroner(BelopUttrykk belopUttrykk) {
        return new BelopAvrundingsUttrykk(belopUttrykk);
    }

    @Override
    public Belop eval(UttrykkContext uttrykkContext) {
        return uttrykkContext.eval(belopUttrykk).rundAvTilHeleKroner();
    }

    @Override
    public String beskriv(UttrykkContext uttrykkContext) {
        return "rund av til hele kroner ("+uttrykkContext.beskriv(belopUttrykk)+")";
    }
}
