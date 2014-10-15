package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.DivisjonsUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public class BelopDivisjonsUttrykk
    extends DivisjonsUttrykk<Belop, BelopUttrykk<?>, BelopDivisjonsUttrykk>
    implements BelopUttrykk<BelopDivisjonsUttrykk>
{
    public BelopDivisjonsUttrykk(BelopUttrykk beloputtrykk, TallUttrykk tallUttrykk) {
        super(beloputtrykk,tallUttrykk);
    }
}
