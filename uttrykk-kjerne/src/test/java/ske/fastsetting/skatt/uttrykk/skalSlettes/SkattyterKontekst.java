package ske.fastsetting.skatt.uttrykk.skalSlettes;

import ske.fastsetting.skatt.uttrykk.TestUttrykkContext;
import ske.fastsetting.skatt.uttrykk.Uttrykk;

class SkattyterKontekst extends TestUttrykkContext {

    protected SkattyterKontekst(Object[] input) {
        super(input);
    }

    public static SkattyterKontekst ny(Object... input) {
        return new SkattyterKontekst(input);
    }

    public <T> T verdiAv(Uttrykk<T> uttrykk) {
        return beregne(uttrykk).verdi();
    }
}
