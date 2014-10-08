package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.RegelUtil;
import ske.fastsetting.skatt.uttrykk.RegelUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkBeskriver;

import java.math.BigInteger;

public class KroneOldUttrykk extends RegelUttrykk<KroneOldUttrykk> implements BelopOldUttrykk {

    private final Belop kroner;

    public KroneOldUttrykk(Belop kroner) {
        this.kroner = kroner;
    }

    public static KroneOldUttrykk kr(int belop) {
        return new KroneOldUttrykk(new Belop(belop));
    }

    public static KroneOldUttrykk kr(BigInteger belop) {
        return new KroneOldUttrykk(new Belop(belop.intValue()));
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
