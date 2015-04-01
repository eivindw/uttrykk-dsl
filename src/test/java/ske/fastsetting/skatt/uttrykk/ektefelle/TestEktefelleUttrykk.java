package ske.fastsetting.skatt.uttrykk.ektefelle;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

class TestEktefelleUttrykk extends AbstractUttrykk<Belop, TestEktefelleUttrykk> implements BelopUttrykk {

    private Uttrykk<Belop> ektefelleUttrykk;

    public TestEktefelleUttrykk(Uttrykk<Belop> ektefelleUttrykk) {

        this.ektefelleUttrykk = ektefelleUttrykk;
    }

    public static TestEktefelleUttrykk ektefelles(Uttrykk<Belop> ektefelleUttrykk) {
        return new TestEktefelleUttrykk(ektefelleUttrykk);
    }

    @Override
    public Belop eval(UttrykkContext ctx) {
        if (ctx.harInput(TestEktefelleUttrykkContext.class)) {
            return ektefelleUttrykk.eval(ctx.input(TestEktefelleUttrykkContext.class));
        } else {
            return Belop.NULL;
        }
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return "ektefelles " + ctx.beskriv(ektefelleUttrykk);
    }

}
