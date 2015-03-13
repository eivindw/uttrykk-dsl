package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;

public class StebundetGrenseUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>, StebundetGrenseUttrykk<K>> implements StedbundetBelopUttrykk<K> {

    private final StedbundetBelopUttrykk<K> uttrykk;
    protected BelopUttrykk minimum;
    protected BelopUttrykk maksimum;

    protected StebundetGrenseUttrykk(StedbundetBelopUttrykk<K> uttrykk) {
        this.uttrykk = uttrykk;
    }

    @Deprecated
    public static <K >StebundetGrenseUttrykk<K> nedre0(StedbundetBelopUttrykk<K> uttrykk) {
        return begrens(uttrykk).nedadProporsjonalt(KroneUttrykk.KR_0);
    }

    public static <K> StebundetGrenseUttrykk<K> begrens(StedbundetBelopUttrykk<K> uttrykk) {
        return new StebundetGrenseUttrykk<>(uttrykk);
    }

    public StebundetGrenseUttrykk<K> nedadProporsjonalt(BelopUttrykk minimum) {
        this.minimum = minimum;
        return this;
    }

    public StebundetGrenseUttrykk<K> nedadProporsjonalt(StedbundetBelopUttrykk<K> minimum) {
        this.minimum = minimum.steduavhengig();
        return this;
    }

    public StebundetGrenseUttrykk<K> oppadProporsjonalt(BelopUttrykk maksimum) {
        this.maksimum = maksimum;
        return this;
    }

    public StebundetGrenseUttrykk<K> oppadProporsjonalt(StedbundetBelopUttrykk maksimum) {
        this.maksimum = maksimum.steduavhengig();
        return this;
    }

    @Override
    public StedbundetBelop<K> eval(UttrykkContext ctx) {
        final StedbundetBelop<K> stedbundetBelop = ctx.eval(uttrykk);

        if (null != minimum) {
            Belop min = ctx.eval(minimum);
            if (stedbundetBelop.steduavhengig().erMindreEnn(min)) {
                return stedbundetBelop.nyMedSammeFordeling(min);
            }
        }
        if (null != maksimum) {
            Belop max = ctx.eval(maksimum);
            if (stedbundetBelop.steduavhengig().erStorreEnn(max)) {
                return stedbundetBelop.nyMedSammeFordeling(max);
            }
        }
        return stedbundetBelop;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        StringBuilder stringBuilder = new StringBuilder(ctx.beskriv(uttrykk));
        if (null == minimum && null == maksimum) {
            stringBuilder.append(" Advarsel: Uttrykket mangler øvre og/eller nedre grense ");
        }
        if (null != minimum) {
            stringBuilder.append(" begrenset nedad proporsjonalt til ");
            stringBuilder.append(ctx.beskriv(minimum));
        }
        if (null != maksimum) {
            stringBuilder.append(" begrenset oppad proporsjonalt til ");
            stringBuilder.append(ctx.beskriv(maksimum));
        }
        return stringBuilder.toString();
    }

}
