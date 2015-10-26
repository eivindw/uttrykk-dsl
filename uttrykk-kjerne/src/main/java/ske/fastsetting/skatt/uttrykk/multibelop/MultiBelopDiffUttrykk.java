package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.DiffUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;

/**
 *
 * @deprecated Bruk MultiBelopPlussMinusUttrykk i stedet
 */
@Deprecated
public class MultiBelopDiffUttrykk<K> extends DiffUttrykk<StedbundetBelop<K>, Uttrykk<StedbundetBelop<K>>,
  MultiBelopDiffUttrykk<K>>
  implements MultiBelopUttrykk<K> {

    public MultiBelopDiffUttrykk(Uttrykk<StedbundetBelop<K>> ledd1, Uttrykk<StedbundetBelop<K>> ledd2) {
        super(ledd1, ledd2);
    }
}