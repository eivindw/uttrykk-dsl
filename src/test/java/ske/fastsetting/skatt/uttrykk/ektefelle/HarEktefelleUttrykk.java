package ske.fastsetting.skatt.uttrykk.ektefelle;

import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

/**
 * Created by jorn ola birkeland on 16.01.15.
 */
public class HarEktefelleUttrykk extends AbstractUttrykk<Boolean,HarEktefelleUttrykk> {
    @Override
    public Boolean eval(UttrykkContext ctx) {
        return ctx.harInput(EktefelleUttrykkContext.class);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return "har ektefelle";
    }
}
