package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Avrunding;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.CompareableUttrykk;

import static ske.fastsetting.skatt.uttrykk.tall.TallPlussMinusUttrykk.diff;
import static ske.fastsetting.skatt.uttrykk.tall.TallPlussMinusUttrykk.sum;

public interface TallUttrykk extends CompareableUttrykk<Tall> {
    default TallMultiplikasjonsUttrykk multiplisertMed(TallUttrykk verdi) {
        return new TallMultiplikasjonsUttrykk(this, verdi);
    }

    default TallDivisjonsUttrykk dividertMed(TallUttrykk verdi) {
        return new TallDivisjonsUttrykk(this, verdi);
    }

    default TallPlussMinusUttrykk pluss(TallUttrykk verdi) {
        return sum(this, verdi);
    }

    default TallPlussMinusUttrykk minus(TallUttrykk verdi) { return diff(this, verdi); }

    @Deprecated()
    default TallAvrundUttrykk rundOpp() {
        return new TallAvrundUttrykk(this, 2, Avrunding.Opp);
    }

    default TallAvrundUttrykk rundAv(int presisjon, Avrunding avrunding) {
        return new TallAvrundUttrykk(this, presisjon, avrunding);
    }

}
