package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.KalkulerbarVerdi;
import ske.fastsetting.skatt.domene.Tall;

public abstract class MultiplikasjonsUttrykk<V extends KalkulerbarVerdi<V>, T extends Uttrykk<V>, B extends
  MultiplikasjonsUttrykk<V, T, B>>
  extends AbstractUttrykk<V, B> {
    protected final T faktor1;
    protected final Uttrykk<Tall> faktor2;

    protected MultiplikasjonsUttrykk(T faktor1, Uttrykk<Tall> faktor2) {
        this.faktor1 = faktor1;
        this.faktor2 = faktor2;
    }

    @Override
    public V eval(UttrykkContext ctx) {
        return ctx.eval(faktor1).multiplisertMed(ctx.eval(faktor2).toBigDecimal());
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(faktor1) + " * " + ctx.beskriv(faktor2);
    }
}
