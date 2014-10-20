package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.DivisjonsUttrykk;

public class TallDivisjonsUttrykk<C>
    extends DivisjonsUttrykk<Tall, TallUttrykk<?,C>, TallDivisjonsUttrykk<C>,C>
    implements TallUttrykk<TallDivisjonsUttrykk<C>,C>
{
    public TallDivisjonsUttrykk(TallUttrykk<?,C> beloputtrykk, TallUttrykk<?,C> tallUttrykk) {
        super(beloputtrykk,tallUttrykk);
    }
}
