package ske.fastsetting.skatt.beregn;

import ske.fastsetting.skatt.domene.Belop;

public class KrUttrykk extends AbstractUttrykk<Belop> {

    private final Belop tall;

    public KrUttrykk(Belop tall) {
        this.tall = tall;
    }

    @Override
    public Belop eval(UttrykkContext ctx) {
        return tall;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return tall + " kr";
    }

    public static KrUttrykk kr(int tall) {
        return new KrUttrykk(new Belop(tall));
    }
}
