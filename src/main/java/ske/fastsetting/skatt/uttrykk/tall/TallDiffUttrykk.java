package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.DiffUttrykk;

public class TallDiffUttrykk<C>
    extends DiffUttrykk<Tall, TallUttrykk<C>, TallDiffUttrykk<C>, C>
    implements TallUttrykk<C>
{

    public TallDiffUttrykk(TallUttrykk<C> ledd1, TallUttrykk<C> ledd2) {
        super(ledd1,ledd2);
    }

    public static <C> TallDiffUttrykk<C> diff(TallUttrykk<C> ledd1, TallUttrykk<C> ledd2) {
        return new TallDiffUttrykk<C>(ledd1,ledd2);
    }
}
