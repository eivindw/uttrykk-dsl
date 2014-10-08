package ske.fastsetting.skatt.uttrykk;

public interface OldUttrykk<T>  {
    T evaluer();
    void beskrivEvaluering(UttrykkBeskriver beskriver);
    void beskrivGenerisk(UttrykkBeskriver beskriver);
}
