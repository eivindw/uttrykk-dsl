package ske.fastsetting.skatt.uttrykk;

public interface Uttrykk<V, C>  {
    V eval(UttrykkContext<C> ctx);

    String beskriv(UttrykkContext<C> ctx);

    String id(UttrykkContext<C> ctx);

    String navn();


}
