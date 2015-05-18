package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;

/**
 * Created by jorn ola birkeland on 15.05.15.
 */
public class TestUttrykkContext extends UttrykkContextImpl {
    protected TestUttrykkContext(Object[] input) {
        super(input);
    }

    public static <X> UttrykkResultat<X> beskrive(Uttrykk<X> uttrykk, Object... input) {
        TestUttrykkContext uttrykkContext = new TestUttrykkContext(input);
        return uttrykkContext.kalkuler(uttrykk, false, true);
    }

    public static <X> UttrykkResultat<X> beregneOgBeskrive(Uttrykk<X> uttrykk, Object... input) {
        TestUttrykkContext uttrykkContext = new TestUttrykkContext(input);
        return uttrykkContext.kalkuler(uttrykk, true, true);
    }

}
