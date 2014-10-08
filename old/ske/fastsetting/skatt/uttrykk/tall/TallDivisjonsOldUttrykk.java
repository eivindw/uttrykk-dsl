package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.DivisjonsUttrykk;

public class TallDivisjonsOldUttrykk extends DivisjonsUttrykk<Tall, TallOldUttrykk, TallDivisjonsOldUttrykk> implements TallOldUttrykk {
    public TallDivisjonsOldUttrykk(TallOldUttrykk beloputtrykk, TallOldUttrykk tallUttrykk) {
        super(beloputtrykk,tallUttrykk);
    }

}
