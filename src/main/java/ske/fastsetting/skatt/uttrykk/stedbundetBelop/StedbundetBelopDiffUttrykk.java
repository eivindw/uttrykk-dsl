package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.DiffUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;

public class StedbundetBelopDiffUttrykk<K> extends DiffUttrykk<StedbundetBelop<K>, Uttrykk<StedbundetBelop<K>>, StedbundetBelopDiffUttrykk<K>>
        implements StedbundetBelopUttrykk<K> {

    public StedbundetBelopDiffUttrykk(Uttrykk<StedbundetBelop<K>> ledd1, Uttrykk<StedbundetBelop<K>> ledd2) {
        super(ledd1, ledd2);
    }
}