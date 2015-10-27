package ske.fastsetting.skatt.uttrykk.bolsk;

import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class IkkeUttrykk extends AbstractBolskUttrykk {

    private final BolskUttrykk uttrykk;

    public IkkeUttrykk(BolskUttrykk uttrykk) {
        this.uttrykk = uttrykk;
    }

    public static IkkeUttrykk ikke(BolskUttrykk uttrykk) {
        return new IkkeUttrykk(uttrykk);
    }

    @Override
    public Boolean eval(UttrykkContext ctx) {
        return !ctx.eval(uttrykk);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return "ikke "+ctx.beskriv(uttrykk);
    }
}
