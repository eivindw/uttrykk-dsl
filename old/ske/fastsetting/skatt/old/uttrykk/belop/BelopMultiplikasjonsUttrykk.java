package ske.fastsetting.skatt.old.uttrykk.belop;

import ske.fastsetting.skatt.old.domene.Belop;
import ske.fastsetting.skatt.old.uttrykk.MultiplikasjonsUttrykk;
import ske.fastsetting.skatt.old.uttrykk.tall.TallUttrykk;

public class BelopMultiplikasjonsUttrykk extends MultiplikasjonsUttrykk<Belop, BelopUttrykk, BelopMultiplikasjonsUttrykk> implements BelopUttrykk {

    public BelopMultiplikasjonsUttrykk(BelopUttrykk beloputtrykk, TallUttrykk tallUttrykk) {
        super(beloputtrykk, tallUttrykk);
    }
}
