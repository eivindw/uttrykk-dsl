package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.BolskOldUttrykk;
import ske.fastsetting.skatt.uttrykk.HvisOldUttrykk;

public class TallHvisOldUttrykk extends HvisOldUttrykk<Tall, TallHvisOldUttrykk> implements TallOldUttrykk {

    public static BrukUttrykk<Tall,TallHvisOldUttrykk> hvis(BolskOldUttrykk bolskUttrykk) {
        return new BrukUttrykk<>(bolskUttrykk, new TallHvisOldUttrykk());
    }

}
