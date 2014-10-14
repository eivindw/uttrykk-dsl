package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.beregn.UttrykkContext;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.RegelUttrykk;

public class GrenseUttrykk extends RegelUttrykk<GrenseUttrykk, Belop> implements BelopUttrykk {

    private final BelopUttrykk uttrykk;
    private BelopUttrykk minimum;
    private BelopUttrykk maksimum;

    private GrenseUttrykk(BelopUttrykk uttrykk) {
        this.uttrykk = uttrykk;
    }

    public static GrenseUttrykk begrens(BelopUttrykk uttrykk) {
        return new GrenseUttrykk(uttrykk);
    }

    public GrenseUttrykk nedad(BelopUttrykk minimum) {
        this.minimum = minimum;
        return this;
    }

    public GrenseUttrykk oppad(BelopUttrykk maksimum) {
        this.maksimum = maksimum;
        return this;
    }

    @Override
    public Belop eval(UttrykkContext ctx) {
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
    public String beskriv(UttrykkContext ctx) {
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
