package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

import java.math.BigInteger;

public class KroneUttrykk extends AbstractUttrykk<Belop, KroneUttrykk> implements BelopUttrykk {

    public static final KroneUttrykk KR_0 = kr(0);

    private final Belop kroner;

    public KroneUttrykk(Belop kroner) {
        this.kroner = kroner;
    }

    public static KroneUttrykk kr(int belop) {
        return new KroneUttrykk(Belop.kr(belop));
    }

    public static KroneUttrykk kr(BigInteger belop) {
        return new KroneUttrykk(Belop.kr(belop.intValue()));
    }

    public static KroneUttrykk kr(double belop) {
        return new KroneUttrykk(Belop.kr(belop));
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
