package ske.fastsetting.skatt.uttrykk.ektefelle;

import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;

public class EktefelleUttrykkContext extends UttrykkContextImpl {
    private EktefelleUttrykkContext(Object... input) {
        super(input);
    }

    public static EktefelleUttrykkContext ny(Object... input) {
        return new EktefelleUttrykkContext(input);
    }

    public EktefelleUttrykkContext medEktefelle(Object... input) {
        EktefelleUttrykkContext ektefelle = ny(input);
        this.leggTilInput(ektefelle);
        ektefelle.leggTilInput(this);

        return ektefelle;
    }

    public <V> UttrykkResultat<V> beskrivResultat(Uttrykk<V> jordbruksfradrag) {
        return kalkuler(jordbruksfradrag, false, true);
    }
}
