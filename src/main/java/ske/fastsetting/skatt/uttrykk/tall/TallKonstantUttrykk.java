package ske.fastsetting.skatt.uttrykk.tall;

import java.math.BigDecimal;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class TallKonstantUttrykk extends AbstractUttrykk<Tall, TallKonstantUttrykk> implements TallUttrykk {

    private final Tall verdi;

    public TallKonstantUttrykk(Tall konstant) {
        this.verdi = konstant;
    }

    public static TallKonstantUttrykk tall(double konstant) {
        return new TallKonstantUttrykk(Tall.ukjent(BigDecimal.valueOf(konstant)));
    }

    public static TallKonstantUttrykk heltall(int konstant) {
        return new TallKonstantUttrykk(Tall.heltall(konstant));
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




