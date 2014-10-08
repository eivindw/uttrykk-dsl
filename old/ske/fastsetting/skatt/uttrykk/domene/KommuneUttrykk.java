package ske.fastsetting.skatt.uttrykk.domene;

import ske.fastsetting.skatt.domene.Kommune;
import ske.fastsetting.skatt.domene.Person;
import ske.fastsetting.skatt.uttrykk.BolskUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkBeskriver;

import java.util.EnumSet;
import java.util.stream.Collectors;

public class KommuneUttrykk {
    public static KommuneUttrykk lagKommuneUttrykk(Kommune kommune) {
        return new KommuneUttrykk(kommune);
    }

    private final Kommune kommune;

    public KommuneUttrykk(Kommune kommune) {
        this.kommune = kommune;
    }

    public static KommuneUttrykk bostedKommuneTil(Person skattyter) {
        return new KommuneUttrykk(skattyter.getKommune());
    }

    public BolskUttrykk erEnAv(EnumSet<Kommune> kommuner) {

        return new BolskUttrykk() {
            @Override
            public Boolean evaluer() {
                return kommuner.contains(kommune);
            }

            @Override
            public void beskrivEvaluering(UttrykkBeskriver beskriver) {
                beskriver.skriv("Bostedkmmune " + kommune + (evaluer() ? " er en av " : " ikke er en av ") + kommuner.stream().map(Enum::name).collect(Collectors.joining(", ")));
            }

            @Override
            public void beskrivGenerisk(UttrykkBeskriver beskriver) {
                beskriver.skriv("Bostedkmmune er en av " + kommuner.stream().map(Enum::name).collect(Collectors.joining(", ")));
            }

        };
    }

}
