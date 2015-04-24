package ske.fastsetting.skatt.uttrykk.belop;



import java.math.BigDecimal;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class BelopAvrundOppTilDeleligMedUttrykk extends AbstractUttrykk<Belop, BelopAvrundOppTilDeleligMedUttrykk> implements BelopUttrykk {

    private BelopUttrykk belopUttrykk;
    private BigDecimal verdi;

    public BelopAvrundOppTilDeleligMedUttrykk (BelopUttrykk belopUttrykk, BigDecimal verdi) {
        this.belopUttrykk = belopUttrykk;
        this.verdi = verdi;
    }

    @Override
    public Belop eval(UttrykkContext uttrykkContext) {

        BigDecimal LEGGTILL = new BigDecimal(0.999);

        BigDecimal tall = uttrykkContext.eval(belopUttrykk).toBigDecimal();

        BigDecimal b = tall.setScale(3);
        BigDecimal c = b.divide(verdi);
        BigDecimal d = c.add(LEGGTILL);
        BigDecimal e = d.setScale(3, d.ROUND_HALF_UP);
        BigDecimal f = e.setScale(0, d.ROUND_DOWN);
        BigDecimal g = f.multiply(verdi);



        return Belop.kr(g.toBigInteger());
    }

    @Override
    public String beskriv(UttrykkContext uttrykkContext) {
        return "rund av opp til næremeste tall delelig med (" + uttrykkContext.beskriv(belopUttrykk) + ")";
    }


}
