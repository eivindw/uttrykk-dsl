package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.KalkulerbarVerdi;

public abstract class DiffUttrykk<V extends KalkulerbarVerdi<V>, T extends Uttrykk<V, C>, B extends DiffUttrykk<V,T,B,C>, C>
    extends AbstractUttrykk<V, B, C>
{
    private final T ledd1;
    private final T ledd2;

    protected DiffUttrykk(T ledd1, T ledd2) {
        this.ledd1 = ledd1;
        this.ledd2 = ledd2;
    }

    @Override
    public V eval(UttrykkContext<C> ctx) {
        return ctx.eval(ledd1).minus(ctx.eval(ledd2));
    }

    @Override
    public String beskriv(UttrykkContext<C> ctx) {
        return ctx.beskriv(ledd1) + " - " + ctx.beskriv(ledd2);
    }
}
