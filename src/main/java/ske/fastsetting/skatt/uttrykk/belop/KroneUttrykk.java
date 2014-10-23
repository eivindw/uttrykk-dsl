package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;

import java.math.BigInteger;

public class KroneUttrykk extends AbstractUttrykk<Belop, KroneUttrykk> implements BelopUttrykk {

    private final Belop kroner;

    public KroneUttrykk(Belop kroner) {
        this.kroner = kroner;
    }

    public static KroneUttrykk kr(int belop) {
        return new KroneUttrykk(new Belop(belop));
    }

    public static KroneUttrykk kr(BigInteger belop) {
        return new KroneUttrykk(new Belop(belop.intValue()));
    }

    @Override
    public Belop eval(UttrykkContext ctx) {
        return kroner;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return kroner.toString();
    }
}
