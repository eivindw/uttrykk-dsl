package ske.fastsetting.skatt.uttrykk;

public class GeneriskBrukHvisUttrykk<T> extends BrukHvisUttrykk<T,GeneriskBrukHvisUttrykk<T>> {

    public GeneriskBrukHvisUttrykk(Uttrykk<T> uttrykk) {
        super(uttrykk);
    }

    public static <T> ske.fastsetting.skatt.uttrykk.GeneriskBrukHvisUttrykk<T> bruk(Uttrykk<T> uttrykk) {
        return new ske.fastsetting.skatt.uttrykk.GeneriskBrukHvisUttrykk<>(uttrykk);
    }
}
