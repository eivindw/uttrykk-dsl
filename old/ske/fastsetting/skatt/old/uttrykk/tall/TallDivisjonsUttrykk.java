package ske.fastsetting.skatt.old.uttrykk.tall;

import ske.fastsetting.skatt.old.domene.Tall;
import ske.fastsetting.skatt.old.uttrykk.DivisjonsUttrykk;

public class TallDivisjonsUttrykk extends DivisjonsUttrykk<Tall, TallUttrykk, TallDivisjonsUttrykk> implements TallUttrykk {
    public TallDivisjonsUttrykk(TallUttrykk beloputtrykk, TallUttrykk tallUttrykk) {
        super(beloputtrykk,tallUttrykk);
    }

}
