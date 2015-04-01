package ske.fastsetting.skatt.uttrykk;

public class UttrykkException extends RuntimeException {
    public UttrykkException(Throwable e, Uttrykk<?> uttrykk) {
        super(uttrykk!=null ? uttrykk.navn(): "",e);
    }
}
