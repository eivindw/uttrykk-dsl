package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Avrunding;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class AvrundTallUttrykk extends AbstractUttrykk<Tall, AvrundTallUttrykk> implements TallUttrykk {

    private TallUttrykk uttrykk;
    private final int presisjon;
    private final Avrunding avrunding;

    public AvrundTallUttrykk(TallUttrykk uttrykk, int presisjon, Avrunding avrunding) {

        this.uttrykk = uttrykk;
        this.presisjon = presisjon;
        this.avrunding = avrunding;
    }

    @Deprecated
    public static AvrundTallUttrykk avrundOpp(TallUttrykk uttrykk) {
        return new AvrundTallUttrykk(uttrykk, 2, Avrunding.Opp);
    }

    public static AvrundTallUttrykk avrundOpp(TallUttrykk uttrykk, int presisjon) {
        return new AvrundTallUttrykk(uttrykk, presisjon, Avrunding.Opp);
    }

    public static AvrundTallUttrykk avrundNed(TallUttrykk uttrykk, int presisjon) {
        return new AvrundTallUttrykk(uttrykk, presisjon, Avrunding.Ned);
    }

    public static AvrundTallUttrykk avrund(TallUttrykk uttrykk, int presisjon) {
        return new AvrundTallUttrykk(uttrykk, presisjon, Avrunding.Naermeste);
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
