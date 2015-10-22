package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.BrukHvisUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;

/**
 * Created by jorn ola birkeland on 21.10.15.
 */
public class BelopBrukHvisUttrykk extends BrukHvisUttrykk<Belop,BelopBrukHvisUttrykk> implements BelopUttrykk {

    public BelopBrukHvisUttrykk(Uttrykk<Belop> uttrykk) {
        super(uttrykk);
    }

    public static BelopBrukHvisUttrykk bruk(Uttrykk<Belop> uttrykk) {
        return new BelopBrukHvisUttrykk(uttrykk);
    }
}
