package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.GrenseUttrykk;

public class MultiBelopForholdsmessigGrenseUttrykk<K> extends GrenseUttrykk<MultiBelop<K>, Belop, MultiBelopForholdsmessigGrenseUttrykk<K>>
  implements MultiBelopUttrykk<K> {


    protected MultiBelopForholdsmessigGrenseUttrykk(MultiBelopUttrykk<K> grunnlag) {
        super(grunnlag);
    }

    public static <K> MultiBelopForholdsmessigGrenseUttrykk<K> begrensFordholdmessig(MultiBelopUttrykk<K> grunnlag) {
        return new MultiBelopForholdsmessigGrenseUttrykk<>(grunnlag);
    }

    @Override
    protected MultiBelop<K> begrensNedad(MultiBelop<K> verdi, Belop min) {
        return verdi.tilBelop().erMindreEnn(min) ? verdi.nyMedSammeFordeling(min) : verdi;
    }

    @Override
    protected MultiBelop<K> begrensOppad(MultiBelop<K> verdi, Belop max) {
        return verdi.tilBelop().erStorreEnn(max) ? verdi.nyMedSammeFordeling(max) : verdi;
    }

}
