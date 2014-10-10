package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.BolskUttrykk;
import ske.fastsetting.skatt.uttrykk.HvisUttrykk;

public class TallHvisUttrykk extends HvisUttrykk<Tall, TallHvisUttrykk> implements TallUttrykk {

    public static BrukUttrykk<Tall,TallHvisUttrykk> hvis(BolskUttrykk bolskUttrykk) {
        return new BrukUttrykk<>(bolskUttrykk, new TallHvisUttrykk());
    }

}
