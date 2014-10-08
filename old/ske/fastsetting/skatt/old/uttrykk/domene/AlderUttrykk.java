package ske.fastsetting.skatt.old.uttrykk.domene;

import ske.fastsetting.skatt.old.domene.Person;
import ske.fastsetting.skatt.old.uttrykk.BolskUttrykk;
import ske.fastsetting.skatt.old.uttrykk.UttrykkBeskriver;

public class AlderUttrykk {
    private final int alder;

    private AlderUttrykk(int alder) {
        this.alder = alder;
    }

    public static AlderUttrykk alderTil(Person person) {
        return new AlderUttrykk(person.getAlder());
    }

    public BolskUttrykk erMellom(int fraAlder, int tilAlder) {
        return new AlderMellomUttrykk(alder,fraAlder,tilAlder);
    }

    private static class AlderMellomUttrykk implements BolskUttrykk {
        private final int alder;
        private final int fraAlder;
        private final int tilAlder;

        public AlderMellomUttrykk(int alder, int fraAlder, int tilAlder) {
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
