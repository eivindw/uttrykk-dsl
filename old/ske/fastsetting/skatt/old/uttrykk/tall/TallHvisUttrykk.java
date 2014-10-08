package ske.fastsetting.skatt.old.uttrykk.tall;

import ske.fastsetting.skatt.old.domene.Tall;
import ske.fastsetting.skatt.old.uttrykk.BolskUttrykk;
import ske.fastsetting.skatt.old.uttrykk.HvisUttrykk;

public class TallHvisUttrykk extends HvisUttrykk<Tall, TallHvisUttrykk> implements TallUttrykk {

    public static BrukUttrykk<Tall,TallHvisUttrykk> hvis(BolskUttrykk bolskUttrykk) {
        return new BrukUttrykk<>(bolskUttrykk, new TallHvisUttrykk());
    }

}
