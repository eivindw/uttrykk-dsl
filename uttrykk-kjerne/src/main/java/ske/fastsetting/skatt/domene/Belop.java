package ske.fastsetting.skatt.domene;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import ske.fastsetting.skatt.domene.enhet.Krone;

public class Belop extends Kvantitet<Long, Krone> implements Comparable<Belop>, KalkulerbarVerdi<Belop> {

    public static final Belop NULL = Belop.kr(0);

    private static final int ORE_I_KR = 100;

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
        super(belop);
    }

    public Belop rundAvTilHeleKroner() {
        return Belop.fraKr(tilHeleKroner());
    }

    public Belop rundAvTilNaermeste(int naermesteKrone) {
        BigDecimal kr = BigDecimal.valueOf(naermesteKrone);
        return fraKr(
          BigDecimal
            .valueOf(tilHeleKroner())
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

        return "kr " + decimalFormat.format(tilHeleKroner());
    }

    public String toStringMedOre() {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setGroupingSeparator(' ');
        decimalFormat.setDecimalFormatSymbols(formatSymbols);

        return "kr " + decimalFormat.format((double) this.verdi() / ORE_I_KR);
    }


    public Belop pluss(Belop ledd) {
        return ledd != null ? fraOre(this.verdi() + ledd.verdi()) : this;
    }

    public Belop minus(Belop ledd) {
        return fraOre(this.verdi() - ledd.verdi());
    }

    public int sammenliknMed(Belop verdi) {
        return compareTo(verdi);
    }

    public Belop multiplisertMed(BigDecimal ledd) {
        return fraOre(Math.round(verdi() * ledd.doubleValue()));
    }


    public BigDecimal dividertMed(Belop divident) {
        return BigDecimal.valueOf((double) verdi() / divident.verdi());
    }

    public Belop dividertMed(int ledd) {
        return dividertMed(BigDecimal.valueOf(ledd));
    }

    public Belop dividertMed(BigDecimal ledd) {
        return fraOre(Math.round(verdi() / ledd.doubleValue()));
    }


    /**
     *  @deprecated Integer har for liten presisjon, ikke bruk denne med mindre du er klar over hva du gjør
     */
    @Deprecated
    public Integer toInteger() {
        return (int) Math.round((double) this.verdi() / ORE_I_KR);
    }


    /**
     * @return beløp der øre blir avrundet til nærmeste hele krone
     */
    public Long tilHeleKroner() {
        return (long) Math.round((double) this.verdi() / ORE_I_KR);
    }


    public BigInteger toBigInteger() {
        return BigInteger.valueOf(tilHeleKroner());
    }

    public BigDecimal toBigDecimal() {
        return BigDecimal.valueOf(tilHeleKroner());
    }

    @Override
    public int compareTo(Belop belop) {
        return Long.compare(verdi(), belop.verdi());
    }

    public boolean erMindreEnn(Belop v) {
        return this.compareTo(v) < 0;
    }

    public boolean erStorreEnn(Belop v) {
        return this.compareTo(v) > 0;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Belop && verdi().equals(((Belop) o).verdi());
    }

    @Override
    public int hashCode() {
        return verdi().hashCode();
    }

    public Belop byttFortegn() {
        return new Belop(-verdi());
    }

    public Belop abs() {
        return new Belop(Math.abs(verdi()));
    }
}
