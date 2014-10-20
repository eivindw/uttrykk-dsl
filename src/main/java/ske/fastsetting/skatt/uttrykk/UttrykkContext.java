package ske.fastsetting.skatt.uttrykk;

public interface UttrykkContext<C> {
    String beskriv(Uttrykk<?,?,C> uttrykk);

    <X> X eval(Uttrykk<X, ?, C> uttrykk);

    String nyId();

    C input();
}
