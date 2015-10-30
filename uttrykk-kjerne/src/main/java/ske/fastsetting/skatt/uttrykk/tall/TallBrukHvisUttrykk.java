package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.BrukHvisUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;

public class TallBrukHvisUttrykk extends BrukHvisUttrykk<Tall,TallBrukHvisUttrykk> implements TallUttrykk {
    public TallBrukHvisUttrykk(Uttrykk<Tall> uttrykk) {
        super(uttrykk);
    }

    public static TallBrukHvisUttrykk bruk(Uttrykk<Tall> uttrykk) {
        return new TallBrukHvisUttrykk(uttrykk);
    }
}
