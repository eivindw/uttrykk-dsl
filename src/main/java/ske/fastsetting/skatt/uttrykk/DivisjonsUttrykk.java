package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.beregn.Uttrykk;
import ske.fastsetting.skatt.beregn.UttrykkContext;
import ske.fastsetting.skatt.domene.KalkulerbarVerdi;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public abstract class DivisjonsUttrykk<V extends KalkulerbarVerdi<V>, T extends Uttrykk<V>, B> extends RegelUttrykk<B, V>  {
    protected final T divident;
    protected final TallUttrykk divisor;

    protected DivisjonsUttrykk(T divident, TallUttrykk tallUttrykk) {
        this.divident = divident;
        this.divisor = tallUttrykk;
    }

    @Override
    public V eval(UttrykkContext ctx) {
        return ctx.eval(divident).dividertMed(ctx.eval(divisor).toBigDecimal());
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(divident) + " dividert med " + ctx.beskriv(divisor);
    }
}