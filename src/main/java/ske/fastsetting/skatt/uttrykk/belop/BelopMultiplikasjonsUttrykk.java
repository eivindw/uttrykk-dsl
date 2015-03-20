package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.MultiplikasjonsUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;

public class BelopMultiplikasjonsUttrykk
    extends MultiplikasjonsUttrykk<Belop, BelopUttrykk, BelopMultiplikasjonsUttrykk>
    implements BelopUttrykk
{
    public BelopMultiplikasjonsUttrykk(BelopUttrykk beloputtrykk, Uttrykk<Tall> tallUttrykk) {
        super(beloputtrykk, tallUttrykk);
    }
}
