package ske.fastsetting.skatt.domene;

import ske.fastsetting.skatt.domene.enhet.Enhet;

import java.math.BigDecimal;

public abstract class Kvantitet<V, E extends Enhet> {
    private final V verdi;

    public Kvantitet(V verdi) {
        this.verdi = verdi;
    }

    public V verdi() {
        return verdi;
    }

    public abstract BigDecimal toBigDecimal();

    public abstract Kvantitet<V, E> multiplisertMed(BigDecimal faktor);
}
