package ske.fastsetting.skatt.uttrykk;

public class FeilUttrykk<T> implements Uttrykk<T> {
    private String feilmelding;

    public FeilUttrykk(String feilmelding) {
        this.feilmelding = feilmelding;
    }

    public static <T> FeilUttrykk<T> feil(String feilmelding) {
        return new FeilUttrykk<>(feilmelding);
    }

    @Override
    public T evaluer() {
        throw new IllegalStateException(feilmelding);
    }

    @Override
    public void beskrivEvaluering(UttrykkBeskriver beskriver) {
        beskriver.skriv(feilmelding);
    }

    @Override
    public void beskrivGenerisk(UttrykkBeskriver beskriver) {
        beskriver.skriv(feilmelding);
    }
}
