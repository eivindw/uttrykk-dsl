package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.DivisjonsUttrykk;

public class TallDivisjonsUttrykk
  extends DivisjonsUttrykk<Tall, TallUttrykk, TallDivisjonsUttrykk>
  implements TallUttrykk {
    public TallDivisjonsUttrykk(TallUttrykk beloputtrykk, TallUttrykk tallUttrykk) {
        super(beloputtrykk, tallUttrykk);
    }
}
