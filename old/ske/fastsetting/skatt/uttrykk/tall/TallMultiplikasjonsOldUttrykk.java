package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.MultiplikasjonsUttrykk;

public class TallMultiplikasjonsOldUttrykk extends MultiplikasjonsUttrykk<Tall, TallOldUttrykk, TallMultiplikasjonsOldUttrykk> implements TallOldUttrykk {
    public TallMultiplikasjonsOldUttrykk(TallOldUttrykk faktor1, TallOldUttrykk faktor2) {
        super(faktor1, faktor2);
    }
}