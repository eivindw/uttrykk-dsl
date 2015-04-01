package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr0;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.GrenseUttrykk;

public class StebundetHverGrenseUttrykk<K> extends GrenseUttrykk<StedbundetBelop<K>, Belop,
  StebundetHverGrenseUttrykk<K>> implements StedbundetBelopUttrykk<K> {


    protected StebundetHverGrenseUttrykk(StedbundetBelopUttrykk<K> grunnlag) {
        super(grunnlag);
    }

    public static <K> StebundetHverGrenseUttrykk<K> nedre0(StedbundetBelopUttrykk<K> grunnlag) {
        return begrensHvertSted(grunnlag).nedad(kr0());
    }

    public static <K> StebundetHverGrenseUttrykk<K> begrensHvertSted(StedbundetBelopUttrykk<K> grunnlag) {
        return new StebundetHverGrenseUttrykk<>(grunnlag);
    }

    @Override
    protected StedbundetBelop<K> begrensNedad(StedbundetBelop<K> verdi, Belop min) {
        return verdi.splitt().stream()
          .map(bs -> bs.getBelop().erMindreEnn(min) ? StedbundetBelop.kr(min, bs.getSted()) : StedbundetBelop.kr(bs
            .getBelop(), bs.getSted()))
          .reduce(StedbundetBelop.kr0(), StedbundetBelop::pluss);
    }

    @Override
    protected StedbundetBelop<K> begrensOppad(StedbundetBelop<K> verdi, Belop max) {
        return verdi.splitt().stream()
          .map(bs -> bs.getBelop().erStorreEnn(max) ? StedbundetBelop.kr(max, bs.getSted()) : StedbundetBelop.kr(bs
            .getBelop(), bs.getSted()))
          .reduce(StedbundetBelop.kr0(), StedbundetBelop::pluss);

    }
}
