package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.MultiplikasjonsUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;

public class MultiBelopMultiplikasjonsUttrykk<K> extends MultiplikasjonsUttrykk<MultiBelop<K>,
  Uttrykk<MultiBelop<K>>, MultiBelopMultiplikasjonsUttrykk<K>> implements MultiBelopUttrykk<K> {
    public MultiBelopMultiplikasjonsUttrykk(Uttrykk<MultiBelop<K>> stedbundetBelopUttrykk, Uttrykk<Tall>
      tall) {
        super(stedbundetBelopUttrykk, tall);
    }
}
