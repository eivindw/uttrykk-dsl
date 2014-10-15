package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import ske.fastsetting.skatt.uttrykk.UttrykkResultat;

public interface UttrykkBeskriver<T> {
    T beskriv(UttrykkResultat<?> resultat);
}
