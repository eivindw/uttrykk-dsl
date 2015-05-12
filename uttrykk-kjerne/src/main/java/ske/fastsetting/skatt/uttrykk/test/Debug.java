package ske.fastsetting.skatt.uttrykk.test;

import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConsoleUttrykkBeskriver;

public class Debug {
    public static void debug(Uttrykk<?> uttrykk, Object... input) {
        final UttrykkResultat<?> resultat = UttrykkContextImpl.beregneOgBeskrive(uttrykk, input);

        System.out.println("verdi: " + resultat.verdi());
        System.out.println();
        System.out.println("***** Beregning *****");

        ConsoleUttrykkBeskriver.print(resultat);
    }
}
