package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.DiffUttrykk;

public class StedbundetBelopDiffUttrykk<K> extends DiffUttrykk<StedbundetBelop<K>, StedbundetBelopUttrykk<K>, StedbundetBelopDiffUttrykk<K>>
        implements StedbundetBelopUttrykk<K> {

    public StedbundetBelopDiffUttrykk(StedbundetBelopUttrykk<K> ledd1, StedbundetBelopUttrykk<K> ledd2) {
        super(ledd1, ledd2);
    }
}