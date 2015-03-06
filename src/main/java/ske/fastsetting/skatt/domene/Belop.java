package ske.fastsetting.skatt.domene;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Belop implements Comparable<Belop>, KalkulerbarVerdi<Belop> {

    public static final Belop NULL = Belop.kr(0);
    private static final MathContext MATHCONTEXT = new MathContext(256, RoundingMode.HALF_EVEN);

    private final BigDecimal belop;

    public static Belop kr(int belop) {
        return fra(BigDecimal.valueOf(belop));
    }

    public static Belop kr(double belop) {
        return fra(BigDecimal.valueOf(belop));
    }

    public static Belop fra(BigDecimal belop) {
        return new Belop(belop);
    }

    public static Belop kr(BigInteger bigInteger) {
        return fra(new BigDecimal(bigInteger));
    }

    protected Belop(BigDecimal belop) {
        this.belop = belop;
    }

    public Belop rundAvTilHeleKroner() {
        return Belop.kr(toInteger());
    }

    public Belop rundAvTilNaermeste(int naermesteKrone) {
        BigDecimal kr = BigDecimal.valueOf(naermesteKrone);
        return fra(
            belop.add(BigDecimal.valueOf(naermesteKrone / 2))
                .divideToIntegralValue(kr)
                .multiply(kr)
        );
    }

    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setGroupingSeparator(' ');
        decimalFormat.setDecimalFormatSymbols(formatSymbols);

        return "kr " + decimalFormat.format(belop.stripTrailingZeros());
    }

    public Belop pluss(Belop ledd) {
        return ledd != null ? fra(this.belop.add(ledd.belop)) : this;
    }

    public Belop minus(Belop ledd) {
        return fra(this.belop.subtract(ledd.belop));
    }

    public int sammenliknMed(Belop verdi) {
        return compareTo(verdi);
    }

    public Belop multiplisertMed(BigDecimal ledd) {
        return fra(belop.multiply(ledd));
    }

    public BigDecimal dividertMed(Belop divident) {
        return belop.divide(divident.belop, MATHCONTEXT).stripTrailingZeros();
    }

    public Belop dividertMed(int ledd) { return dividertMed(BigDecimal.valueOf(ledd)); }

    public Belop dividertMed(BigDecimal ledd) {
        return fra(belop.divide(ledd, MATHCONTEXT));
    }

    public Integer toInteger() {
        return this.belop.stripTrailingZeros().setScale(0, RoundingMode.HALF_UP).intValue();
    }

    public BigInteger toBigInteger() {
        return this.belop.stripTrailingZeros().setScale(0, RoundingMode.HALF_UP).toBigInteger();
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
        return o instanceof Belop && belop.stripTrailingZeros().equals(((Belop) o).belop.stripTrailingZeros());
    }

    @Override
    public int hashCode() {
        return belop.hashCode();
    }

    public Belop byttFortegn() {
        return new Belop(belop.negate());
    }

    public Belop abs() {
        return new Belop(belop.abs());
    }
}
