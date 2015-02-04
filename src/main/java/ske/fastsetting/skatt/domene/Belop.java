package ske.fastsetting.skatt.domene;

import org.javamoney.moneta.Money;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Belop implements Comparable<Belop>, KalkulerbarVerdi<Belop> {

    public static final Belop NULL = Belop.kr(0);

    private final Money belop;

    public static Belop kr(int belop) {
        return new Belop(Money.of(belop, "NOK"));
    }

    public static Belop kr(double belop) {
        return new Belop(Money.of(belop, "NOK"));
    }

    public static Belop fra(Money belop) {
        return new Belop(belop);
    }

    public static Belop kr(BigInteger bigInteger) {
        return new Belop(Money.of(bigInteger, "NOK"));
    }

    private Belop(Money belop) {
        this.belop = belop;
    }

    public Belop rundAvTilHeleKroner() {
        return Belop.kr(toInteger());
    }

    public Belop rundAvTilNaermeste(int naermesteKrone) {
        return new Belop(
            belop.add(Money.of(naermesteKrone / 2, "NOK"))
                .divideToIntegralValue(naermesteKrone)
                .multiply(naermesteKrone)
        );
    }

    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setGroupingSeparator(' ');
        decimalFormat.setDecimalFormatSymbols(formatSymbols);

        return "kr " + decimalFormat.format(belop.getNumberStripped());
    }

    public Belop pluss(Belop ledd) {
        return ledd != null ? new Belop(this.belop.add(ledd.belop)) : this;
    }

    public Belop minus(Belop ledd) {
        return new Belop(this.belop.subtract(ledd.belop));
    }

    public int sammenliknMed(Belop verdi) {
        return compareTo(verdi);
    }

    public Belop multiplisertMed(BigDecimal ledd) {
        return new Belop(belop.multiply(ledd));
    }

    public Belop dividertMed(BigDecimal ledd) {
        return new Belop(belop.divide(ledd));
    }

    public Integer toInteger() {
        return this.belop.getNumberStripped().setScale(0, RoundingMode.HALF_UP).intValue();
    }

    public BigInteger toBigInteger() {
        return this.belop.getNumberStripped().setScale(0, RoundingMode.HALF_UP).toBigInteger();
    }

    public BigDecimal dividertMed(Belop divident) {
        return belop.divide(divident.belop.getNumber()).getNumberStripped();
    }

    @Override
    public int compareTo(Belop verdi) {
        return belop.compareTo(verdi.belop);
    }

    public boolean erMindreEnn(Belop v) {
        return this.compareTo(v) < 0;
    }

    public boolean erStorreEnn(Belop v) {
        return this.compareTo(v) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Belop belop1 = (Belop) o;

        return belop.equals(belop1.belop);
    }

    @Override
    public int hashCode() {
        return belop.hashCode();
    }
}
