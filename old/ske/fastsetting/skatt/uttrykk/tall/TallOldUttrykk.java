package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.CompareableOldUttrykk;

public interface TallOldUttrykk extends CompareableOldUttrykk<Tall> {
    default TallOldUttrykk multiplisertMed(TallOldUttrykk verdi) {
        return new TallMultiplikasjonsOldUttrykk(this, verdi);
    }

    default TallOldUttrykk dividertMed(TallOldUttrykk verdi) {
        return new TallDivisjonsOldUttrykk(this, verdi);
    }


}
