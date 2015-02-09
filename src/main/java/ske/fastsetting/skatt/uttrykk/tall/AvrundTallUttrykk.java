package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

/**
 * Created by x00jen on 09.02.15.
 */
public class AvrundTallUttrykk extends AbstractUttrykk<Tall,AvrundTallUttrykk> implements TallUttrykk {

    private TallUttrykk uttrykk;

    public AvrundTallUttrykk(TallUttrykk uttrykk) {

        this.uttrykk = uttrykk;
    }

    public static AvrundTallUttrykk avrundOpp(TallUttrykk uttrykk) {
        return new AvrundTallUttrykk(uttrykk);
    }

    @Override
    public Tall eval(UttrykkContext ctx) {

        return ctx.eval(uttrykk).rundOpp();
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return null;
    }
}
