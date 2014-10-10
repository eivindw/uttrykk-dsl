package ske.fastsetting.skatt.beregn;

public interface Uttrykk<T> {

    T eval();

    String beskriv();
}
