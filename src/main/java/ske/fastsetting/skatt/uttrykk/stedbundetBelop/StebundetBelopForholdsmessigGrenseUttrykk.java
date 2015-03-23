package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.GrenseUttrykk;

public class StebundetBelopForholdsmessigGrenseUttrykk<K> extends GrenseUttrykk<StedbundetBelop<K>, Belop, StebundetBelopForholdsmessigGrenseUttrykk<K>> implements StedbundetBelopUttrykk<K> {


    protected StebundetBelopForholdsmessigGrenseUttrykk(StedbundetBelopUttrykk<K> grunnlag) {
        super(grunnlag);
    }

    public static <K> StebundetBelopForholdsmessigGrenseUttrykk<K> begrensFordholdmessig(StedbundetBelopUttrykk<K> grunnlag) {
        return new StebundetBelopForholdsmessigGrenseUttrykk<>(grunnlag);
    }

    @Override
    protected StedbundetBelop<K> begrensNedad(StedbundetBelop<K> verdi, Belop min) {
        return verdi.steduavhengig().erMindreEnn(min) ? verdi.nyMedSammeFordeling(min) : verdi;
    }

    @Override
    protected StedbundetBelop<K> begrensOppad(StedbundetBelop<K> verdi, Belop max) {
        return verdi.steduavhengig().erStorreEnn(max) ? verdi.nyMedSammeFordeling(max) : verdi;
    }

}
