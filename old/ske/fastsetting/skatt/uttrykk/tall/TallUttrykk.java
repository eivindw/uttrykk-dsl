package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.CompareableUttrykk;

public interface TallUttrykk extends CompareableUttrykk<Tall> {
    default TallUttrykk multiplisertMed(TallUttrykk verdi) {
        return new TallMultiplikasjonsUttrykk(this, verdi);
    }

    default TallUttrykk dividertMed(TallUttrykk verdi) {
        return new TallDivisjonsUttrykk(this, verdi);
    }


}
