package ske.fastsetting.skatt.uttrykk;

public class FeilUttrykk<T> extends AbstractUttrykk<T, FeilUttrykk<T>> {
    private String feilmelding;

    public FeilUttrykk(String feilmelding) {
        this.feilmelding = feilmelding;
    }

    public static <T> FeilUttrykk<T> feil(String feilmelding) {
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
