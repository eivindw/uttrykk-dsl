package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.DivisjonsUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

/**
* Created by jorn ola birkeland on 24.02.15.
*/
public class StedbundetBelopDivisjonsUttrykk extends DivisjonsUttrykk<StedbundetBelop,StedbundetBelopUttrykk,StedbundetBelopDivisjonsUttrykk> implements StedbundetBelopUttrykk {
    public StedbundetBelopDivisjonsUttrykk(StedbundetBelopUttrykk stedbundetBelopUttrykk, TallUttrykk tall) {
        super(stedbundetBelopUttrykk,tall);
    }
}
