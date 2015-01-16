package ske.fastsetting.skatt.uttrykk.ektefelle;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

/**
 * Created by jorn ola birkeland on 15.01.15.
 */
public class EktefelleUttrykk extends AbstractUttrykk<Belop,EktefelleUttrykk> implements BelopUttrykk {


    private Uttrykk<Belop> ektefelleUttrykk;

    public EktefelleUttrykk(Uttrykk<Belop> ektefelleUttrykk) {

        this.ektefelleUttrykk = ektefelleUttrykk;
    }

    public static EktefelleUttrykk ektefelles(Uttrykk<Belop> ektefelleUttrykk) {
        return new EktefelleUttrykk(ektefelleUttrykk);
    }

    @Override
    public Belop eval(UttrykkContext ctx) {
        if(ctx.harInput(EktefelleUttrykkContext.class)) {
            return ektefelleUttrykk.eval(ctx.input(EktefelleUttrykkContext.class));
        } else {
            return Belop.NULL;
        }
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return "ektefelles "+ctx.beskriv(ektefelleUttrykk);
    }

}
