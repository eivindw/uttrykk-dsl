package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class BelopByttFortegnUttrykk extends AbstractUttrykk<Belop, BelopByttFortegnUttrykk> implements BelopUttrykk {
    private BelopUttrykk belopUttrykk;

    public BelopByttFortegnUttrykk(BelopUttrykk belopUttrykk) {
        this.belopUttrykk = belopUttrykk;
    }

    @Override
    public Belop eval(UttrykkContext ctx) {
        return ctx.eval(belopUttrykk).byttFortegn();
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return "(- " + ctx.beskriv(belopUttrykk)+")";
    }
}
