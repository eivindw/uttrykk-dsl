package ske.fastsetting.skatt.domene;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.Kvantitet;

public class BelopPerKvantitet<K extends Kvantitet> {

    private Belop belop;

    public BelopPerKvantitet(Belop belop) {
        this.belop = belop;
    }

    public Belop multiplisertMed(K kvantitet) {
        return belop.multiplisertMed(kvantitet.toBigDecimal());
    }
}
