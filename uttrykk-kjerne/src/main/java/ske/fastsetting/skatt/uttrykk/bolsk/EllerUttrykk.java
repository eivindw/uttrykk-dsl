package ske.fastsetting.skatt.uttrykk.bolsk;

import ske.fastsetting.skatt.uttrykk.UttrykkContext;

class EllerUttrykk extends BolskUttrykk {
    private final BolskUttrykk forsteUttrykk;
    private final BolskUttrykk andreUttrykk;

    public EllerUttrykk(BolskUttrykk forsteUttrykk, BolskUttrykk andreUttrykk) {
        this.forsteUttrykk = forsteUttrykk;
        this.andreUttrykk = andreUttrykk;
    }

    @Override
    public Boolean eval(UttrykkContext ctx) {
        return ctx.eval(forsteUttrykk) || ctx.eval(andreUttrykk);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(forsteUttrykk) + " eller " + ctx.beskriv(andreUttrykk);
    }
}
