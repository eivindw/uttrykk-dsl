package ske.fastsetting.skatt.uttrykk;

public interface Uttrykk<T>  {
    T evaluer();
    void beskrivEvaluering(UttrykkBeskriver beskriver);
    void beskrivGenerisk(UttrykkBeskriver beskriver);
}
