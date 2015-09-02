package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.bolsk.BolskUttrykk;
import ske.fastsetting.skatt.uttrykk.HvisUttrykk;

public class TallHvisUttrykk extends HvisUttrykk<Tall, TallHvisUttrykk> implements TallUttrykk {

    public static BrukUttrykk<Tall, TallHvisUttrykk> hvis(Uttrykk<Boolean> bolskUttrykk) {
        return new BrukUttrykk<>(bolskUttrykk, new TallHvisUttrykk());
    }

}
