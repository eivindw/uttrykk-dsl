package ske.fastsetting.skatt.uttrykk.ektefelle;

import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

class TestHarEktefelleUttrykk extends AbstractUttrykk<Boolean, TestHarEktefelleUttrykk> {
    @Override
    public Boolean eval(UttrykkContext ctx) {
        return ctx.harInput(TestEktefelleUttrykkContext.class);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return "har ektefelle";
    }
}
