package ske.fastsetting.skatt.old.uttrykk.uttrykkbeskriver;

import ske.fastsetting.skatt.old.domene.Regel;
import ske.fastsetting.skatt.old.uttrykk.UttrykkBeskriver;

public class NullUttrykksBeskriver implements UttrykkBeskriver {
    @Override
    public UttrykkBeskriver overskrift(String overskrift) {
        return this;
    }

    @Override
    public void skriv(String line) {

    }

    @Override
    public UttrykkBeskriver rykkInn() {
        return this;
    }

    @Override
    public void tags(String... strings) {

    }

    @Override
    public void regler(Regel... regler) {

    }
}
