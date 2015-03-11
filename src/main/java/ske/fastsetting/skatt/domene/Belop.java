package ske.fastsetting.skatt.domene;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Belop implements Comparable<Belop>, KalkulerbarVerdi<Belop> {

    public static final Belop NULL = Belop.kr(0);

    private static final int ORE_I_KR = 100;

    private final long belop;

    public static Belop kr(int belop) {
        return fraKr(belop);
    }

    public static Belop kr(double belop) {
        return fraKr(belop);
    }

    public static Belop kr(BigInteger bigInteger) {
        return fraKr(bigInteger.doubleValue());
    }

    private static Belop fraOre(long belop) {
        return new Belop(belop);
    }

    private static Belop fraKr(double belop) {
        return new Belop(Math.round(belop * ORE_I_KR));
    }

    private Belop(long belop) {
        this.belop = belop;
    }

    public Belop rundAvTilHeleKroner() {
        return Belop.fraKr(toInteger());
    }

    public Belop rundAvTilNaermeste(int naermesteKrone) {
        BigDecimal kr = BigDecimal.valueOf(naermesteKrone);
        return fraKr(
            BigDecimal
                .valueOf(toInteger())
                .add(BigDecimal.valueOf(naermesteKrone / 2))
                .divideToIntegralValue(kr)
                .multiply(kr)
                .doubleValue()
        );
    }

    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setGroupingSeparator(' ');
        decimalFormat.setDecimalFormatSymbols(formatSymbols);

        return "kr " + decimalFormat.format(toInteger());
    }

    public Belop pluss(Belop ledd) {
        return ledd != null ? fraOre(this.belop + ledd.belop) : this;
    }

    public Belop minus(Belop ledd) {
        return fraOre(this.belop - ledd.belop);
    }

    public int sammenliknMed(Belop verdi) {
        return compareTo(verdi);
    }

    public Belop multiplisertMed(BigDecimal ledd) {
        return fraOre(Math.round(belop * ledd.doubleValue()));
    }

    public BigDecimal dividertMed(Belop divident) {
        return BigDecimal.valueOf((double) belop / divident.belop);
    }

    public Belop dividertMed(int ledd) {
        return dividertMed(BigDecimal.valueOf(ledd));
    }

    public Belop dividertMed(BigDecimal ledd) {
        return fraOre(Math.round(belop / ledd.doubleValue()));
    }

    public Integer toInteger() {
        return (int) Math.round((double) this.belop / ORE_I_KR);
    }

    public BigInteger toBigInteger() {
        return BigInteger.valueOf(toInteger());
    }

    @Override
    public int compareTo(Belop verdi) {
        return Long.compare(belop, verdi.belop);
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
        return new Belop(-belop);
    }

    public Belop abs() {
        return new Belop(Math.abs(belop));
    }
}
