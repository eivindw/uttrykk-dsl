package ske.fastsetting.skatt.uttrykk.test;

import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConsoleUttrykkBeskriver;

public class Debug extends UttrykkContextImpl {
    protected Debug(Object[] input) {
        super(input);
    }

    public static void debug(Uttrykk<?> uttrykk, Object... input) {
        final UttrykkResultat<?> resultat = new Debug(input).kalkuler(uttrykk, true,true);

        System.out.println("verdi: " + resultat.verdi());
        System.out.println();
        System.out.println("***** Beregning *****");

        ConsoleUttrykkBeskriver.print(resultat);
    }
}
