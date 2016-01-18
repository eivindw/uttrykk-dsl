package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.DiffUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;

/**
 *
 * @deprecated Bruk MultiBelopPlussMinusUttrykk i stedet
 */
@Deprecated
public class MultiBelopDiffUttrykk<K> extends DiffUttrykk<MultiBelop<K>, Uttrykk<MultiBelop<K>>,
  MultiBelopDiffUttrykk<K>>
  implements MultiBelopUttrykk<K> {

    public MultiBelopDiffUttrykk(Uttrykk<MultiBelop<K>> ledd1, Uttrykk<MultiBelop<K>> ledd2) {
        super(ledd1, ledd2);
    }
}