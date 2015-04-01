package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Avrunding;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class TallAvrundUttrykk extends AbstractUttrykk<Tall, TallAvrundUttrykk> implements TallUttrykk {

    private TallUttrykk uttrykk;
    private final int presisjon;
    private final Avrunding avrunding;

    public TallAvrundUttrykk(TallUttrykk uttrykk, int presisjon, Avrunding avrunding) {

        this.uttrykk = uttrykk;
        this.presisjon = presisjon;
        this.avrunding = avrunding;
    }

    @Deprecated
    public static TallAvrundUttrykk avrundOpp(TallUttrykk uttrykk) {
        return new TallAvrundUttrykk(uttrykk, 2, Avrunding.Opp);
    }

    public static TallAvrundUttrykk avrundOpp(TallUttrykk uttrykk, int presisjon) {
        return new TallAvrundUttrykk(uttrykk, presisjon, Avrunding.Opp);
    }

    public static TallAvrundUttrykk avrundNed(TallUttrykk uttrykk, int presisjon) {
        return new TallAvrundUttrykk(uttrykk, presisjon, Avrunding.Ned);
    }

    public static TallAvrundUttrykk avrund(TallUttrykk uttrykk, int presisjon) {
        return new TallAvrundUttrykk(uttrykk, presisjon, Avrunding.Naermeste);
    }

    @Override
    public Tall eval(UttrykkContext ctx) {

        return ctx.eval(uttrykk).rundAv(presisjon, avrunding);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return null;
    }
}
