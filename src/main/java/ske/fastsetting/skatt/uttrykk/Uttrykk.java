package ske.fastsetting.skatt.uttrykk;

public interface Uttrykk<T> {

    T eval(UttrykkContext ctx);

    String beskriv(UttrykkContext ctx);

    String navn();

    String id(UttrykkContext ctx);
}
