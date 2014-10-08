package ske.fastsetting.skatt.uttrykk;

public class FeilOldUttrykk<T> implements OldUttrykk<T> {
    private String feilmelding;

    public FeilOldUttrykk(String feilmelding) {
        this.feilmelding = feilmelding;
    }

    public static <T> FeilOldUttrykk<T> feil(String feilmelding) {
        return new FeilOldUttrykk<>(feilmelding);
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
