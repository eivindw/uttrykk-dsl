package ske.fastsetting.skatt.domene;

import org.javamoney.moneta.Money;

import java.math.BigDecimal;

public class Belop implements Comparable<Belop>, KalkulerbarVerdi<Belop> {

    public static final Belop NULL = new Belop(0);

    private final Money belop;

    public Belop(int belop) {
        this(Money.of(belop, "NOK"));
    }

    public Belop(Money belop) {
        this.belop = belop;
    }

    public String toString() {
        return "kr " + belop.getNumberStripped().toPlainString();
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
        return this.belop.getNumber().intValue();
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
