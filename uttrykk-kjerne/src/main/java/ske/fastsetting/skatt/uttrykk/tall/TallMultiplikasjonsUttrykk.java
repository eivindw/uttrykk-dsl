package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.MultiplikasjonsUttrykk;

public class TallMultiplikasjonsUttrykk
  extends MultiplikasjonsUttrykk<Tall, TallUttrykk, TallMultiplikasjonsUttrykk>
  implements TallUttrykk {
    public TallMultiplikasjonsUttrykk(TallUttrykk faktor1, TallUttrykk faktor2) {
        super(faktor1, faktor2);
    }
}