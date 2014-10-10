package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.UttrykkBeskriver;
import ske.fastsetting.skatt.uttrykk.RegelUtil;
import ske.fastsetting.skatt.uttrykk.RegelUttrykk;

import java.math.BigInteger;

public class KroneUttrykk extends RegelUttrykk<KroneUttrykk> implements BelopUttrykk {

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
    public Belop evaluer() {
        return kroner;
    }

    @Override
    public void beskrivEvaluering(UttrykkBeskriver beskriver) {
        beskriver.skriv(kroner.toString() + RegelUtil.formater(navn));
    }

    @Override
    public void beskrivGeneriskMedRegel(UttrykkBeskriver beskriver) {
        beskriver.skriv(kroner.toString());
    }

}
