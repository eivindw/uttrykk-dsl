package ske.fastsetting.skatt.uttrykk;

public class TestUttrykkContext extends UttrykkContextImpl<TestUttrykkContext> {
    protected TestUttrykkContext(Object[] input) {
        super(input);
    }

    @Override
    protected TestUttrykkContext ny() {
        return new TestUttrykkContext(new Object[0]);
    }

    public static <X> X verdiAv(Uttrykk<X> uttrykk, Object... input) {
        TestUttrykkContext uttrykkContext = new TestUttrykkContext(input);
        return uttrykkContext.kalkuler(uttrykk, true, false).verdi();
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
