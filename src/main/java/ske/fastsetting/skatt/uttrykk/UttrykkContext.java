package ske.fastsetting.skatt.uttrykk;

public interface UttrykkContext {
    String beskriv(Uttrykk uttrykk);

    <X> X eval(Uttrykk<X, ?> uttrykk);

    String nyId();
}
