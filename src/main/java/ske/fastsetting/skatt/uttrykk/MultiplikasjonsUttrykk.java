package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.KalkulerbarVerdi;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public abstract class MultiplikasjonsUttrykk<V extends KalkulerbarVerdi<V>, T extends Uttrykk<V, ?, C>, B extends MultiplikasjonsUttrykk<V,T,B,C>, C>
    extends AbstractUttrykk<V, B, C>
{
    protected final T faktor1;
    protected final TallUttrykk<?,C> faktor2;

    protected MultiplikasjonsUttrykk(T faktor1, TallUttrykk<?,C> faktor2) {
        this.faktor1 = faktor1;
        this.faktor2 = faktor2;
    }

    @Override
    public V eval(UttrykkContext<C> ctx) {
        return ctx.eval(faktor1).multiplisertMed(ctx.eval(faktor2).toBigDecimal());
    }

    @Override
    public String beskriv(UttrykkContext<C> ctx) {
        return ctx.beskriv(faktor1) + " * " + ctx.beskriv(faktor2);
    }
}
