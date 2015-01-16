package ske.fastsetting.skatt.uttrykk;

public interface UttrykkContext {
    String beskriv(Uttrykk<?> uttrykk);

    <X> X eval(Uttrykk<X> uttrykk);

    String nyId();

    <T> T input(Class<T> clazz);

    <T> boolean harInput(Class<T> clazz);
}
