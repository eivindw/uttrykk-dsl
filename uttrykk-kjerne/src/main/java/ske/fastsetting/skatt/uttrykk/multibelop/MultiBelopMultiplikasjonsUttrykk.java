package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.MultiplikasjonsUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;

public class MultiBelopMultiplikasjonsUttrykk<K> extends MultiplikasjonsUttrykk<StedbundetBelop<K>,
  Uttrykk<StedbundetBelop<K>>, MultiBelopMultiplikasjonsUttrykk<K>> implements MultiBelopUttrykk<K> {
    public MultiBelopMultiplikasjonsUttrykk(Uttrykk<StedbundetBelop<K>> stedbundetBelopUttrykk, Uttrykk<Tall>
      tall) {
        super(stedbundetBelopUttrykk, tall);
    }
}
