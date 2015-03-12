package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.DivisjonsUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public class StedbundetBelopDivisjonsUttrykk<K> extends DivisjonsUttrykk<StedbundetBelop<K>,StedbundetBelopUttrykk<K>,StedbundetBelopDivisjonsUttrykk<K>> implements StedbundetBelopUttrykk<K> {
    public StedbundetBelopDivisjonsUttrykk(StedbundetBelopUttrykk<K> stedbundetBelopUttrykk, TallUttrykk tall) {
        super(stedbundetBelopUttrykk,tall);
    }
}
