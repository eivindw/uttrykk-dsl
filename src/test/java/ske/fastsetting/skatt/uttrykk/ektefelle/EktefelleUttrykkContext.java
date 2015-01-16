package ske.fastsetting.skatt.uttrykk.ektefelle;

import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;

/**
 * Created by jorn ola birkeland on 16.01.15.
 */
public class EktefelleUttrykkContext<V> extends UttrykkContextImpl<V> {
    private EktefelleUttrykkContext(Object... input) {
        super(input);
    }

    public static <X> EktefelleUttrykkContext<X> ny(Object... input) {
        return new EktefelleUttrykkContext<>(input);
    }

    public EktefelleUttrykkContext<V> medEktefelle(Object... input) {
        EktefelleUttrykkContext<V> ektefelle = ny(input);
        this.leggTilInput(ektefelle);
        ektefelle.leggTilInput(this);

        return ektefelle;
    }

    public UttrykkResultat<V> beskrivResultat(Uttrykk<V> jordbruksfradrag) {
        return kalkuler(jordbruksfradrag,false,true);
    }
}
