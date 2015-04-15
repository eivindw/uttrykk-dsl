package ske.fastsetting.skatt.uttrykk.belop;


import java.math.BigDecimal;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class BelopAvrundNedTilNaermeste100Uttrykk extends AbstractUttrykk<Belop, BelopAvrundNedTilNaermeste100Uttrykk> implements BelopUttrykk {

    private BelopUttrykk belopUttrykk;
    private static final BigDecimal HUNDRE = new BigDecimal(100);

      public BelopAvrundNedTilNaermeste100Uttrykk(BelopUttrykk belopUttrykk) {
        this.belopUttrykk = belopUttrykk;
    }

      @Override
      public Belop eval(UttrykkContext uttrykkContext) {

        BigDecimal tall = uttrykkContext.eval(belopUttrykk).toBigDecimal();

        BigDecimal b = tall.setScale(3, tall.ROUND_DOWN);
        BigDecimal c = b.divide(HUNDRE);
        BigDecimal d = c.setScale(0, c.ROUND_DOWN);
        BigDecimal e = d.multiply(HUNDRE);

        return Belop.kr(e.toBigInteger());
      }

      @Override
      public String beskriv(UttrykkContext uttrykkContext) {
        return "rund av ned til næremeste 100 delelig med (" + uttrykkContext.beskriv(belopUttrykk) + ")";
      }
}