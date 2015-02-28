package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.MultiplikasjonsUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

/**
* Created by jorn ola birkeland on 24.02.15.
*/
public class StedbundetBelopMultiplikasjonsUttrykk<K> extends MultiplikasjonsUttrykk<StedbundetBelop<K>,StedbundetBelopUttrykk<K>,StedbundetBelopMultiplikasjonsUttrykk<K>> implements StedbundetBelopUttrykk<K> {
    public StedbundetBelopMultiplikasjonsUttrykk(StedbundetBelopUttrykk<K> stedbundetBelopUttrykk, TallUttrykk tall) {
        super(stedbundetBelopUttrykk,tall);
    }
}
