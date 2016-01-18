package ske.fastsetting.skatt.uttrykk.multibelop;

import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr0;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.GrenseUttrykk;

public class MultiBelopHverGrenseUttrykk<K> extends GrenseUttrykk<MultiBelop<K>, Belop,
  MultiBelopHverGrenseUttrykk<K>> implements MultiBelopUttrykk<K> {


    protected MultiBelopHverGrenseUttrykk(MultiBelopUttrykk<K> grunnlag) {
        super(grunnlag);
    }

    public static <K> MultiBelopHverGrenseUttrykk<K> nedre0(MultiBelopUttrykk<K> grunnlag) {
        return begrensHvertSted(grunnlag).nedad(kr0());
    }

    public static <K> MultiBelopHverGrenseUttrykk<K> begrensHvertSted(MultiBelopUttrykk<K> grunnlag) {
        return new MultiBelopHverGrenseUttrykk<>(grunnlag);
    }

    @Override
    protected MultiBelop<K> begrensNedad(MultiBelop<K> verdi, Belop min) {
        return verdi.splitt().stream()
          .map(bs -> bs.getBelop().erMindreEnn(min) ? MultiBelop.kr(min, bs.getNokkel()) : MultiBelop.kr(bs
            .getBelop(), bs.getNokkel()))
          .reduce(MultiBelop.kr0(), MultiBelop::pluss);
    }

    @Override
    protected MultiBelop<K> begrensOppad(MultiBelop<K> verdi, Belop max) {
        return verdi.splitt().stream()
          .map(bs -> bs.getBelop().erStorreEnn(max) ? MultiBelop.kr(max, bs.getNokkel()) : MultiBelop.kr(bs
            .getBelop(), bs.getNokkel()))
          .reduce(MultiBelop.kr0(), MultiBelop::pluss);

    }
}
