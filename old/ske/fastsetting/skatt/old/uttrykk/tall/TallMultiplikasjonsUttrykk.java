package ske.fastsetting.skatt.old.uttrykk.tall;

import ske.fastsetting.skatt.old.domene.Tall;
import ske.fastsetting.skatt.old.uttrykk.MultiplikasjonsUttrykk;

public class TallMultiplikasjonsUttrykk extends MultiplikasjonsUttrykk<Tall, TallUttrykk, TallMultiplikasjonsUttrykk> implements TallUttrykk {
    public TallMultiplikasjonsUttrykk(TallUttrykk faktor1, TallUttrykk faktor2) {
        super(faktor1, faktor2);
    }
}