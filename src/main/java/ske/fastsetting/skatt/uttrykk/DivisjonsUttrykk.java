package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.KalkulerbarVerdi;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public abstract class DivisjonsUttrykk<V extends KalkulerbarVerdi<V>, T extends Uttrykk<V, C>, B extends DivisjonsUttrykk<V,T,B,C>, C>
    extends AbstractUttrykk<V, B, C>
{
    protected final T divident;
    protected final TallUttrykk<C> divisor;

    protected DivisjonsUttrykk(T divident, TallUttrykk<C> tallUttrykk) {
        this.divident = divident;
        this.divisor = tallUttrykk;
    }

    @Override
    public V eval(UttrykkContext<C> ctx) {
        return ctx.eval(divident).dividertMed(ctx.eval(divisor).toBigDecimal());
    }

    @Override
    public String beskriv(UttrykkContext<C> ctx) {
        return ctx.beskriv(divident) + " dividert med " + ctx.beskriv(divisor);
    }
}