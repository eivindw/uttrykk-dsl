package ske.fastsetting.skatt.uttrykk;

public interface UttrykkContext {
    String beskriv(Uttrykk<?> uttrykk);

    <X> X eval(Uttrykk<X> uttrykk);

    <T> T hentInput(Class<T> clazz);

    void settInput(Object input);

    void fjernInput(Object input);

    <T> boolean harInput(Class<T> clazz);

    UttrykkContext klon();
}
