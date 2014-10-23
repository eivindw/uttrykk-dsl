package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.DiffUttrykk;

public class TallDiffUttrykk
    extends DiffUttrykk<Tall, TallUttrykk, TallDiffUttrykk>
    implements TallUttrykk
{

    public TallDiffUttrykk(TallUttrykk ledd1, TallUttrykk ledd2) {
        super(ledd1, ledd2);
    }

    public static TallDiffUttrykk diff(TallUttrykk ledd1, TallUttrykk ledd2) {
        return new TallDiffUttrykk(ledd1, ledd2);
    }
}
