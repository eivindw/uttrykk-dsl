package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

import java.math.BigDecimal;

public class KonstantUttrykk extends AbstractUttrykk<Tall, KonstantUttrykk> implements TallUttrykk {

    private final Tall verdi;

    public KonstantUttrykk(Tall konstant) {
        this.verdi = konstant;
    }

    public static KonstantUttrykk tall(double konstant) {
        return new KonstantUttrykk(Tall.ukjent(BigDecimal.valueOf(konstant)));
    }

    @Override
    public Tall eval(UttrykkContext ctx) {
        return verdi;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return verdi.toString();
    }

}




