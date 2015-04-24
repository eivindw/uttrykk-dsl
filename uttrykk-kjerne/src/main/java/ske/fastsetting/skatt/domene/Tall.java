package ske.fastsetting.skatt.domene;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class Tall implements Comparable<Tall>, KalkulerbarVerdi<Tall> {

    public static final Tall ZERO = new Tall(TallUttrykkType.ZERO, BigDecimal.ZERO);

    private final BigDecimal verdi;
    private final TallUttrykkType type;

    @Override
    public int compareTo(Tall tall) {
        return verdi.compareTo(tall.verdi);
    }

    public BigDecimal toBigDecimal() {
        return verdi;
    }

    public BigInteger toBigInteger() {
        return verdi.toBigInteger();
    }


    public enum TallUttrykkType {
        ZERO,
        UKJENT,
        PROSENT,
        HELTALL
    }

    public Tall() {
        this(TallUttrykkType.ZERO, BigDecimal.ZERO);
    }

    public static Tall nytt(BigDecimal verdi,TallUttrykkType type) {
        return new Tall(type, verdi);
    }

    public static Tall prosent(double prosent) {
        return new Tall(TallUttrykkType.PROSENT, BigDecimal.valueOf(prosent).divide(BigDecimal.valueOf(100)));
    }

    public static Tall heltall(int heltall) {
        return new Tall(TallUttrykkType.HELTALL, BigDecimal.valueOf(heltall));
    }

    public static Tall ukjent(BigDecimal verdi) {
        return new Tall(TallUttrykkType.UKJENT, verdi);
    }

    public Tall(TallUttrykkType type, BigDecimal verdi) {
        this.type = type;
        this.verdi = verdi;
    }

    public Tall pluss(Tall ledd) {
        TallUttrykkType nyType = finnType(ledd);

        return new Tall(nyType, verdi.add(ledd.verdi));
    }

    public Tall minus(Tall ledd) {
        TallUttrykkType nyType = finnType(ledd);

        return new Tall(nyType, verdi.subtract(ledd.verdi));
    }

    public Tall multiplisertMed(BigDecimal faktor) {
        return new Tall(type, verdi.multiply(faktor));
    }


    public Tall dividertMed(BigDecimal divisor) {
        return new Tall(type, verdi.divide(divisor));
    }

    @Deprecated
    public Tall rundOpp() {
        switch (type) {
        case PROSENT:
            return new Tall(TallUttrykkType.PROSENT, this.verdi.round(new MathContext(2, RoundingMode.UP)));
        default:
            return this;
        }
    }

    public Tall rundOpp(int presisjon) {
        return rundAv(presisjon, Avrunding.Opp);
    }

    public Tall rundNed(int presisjon) {
        return rundAv(presisjon, Avrunding.Ned);
    }

    public Tall rundAv(int presisjon, Avrunding avrunding) {
        return new Tall(type, avrunding.rundAv(verdi, presisjon));
    }

    private TallUttrykkType finnType(Tall ledd) {
        return type != TallUttrykkType.ZERO ? type : ledd.type;
    }

    @Override
    public String toString() {
        switch (type) {
        case PROSENT:
            return verdi.movePointRight(2).toString() + "%";
        case HELTALL:
            return Integer.toString(verdi.intValueExact());
        default:
            return verdi.toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Tall tall = (Tall) o;

        if (type != tall.type)
            return false;
        if (!verdi.equals(tall.verdi))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = verdi.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
