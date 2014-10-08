package ske.fastsetting.skatt.old.uttrykk.belop;

import ske.fastsetting.skatt.old.domene.Belop;
import ske.fastsetting.skatt.old.uttrykk.BolskUttrykk;
import ske.fastsetting.skatt.old.uttrykk.HvisUttrykk;

public class BelopHvisUttrykk extends HvisUttrykk<Belop, BelopHvisUttrykk> implements BelopUttrykk {

    public static BrukUttrykk<Belop,BelopHvisUttrykk> hvis(BolskUttrykk uttrykk) {
        return new BrukUttrykk<>(uttrykk, new BelopHvisUttrykk());
    }
}
