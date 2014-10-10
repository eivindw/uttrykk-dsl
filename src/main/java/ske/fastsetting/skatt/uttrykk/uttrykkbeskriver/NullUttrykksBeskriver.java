package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import ske.fastsetting.skatt.domene.Regel;
import ske.fastsetting.skatt.uttrykk.UttrykkBeskriver;

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
