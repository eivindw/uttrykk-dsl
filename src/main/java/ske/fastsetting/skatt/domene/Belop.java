package ske.fastsetting.skatt.domene;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Belop implements Comparable<Belop>, KalkulerbarVerdi<Belop> {

    public static final Belop NULL = Belop.kr(0);

    private final double belop;

    public static Belop kr(int belop) {
        return fra(belop);
    }

    public static Belop kr(double belop) {
        return fra(belop);
    }

    public static Belop fra(double belop) {
        return new Belop(belop);
    }

    public static Belop kr(BigInteger bigInteger) {
        return fra(bigInteger.doubleValue());
    }

    protected Belop(double belop) {
        this.belop = belop;
    }

    public Belop rundAvTilHeleKroner() {
        return Belop.kr(toInteger());
    }

    public Belop rundAvTilNaermeste(int naermesteKrone) {
        BigDecimal kr = BigDecimal.valueOf(naermesteKrone);
        return fra(
            BigDecimal.valueOf(belop).add(BigDecimal.valueOf(naermesteKrone / 2))
                .divideToIntegralValue(kr)
                .multiply(kr).doubleValue()
        );
    }

    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setGroupingSeparator(' ');
        decimalFormat.setDecimalFormatSymbols(formatSymbols);

        return "kr " + decimalFormat.format(belop);
    }

    public Belop pluss(Belop ledd) {
        return ledd != null ? fra(this.belop + ledd.belop) : this;
    }

    public Belop minus(Belop ledd) {
        return fra(this.belop - ledd.belop);
    }

    public int sammenliknMed(Belop verdi) {
        return compareTo(verdi);
    }

    public Belop multiplisertMed(BigDecimal ledd) {
        return fra(belop * ledd.doubleValue());
    }

    public BigDecimal dividertMed(Belop divident) {
        return BigDecimal.valueOf(belop / divident.belop);
    }

    public Belop dividertMed(int ledd) { return dividertMed(BigDecimal.valueOf(ledd)); }

    public Belop dividertMed(BigDecimal ledd) {
        return fra(belop / ledd.doubleValue());
    }

    public Integer toInteger() {
        return (int) Math.round(this.belop);
    }

    public BigInteger toBigInteger() {
        return BigInteger.valueOf(Math.round(this.belop));
    }

    @Override
    public int compareTo(Belop verdi) {
        return Double.compare(belop, verdi.belop);
    }

    public boolean erMindreEnn(Belop v) {
        return this.compareTo(v) < 0;
    }

    public boolean erStorreEnn(Belop v) {
        return this.compareTo(v) > 0;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Belop && belop == ((Belop) o).belop;
    }

    @Override
    public int hashCode() {
        return (int) belop;
    }

    public Belop byttFortegn() {
        return new Belop(- belop);
    }

    public Belop abs() {
        return new Belop(Math.abs(belop));
    }
}
