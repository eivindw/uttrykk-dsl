package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.DivisjonsUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public class MultiBelopDivisjonsUttrykk<K> extends DivisjonsUttrykk<MultiBelop<K>,
  MultiBelopUttrykk<K>, MultiBelopDivisjonsUttrykk<K>> implements MultiBelopUttrykk<K> {
    public MultiBelopDivisjonsUttrykk(MultiBelopUttrykk<K> multiBelopUttrykk, TallUttrykk tall) {
        super(multiBelopUttrykk, tall);
    }
}
