package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.DiffUttrykk;

public class TallDiffOldUttrykk extends DiffUttrykk<Tall, TallOldUttrykk, TallDiffOldUttrykk> implements TallOldUttrykk {

    public TallDiffOldUttrykk(TallOldUttrykk ledd1, TallOldUttrykk ledd2) {
        super(ledd1,ledd2);
    }

    public static TallDiffOldUttrykk diff(TallOldUttrykk ledd1, TallOldUttrykk ledd2) {
        return new TallDiffOldUttrykk(ledd1,ledd2);
    }
}
