package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;

public class StebundetHverGrenseUttrykk<K> extends AbstractUttrykk<StedbundetBelop<K>, StebundetHverGrenseUttrykk<K>> implements StedbundetBelopUttrykk<K> {

    private final StedbundetBelopUttrykk<K> uttrykk;
    protected BelopUttrykk minimumHver;
    protected BelopUttrykk maksimumHver;

    protected StebundetHverGrenseUttrykk(StedbundetBelopUttrykk<K> uttrykk) {
        this.uttrykk = uttrykk;
    }

    public static <K >StebundetHverGrenseUttrykk<K> nedre0(StedbundetBelopUttrykk<K> uttrykk) {
        return begrensHvertSted(uttrykk).nedad(KroneUttrykk.KR_0);
    }

    public static <K> StebundetHverGrenseUttrykk<K> begrensHvertSted(StedbundetBelopUttrykk<K> uttrykk) {
        return new StebundetHverGrenseUttrykk<>(uttrykk);
    }

    public StebundetHverGrenseUttrykk<K> nedad(BelopUttrykk minimum) {
        this.minimumHver = minimum;
        return this;
    }

    public StebundetHverGrenseUttrykk<K> oppad(BelopUttrykk maksimum) {
        this.maksimumHver = maksimum;
        return this;
    }

    @Override
    public StedbundetBelop<K> eval(UttrykkContext ctx) {
        final StedbundetBelop<K> stedbundetBelop = ctx.eval(uttrykk);

        StedbundetBelop<K> resultat = StedbundetBelop.kr0();

        if (null != minimumHver) {
            Belop min = ctx.eval(minimumHver);
            resultat = stedbundetBelop.splitt().stream()
                    .map(bs->bs.getBelop().erMindreEnn(min) ? StedbundetBelop.kr(min,bs.getSted()) : StedbundetBelop.kr(bs.getBelop(),bs.getSted()))
                    .reduce(StedbundetBelop.kr0(), StedbundetBelop::pluss);
        }
        if (null != maksimumHver) {
            Belop max = ctx.eval(maksimumHver);
            resultat = resultat.splitt().stream()
                    .map(bs->bs.getBelop().erStorreEnn(max) ? StedbundetBelop.kr(max,bs.getSted()) : StedbundetBelop.kr(bs.getBelop(),bs.getSted()))
                    .reduce(StedbundetBelop.kr0(), StedbundetBelop::pluss);
        }
        return resultat;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        StringBuilder stringBuilder = new StringBuilder(ctx.beskriv(uttrykk));
        if (null == minimumHver && null == maksimumHver) {
            stringBuilder.append(" Advarsel: Uttrykket mangler øvre og/eller nedre grense ");
        }
        if (null != minimumHver) {
            stringBuilder.append(" begrenset nedad til ");
            stringBuilder.append(ctx.beskriv(minimumHver));
        }
        if (null != maksimumHver) {
            stringBuilder.append(" begrenset oppad til ");
            stringBuilder.append(ctx.beskriv(maksimumHver));
        }
        return stringBuilder.toString();
    }

}
