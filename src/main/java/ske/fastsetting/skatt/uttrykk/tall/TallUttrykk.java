package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.CompareableUttrykk;

public interface TallUttrykk<B extends TallUttrykk<B,C>, C> extends CompareableUttrykk<Tall, B, C> {
    default TallUttrykk<?,C> multiplisertMed(TallUttrykk<?,C> verdi) {
        return new TallMultiplikasjonsUttrykk<>(this, verdi);
    }

    default TallUttrykk<?,C> dividertMed(TallUttrykk<?,C> verdi) {
        return new TallDivisjonsUttrykk<>(this, verdi);
    }
}
