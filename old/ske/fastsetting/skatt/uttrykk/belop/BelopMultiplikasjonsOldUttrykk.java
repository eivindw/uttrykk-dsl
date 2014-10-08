package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.MultiplikasjonsUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallOldUttrykk;

public class BelopMultiplikasjonsOldUttrykk extends MultiplikasjonsUttrykk<Belop, BelopOldUttrykk, BelopMultiplikasjonsOldUttrykk> implements BelopOldUttrykk {

    public BelopMultiplikasjonsOldUttrykk(BelopOldUttrykk beloputtrykk, TallOldUttrykk tallUttrykk) {
        super(beloputtrykk, tallUttrykk);
    }
}
