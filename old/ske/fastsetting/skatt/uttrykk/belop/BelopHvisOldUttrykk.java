package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.BolskOldUttrykk;
import ske.fastsetting.skatt.uttrykk.HvisOldUttrykk;

public class BelopHvisOldUttrykk extends HvisOldUttrykk<Belop, BelopHvisOldUttrykk> implements BelopOldUttrykk {

    public static BrukUttrykk<Belop,BelopHvisOldUttrykk> hvis(BolskOldUttrykk uttrykk) {
        return new BrukUttrykk<>(uttrykk, new BelopHvisOldUttrykk());
    }
}
