package ske.fastsetting.skatt.domene;

public class Person {
    public static final Person UKJENT = new Person(0,Kommune.Ukjent);
    private final int alder;
    private Kommune kommune;

    public Person(int alder, Kommune kommune) {
        this.alder = alder;
        this.kommune = kommune;
    }

    public int getAlder() {
        return alder;
    }


    public Kommune getKommune() {
        return kommune;
    }
}
