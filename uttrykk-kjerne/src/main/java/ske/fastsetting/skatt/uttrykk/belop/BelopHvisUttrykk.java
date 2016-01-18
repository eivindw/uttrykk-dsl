package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.bolsk.BolskUttrykk;
import ske.fastsetting.skatt.uttrykk.HvisUttrykk;

public class BelopHvisUttrykk extends HvisUttrykk<Belop, BelopHvisUttrykk> implements BelopUttrykk {
    public static BrukUttrykk<Belop, BelopHvisUttrykk> hvis(Uttrykk<Boolean> uttrykk) {
        return new BrukUttrykk<>(uttrykk, new BelopHvisUttrykk());
    }
}
