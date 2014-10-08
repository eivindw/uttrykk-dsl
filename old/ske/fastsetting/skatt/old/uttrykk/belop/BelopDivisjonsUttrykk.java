package ske.fastsetting.skatt.old.uttrykk.belop;

import ske.fastsetting.skatt.old.domene.Belop;
import ske.fastsetting.skatt.old.uttrykk.DivisjonsUttrykk;
import ske.fastsetting.skatt.old.uttrykk.tall.TallUttrykk;

public class BelopDivisjonsUttrykk extends DivisjonsUttrykk<Belop, BelopUttrykk, BelopDivisjonsUttrykk> implements BelopUttrykk {
    public BelopDivisjonsUttrykk(BelopUttrykk beloputtrykk, TallUttrykk tallUttrykk) {
        super(beloputtrykk,tallUttrykk);
    }
}
