package ske.fastsetting.skatt.uttrykk.distanse;

import java.math.BigDecimal;
import java.math.BigInteger;

import ske.fastsetting.skatt.domene.KalkulerbarVerdi;
import ske.fastsetting.skatt.domene.Kvantitet;

public class Distanse extends Kvantitet<Double, Kilometer> implements Comparable<Distanse>, KalkulerbarVerdi<Distanse> {

    public static Distanse NULL = new Distanse(0d);

    public static Distanse km(BigInteger km) {
        return new Distanse(km.doubleValue());
    }

    public static Distanse km(BigDecimal km) {
        return new Distanse(km.doubleValue());
    }

    public static Distanse km(double km) {
        return new Distanse(km);
    }

    public static Distanse km(long km) {
        return new Distanse((double) km);
    }

    public Distanse(Double verdi) {
        super(verdi);
    }

    @Override
    public BigDecimal toBigDecimal() {
        return BigDecimal.valueOf(verdi());
    }

    @Override
    public Distanse minus(Distanse ledd) {
        return new Distanse(verdi() - ledd.verdi());
    }

    @Override
    public Distanse pluss(Distanse ledd) {
        return new Distanse(verdi() + ledd.verdi());
    }

    @Override
    public Distanse multiplisertMed(BigDecimal faktor) {
        return new Distanse(verdi() * faktor.doubleValue());
    }

    @Override
    public Distanse dividertMed(BigDecimal divisor) {
        return new Distanse(verdi() / divisor.doubleValue());
    }

    @Override
    public int compareTo(Distanse o) {
        return verdi().compareTo(o.verdi());
    }

    public boolean erMindreEnn(Distanse v) {
        return this.compareTo(v) < 0;
    }

    public boolean erStorreEnn(Distanse v) {
        return this.compareTo(v) > 0;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Distanse && verdi().equals(((Distanse) o).verdi());
    }

    @Override
    public int hashCode() {
        return verdi().hashCode();
    }


    @Override
    public String toString() {
        return verdi() + " km";
    }
}
