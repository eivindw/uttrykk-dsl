package ske.fastsetting.skatt.beregn;

import ske.fastsetting.skatt.domene.KalkulerbarVerdi;
import ske.fastsetting.skatt.domene.Tall;

public class MultiplikasjonsUttrykk<T extends KalkulerbarVerdi<T>> extends AbstractUttrykk<T> {

    private final Uttrykk<T> uttrykk1;
    private final Uttrykk<Tall> uttrykk2;

    public MultiplikasjonsUttrykk(Uttrykk<T> uttrykk1, Uttrykk<Tall> uttrykk2) {
        this.uttrykk1 = uttrykk1;
        this.uttrykk2 = uttrykk2;
    }

    @Override
    public T eval(UttrykkContext ctx) {
        return uttrykk1.eval(ctx).multiplisertMed(uttrykk2.eval(ctx).toBigDecimal());
        //return (int)((int)ctx.eval(uttrykk1) * (double)ctx.eval(uttrykk2));
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(uttrykk1) + " * " + ctx.beskriv(uttrykk2);
    }

    public static <T extends KalkulerbarVerdi<T>> MultiplikasjonsUttrykk mult(
        Uttrykk<T> uttrykk1, Uttrykk<Tall> uttrykk2
    ) {
        return new MultiplikasjonsUttrykk<>(uttrykk1, uttrykk2);
    }
}
