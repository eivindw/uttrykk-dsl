package ske.fastsetting.skatt.uttrykk.distanse;

import ske.fastsetting.skatt.domene.KalkulerbarVerdi;
import ske.fastsetting.skatt.domene.Kvantitet;

import java.math.BigDecimal;

public class Distanse extends Kvantitet<Double,Kilometer> implements Comparable<Distanse>, KalkulerbarVerdi<Distanse> {

    public Distanse(Double verdi) {
        super(verdi);
    }

    @Override
    public BigDecimal toBigDecimal() {
        return BigDecimal.valueOf(verdi());
    }

    @Override
    public Distanse minus(Distanse ledd) {
        return new Distanse(verdi()-ledd.verdi());
    }

    @Override
    public Distanse pluss(Distanse ledd) {
        return new Distanse(verdi()+ledd.verdi());
    }

    @Override
    public Distanse multiplisertMed(BigDecimal faktor) {
        return new Distanse(verdi()*faktor.doubleValue());
    }

    @Override
    public Distanse dividertMed(BigDecimal divisor) {
        return new Distanse(verdi()/divisor.doubleValue());
    }

    @Override
    public int compareTo(Distanse o) {
        return verdi().compareTo(o.verdi());
    }

    @Override
    public String toString() {
        return verdi() + " km";
    }
}
