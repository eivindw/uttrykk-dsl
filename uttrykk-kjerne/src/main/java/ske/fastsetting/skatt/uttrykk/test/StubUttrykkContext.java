package ske.fastsetting.skatt.uttrykk.test;

import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;

/**
 * Created by jorn ola birkeland on 11.05.15.
 */
public class StubUttrykkContext extends UttrykkContextImpl {
    protected StubUttrykkContext(Object[] input) {
        super(input);
    }

    public static StubUttrykkContext ny(Object... input) {
        return new StubUttrykkContext(input);
    }

    public <T> void stub(Uttrykk<T> uttrykk, T verdi) {
        overstyrVerdi(uttrykk,verdi);
    }

    public <T> T verdiAv(Uttrykk<T> uttrykk) {
        return kalkuler(uttrykk,true,false).verdi();
    }
}
