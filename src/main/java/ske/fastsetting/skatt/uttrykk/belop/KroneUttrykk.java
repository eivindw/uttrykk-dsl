package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;

import java.math.BigInteger;

public class KroneUttrykk<C> extends AbstractUttrykk<Belop, KroneUttrykk<C>, C> implements BelopUttrykk<C> {

    private final Belop kroner;

    public KroneUttrykk(Belop kroner) {
        this.kroner = kroner;
    }

    public static <C> KroneUttrykk<C> kr(int belop) {
        return new KroneUttrykk<>(new Belop(belop));
    }

    public static <C> KroneUttrykk<C> kr(BigInteger belop) {
        return new KroneUttrykk<>(new Belop(belop.intValue()));
    }

    @Override
    public Belop eval(UttrykkContext<C> ctx) {
        return kroner;
    }

    @Override
    public String beskriv(UttrykkContext<C> ctx) {
        return kroner.toString();
    }
}
