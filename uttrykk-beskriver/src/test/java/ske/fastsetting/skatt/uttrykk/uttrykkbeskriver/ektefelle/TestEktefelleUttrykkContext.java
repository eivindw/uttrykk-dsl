package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ektefelle;

import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;

public class TestEktefelleUttrykkContext extends UttrykkContextImpl {
    private TestEktefelleUttrykkContext(Object... input) {
        super(input);
    }

    public static TestEktefelleUttrykkContext ny(Object... input) {
        return new TestEktefelleUttrykkContext(input);
    }

    public TestEktefelleUttrykkContext medEktefelle(Object... input) {
        TestEktefelleUttrykkContext ektefelle = ny(input);
        this.leggTilInput(ektefelle);
        ektefelle.leggTilInput(this);

        return this;
    }

    public <V> UttrykkResultat<V> beskrivResultat(Uttrykk<V> jordbruksfradrag) {
        return kalkuler(jordbruksfradrag, false, true);
    }
}
