package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.DivisjonsUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallOldUttrykk;

public class BelopDivisjonsOldUttrykk extends DivisjonsUttrykk<Belop, BelopOldUttrykk, BelopDivisjonsOldUttrykk> implements BelopOldUttrykk {
    public BelopDivisjonsOldUttrykk(BelopOldUttrykk beloputtrykk, TallOldUttrykk tallUttrykk) {
        super(beloputtrykk,tallUttrykk);
    }
}
