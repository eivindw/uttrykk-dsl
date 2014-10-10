package ske.fastsetting.skatt.domene;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;


public class Belop implements Comparable<Belop>, KalkulerbarVerdi<Belop>  {

    public static final Belop NULL = new Belop(0);
    private final Integer belop;


    public Belop(int belop) {
        this.belop = belop;
    }

    public String toString() {
        return "kr "+belop;
    }

    public Belop pluss(Belop ledd) {
        return new Belop(ledd !=null? this.belop+ ledd.belop : this.belop);
    }

    public Belop minus(Belop ledd) {
        return new Belop(this.belop- ledd.belop);
    }

    public int sammenliknMed(Belop verdi) {
        return belop.compareTo(verdi.belop);
    }

    public Belop multiplisertMed(BigDecimal ledd) {
        return new Belop(ledd.multiply(BigDecimal.valueOf(belop)).intValue());
    }

    public Belop dividertMed(BigDecimal ledd) {
        MathContext kontekst = new MathContext(6, RoundingMode.HALF_UP);
        return new Belop(BigDecimal.valueOf(belop).divide(ledd,kontekst).intValue());
    }

    public Integer toInteger() {
        return this.belop;
    }

    public BigDecimal dividertMed(Belop divident) {
        MathContext kontekst = new MathContext(6, RoundingMode.HALF_UP);
        return BigDecimal.valueOf(this.belop).divide(BigDecimal.valueOf(divident.belop),kontekst);
    }

    @Override
    public int compareTo(Belop verdi) {
        return belop.compareTo(verdi.belop);
    }

    public boolean erMindreEnn(Belop v) {
        // TODO Hiv exception dersom v er null
        return this.compareTo(v) < 0;
    }

    public boolean erStorreEnn(Belop v) {
        // TODO Hiv exception dersom v er null
        return this.compareTo(v) > 0;
    }

}
