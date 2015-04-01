package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.BolskUttrykk;
import ske.fastsetting.skatt.uttrykk.HvisUttrykk;

public class BelopHvisUttrykk extends HvisUttrykk<Belop, BelopHvisUttrykk> implements BelopUttrykk {
    public static BrukUttrykk<Belop, BelopHvisUttrykk> hvis(BolskUttrykk uttrykk) {
        return new BrukUttrykk<>(uttrykk, new BelopHvisUttrykk());
    }
}
