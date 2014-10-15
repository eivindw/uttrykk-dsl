package ske.fastsetting.skatt.domene;

import java.math.BigDecimal;
import java.math.BigInteger;

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
        PROSENT
    }

    public Tall() {
        this(TallUttrykkType.ZERO, BigDecimal.ZERO);
    }

    public static Tall prosent(double prosent) {
        return new Tall(TallUttrykkType.PROSENT, BigDecimal.valueOf(prosent).divide(BigDecimal.valueOf(100)));
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


    private TallUttrykkType finnType(Tall ledd) {
        return type != TallUttrykkType.ZERO ? type : ledd.type;
    }

    @Override
    public String toString() {
        switch (type) {
            case PROSENT:
                return verdi.movePointRight(2).toString() + "%";
            default:
                return verdi.toString();
        }
    }
}
