package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.GrenseUttrykk;

public class MultiBelopForholdsmessigGrenseUttrykk<K> extends GrenseUttrykk<StedbundetBelop<K>, Belop, MultiBelopForholdsmessigGrenseUttrykk<K>>
  implements MultiBelopUttrykk<K> {


    protected MultiBelopForholdsmessigGrenseUttrykk(MultiBelopUttrykk<K> grunnlag) {
        super(grunnlag);
    }

    public static <K> MultiBelopForholdsmessigGrenseUttrykk<K> begrensFordholdmessig(MultiBelopUttrykk<K> grunnlag) {
        return new MultiBelopForholdsmessigGrenseUttrykk<>(grunnlag);
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
