package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.DivisjonsUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public class BelopDivisjonsUttrykk<C>
    extends DivisjonsUttrykk<Belop, BelopUttrykk<C>, BelopDivisjonsUttrykk<C>, C>
    implements BelopUttrykk<C>
{
    public BelopDivisjonsUttrykk(BelopUttrykk<C> beloputtrykk, TallUttrykk<C> tallUttrykk) {
        super(beloputtrykk,tallUttrykk);
    }
}
