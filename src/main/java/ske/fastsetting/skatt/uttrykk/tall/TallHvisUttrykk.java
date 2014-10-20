package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.BolskUttrykk;
import ske.fastsetting.skatt.uttrykk.HvisUttrykk;

public class TallHvisUttrykk<C> extends HvisUttrykk<Tall, TallHvisUttrykk<C>, C> implements TallUttrykk<C> {

    public static <C> BrukUttrykk<Tall,TallHvisUttrykk<C>,C> hvis(BolskUttrykk<C> bolskUttrykk) {
        return new BrukUttrykk<>(bolskUttrykk, new TallHvisUttrykk<C>());
    }

}
