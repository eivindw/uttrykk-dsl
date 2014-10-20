package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;

public class GrenseUttrykk<C> extends AbstractUttrykk<Belop, GrenseUttrykk<C>, C> implements BelopUttrykk<C> {

    private final BelopUttrykk<C> uttrykk;
    private BelopUttrykk<C> minimum;
    private BelopUttrykk<C> maksimum;

    private GrenseUttrykk(BelopUttrykk<C> uttrykk) {
        this.uttrykk = uttrykk;
    }

    public static <C> GrenseUttrykk<C> begrens(BelopUttrykk<C> uttrykk) {
        return new GrenseUttrykk<C>(uttrykk);
    }

    public GrenseUttrykk nedad(BelopUttrykk<C> minimum) {
        this.minimum = minimum;
        return this;
    }

    public GrenseUttrykk oppad(BelopUttrykk<C> maksimum) {
        this.maksimum = maksimum;
        return this;
    }

    @Override
    public Belop eval(UttrykkContext<C> ctx) {
        Belop e = ctx.eval(uttrykk);
        if (null != minimum) {
            Belop min = ctx.eval(minimum);
            if (e.erMindreEnn(min)) {
                e = min;
            }
        }
        if (null != maksimum) {
            Belop max = ctx.eval(maksimum);
            if (e.erStorreEnn(max) ) {
                e = max;
            }
        }
        return e;
    }

    @Override
    public String beskriv(UttrykkContext<C> ctx) {
        StringBuilder stringBuilder = new StringBuilder(ctx.beskriv(uttrykk));
        if (null == minimum && null == maksimum) {
            stringBuilder.append(" Advarsel: Uttrykket mangler øvre og/eller nedre grense ");
        }
        if (null != minimum) {
            stringBuilder.append(" begrenset nedad til ");
            ctx.beskriv(minimum);
        }
        if (null != maksimum) {
            stringBuilder.append(" begrenset oppad til ");
            ctx.beskriv(maksimum);
        }
        return stringBuilder.toString();
    }
}
