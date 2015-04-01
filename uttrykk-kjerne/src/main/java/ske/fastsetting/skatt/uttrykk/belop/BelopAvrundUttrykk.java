package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class BelopAvrundUttrykk extends AbstractUttrykk<Belop, BelopAvrundUttrykk> implements BelopUttrykk {

    private BelopUttrykk belopUttrykk;

    public BelopAvrundUttrykk(BelopUttrykk belopUttrykk) {

        this.belopUttrykk = belopUttrykk;
    }

    public static BelopAvrundUttrykk rundAvTilHeleKroner(BelopUttrykk belopUttrykk) {
        return new BelopAvrundUttrykk(belopUttrykk);
    }

    @Override
    public Belop eval(UttrykkContext uttrykkContext) {
        return uttrykkContext.eval(belopUttrykk).rundAvTilHeleKroner();
    }

    @Override
    public String beskriv(UttrykkContext uttrykkContext) {
        return "rund av til hele kroner (" + uttrykkContext.beskriv(belopUttrykk) + ")";
    }
}
