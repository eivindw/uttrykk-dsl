package ske.fastsetting.skatt.uttrykk;

public class FeilUttrykk<T,C> extends AbstractUttrykk<T, FeilUttrykk<T,C>, C> {
    private String feilmelding;

    public FeilUttrykk(String feilmelding) {
        this.feilmelding = feilmelding;
    }

    public static <T,C> FeilUttrykk<T,C> feil(String feilmelding) {
        return new FeilUttrykk<>(feilmelding);
    }

    @Override
    public T eval(UttrykkContext ctx) {
        throw new IllegalStateException(feilmelding);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return feilmelding;
    }
}
