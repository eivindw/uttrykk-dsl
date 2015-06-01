package ske.fastsetting.skatt.uttrykk.distanse;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.BelopPerKvantitet;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopPerKvantitetUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;


public class DistanseMultiplisertMedBelopPerDistanseUttrykk extends AbstractUttrykk<Belop,
        DistanseMultiplisertMedBelopPerDistanseUttrykk> implements BelopUttrykk {
    private final DistanseUttrykk distanseUttrykk;
    private final BelopPerKvantitetUttrykk<Distanse> belopPerDistanseUttrykk;

    public DistanseMultiplisertMedBelopPerDistanseUttrykk(DistanseUttrykk distanseUttrykk,
                                                          BelopPerKvantitetUttrykk<Distanse> belopPerDistanseUttrykk) {
        this.distanseUttrykk = distanseUttrykk;
        this.belopPerDistanseUttrykk = belopPerDistanseUttrykk;
    }

    @Override
    public Belop eval(UttrykkContext ctx) {

        Distanse distanse = ctx.eval(distanseUttrykk);
        BelopPerKvantitet<Distanse> belopPerDistanse = ctx.eval(belopPerDistanseUttrykk);

        return belopPerDistanse.multiplisertMed(distanse);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return null;
    }
}
