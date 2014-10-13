package ske.fastsetting.skatt.beregn;

public interface UttrykkContext {
    String beskriv(Uttrykk uttrykk);

    <X> X eval(Uttrykk<X> uttrykk);

    String nyId();
}
