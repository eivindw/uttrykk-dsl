package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.MultiplikasjonsUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;

public class StedbundetBelopMultiplikasjonsUttrykk<K> extends MultiplikasjonsUttrykk<StedbundetBelop<K>,
  Uttrykk<StedbundetBelop<K>>, StedbundetBelopMultiplikasjonsUttrykk<K>> implements StedbundetBelopUttrykk<K> {
    public StedbundetBelopMultiplikasjonsUttrykk(Uttrykk<StedbundetBelop<K>> stedbundetBelopUttrykk, Uttrykk<Tall>
      tall) {
        super(stedbundetBelopUttrykk, tall);
    }
}
