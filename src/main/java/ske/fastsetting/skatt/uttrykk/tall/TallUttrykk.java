package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.CompareableUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;

public interface TallUttrykk<B extends TallUttrykk> extends CompareableUttrykk<Tall, B> {
    default TallUttrykk multiplisertMed(TallUttrykk verdi) {
        return new TallMultiplikasjonsUttrykk(this, verdi);
    }

    default TallUttrykk dividertMed(TallUttrykk verdi) {
        return new TallDivisjonsUttrykk(this, verdi);
    }
}
