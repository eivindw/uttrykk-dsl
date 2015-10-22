package ske.fastsetting.skatt.uttrykk;

/**
 * Created by jorn ola birkeland on 15.05.15.
 */
public class TestUttrykkContext extends UttrykkContextImpl {
    public TestUttrykkContext(Object[] input) {
        super(input);
    }

    public static <X> UttrykkResultat<X> beregne(Uttrykk<X> uttrykk, Object... input) {
        TestUttrykkContext uttrykkContext = new TestUttrykkContext(input);
        return uttrykkContext.kalkuler(uttrykk, true, false);
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
