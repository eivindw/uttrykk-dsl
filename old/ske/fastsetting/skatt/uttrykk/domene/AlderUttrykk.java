package ske.fastsetting.skatt.uttrykk.domene;

import ske.fastsetting.skatt.domene.Person;
import ske.fastsetting.skatt.uttrykk.BolskOldUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkBeskriver;

public class AlderUttrykk {
    private final int alder;

    private AlderUttrykk(int alder) {
        this.alder = alder;
    }

    public static AlderUttrykk alderTil(Person person) {
        return new AlderUttrykk(person.getAlder());
    }

    public BolskOldUttrykk erMellom(int fraAlder, int tilAlder) {
        return new AlderMellomOldUttrykk(alder,fraAlder,tilAlder);
    }

    private static class AlderMellomOldUttrykk implements BolskOldUttrykk {
        private final int alder;
        private final int fraAlder;
        private final int tilAlder;

        public AlderMellomOldUttrykk(int alder, int fraAlder, int tilAlder) {
            this.alder = alder;
            this.fraAlder = fraAlder;
            this.tilAlder = tilAlder;
        }

        @Override
        public Boolean evaluer() {
            return alder>fraAlder && alder<tilAlder;
        }

        @Override
        public void beskrivEvaluering (UttrykkBeskriver writer) {
            writer.skriv("skattyters alder (" + alder + " år) " + (evaluer() ? "er mellom " : "ikke er mellom ") + fraAlder + " og " + tilAlder + " år");
        }

        @Override
        public void beskrivGenerisk(UttrykkBeskriver beskriver) {
            beskriver.skriv("skattyters alder er mellom " + fraAlder + " og " + tilAlder + " år");
        }
    }
}
