package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetBelopUttrykk;

public class GrenseUttrykk extends AbstractUttrykk<Belop, GrenseUttrykk> implements BelopUttrykk {

    private final BelopUttrykk uttrykk;
    protected BelopUttrykk minimum;
    protected BelopUttrykk maksimum;

    protected GrenseUttrykk(BelopUttrykk uttrykk) {
        this.uttrykk = uttrykk;
    }

    public static GrenseUttrykk nedre0(BelopUttrykk uttrykk) {
        return begrens(uttrykk).nedad(KroneUttrykk.KR_0);
    }

    public static GrenseUttrykk begrens(BelopUttrykk uttrykk) {
        return new GrenseUttrykk(uttrykk);
    }

    public GrenseUttrykk nedad(BelopUttrykk minimum) {
        this.minimum = minimum;
        return this;
    }

    public GrenseUttrykk nedad(StedbundetBelopUttrykk minimum) {
        this.minimum = minimum.steduavhengig();
        return this;
    }

    public GrenseUttrykk oppad(BelopUttrykk maksimum) {
        this.maksimum = maksimum;
        return this;
    }

    public GrenseUttrykk oppad(StedbundetBelopUttrykk maksimum) {
        this.maksimum = maksimum.steduavhengig();
        return this;
    }

    @Override
    public Belop eval(UttrykkContext ctx) {
        final Belop e = ctx.eval(uttrykk);
        if (null != minimum) {
            Belop min = ctx.eval(minimum);
            if (e.erMindreEnn(min)) {
                return min;
            }
        }
        if (null != maksimum) {
            Belop max = ctx.eval(maksimum);
            if (e.erStorreEnn(max)) {
                return max;
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
            stringBuilder.append(ctx.beskriv(minimum));
        }
        if (null != maksimum) {
            stringBuilder.append(" begrenset oppad til ");
            stringBuilder.append(ctx.beskriv(maksimum));
        }
        return stringBuilder.toString();
    }
}
