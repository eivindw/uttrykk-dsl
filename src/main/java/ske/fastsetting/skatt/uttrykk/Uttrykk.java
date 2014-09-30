package ske.fastsetting.skatt.uttrykk;

public interface Uttrykk<I, O> {

    O eval(I... argumenter);
}
