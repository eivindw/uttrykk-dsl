package ske.fastsetting.skatt.old.uttrykk;

public interface Uttrykk<T>  {
    T evaluer();
    void beskrivEvaluering(UttrykkBeskriver beskriver);
    void beskrivGenerisk(UttrykkBeskriver beskriver);
}
