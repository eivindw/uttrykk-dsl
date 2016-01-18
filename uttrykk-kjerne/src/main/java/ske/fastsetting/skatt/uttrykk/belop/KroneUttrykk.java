package ske.fastsetting.skatt.uttrykk.belop;

import java.math.BigInteger;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class KroneUttrykk extends AbstractUttrykk<Belop, KroneUttrykk> implements BelopUttrykk {

    private static final KroneUttrykk KR_0 = kr(0);
    private final Type type;

    public static BelopUttrykk kr0() {return KR_0;};

    private enum Type {
        HeleKroner,
        MedOre
    }

    private final Belop kroner;

    private KroneUttrykk(Belop kroner, Type type) {
        this.kroner = kroner;
        this.type = type;
    }

    public static KroneUttrykk kr(int belop) {
        return new KroneUttrykk(Belop.kr(belop),Type.HeleKroner);
    }

    public static KroneUttrykk kr(BigInteger belop) {
        return new KroneUttrykk(Belop.kr(belop.intValue()),Type.MedOre);
    }

    public static KroneUttrykk kr(double belop) {
        return new KroneUttrykk(Belop.kr(belop),Type.MedOre);
    }

    @Override
    public Belop eval(UttrykkContext ctx) {
        return kroner;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return type.equals(Type.HeleKroner) ? kroner.toString() : kroner.toStringMedOre();
    }
}
