package ske.fastsetting.skatt.beregn;

public interface Uttrykk<T> {

    T eval();

    String beskriv();

    Uttrykk<T> navn(String id);

    String navn();

    String id();
}
