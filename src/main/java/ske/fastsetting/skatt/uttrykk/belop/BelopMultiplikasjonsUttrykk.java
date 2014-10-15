package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.MultiplikasjonsUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public class BelopMultiplikasjonsUttrykk
    extends MultiplikasjonsUttrykk<Belop, BelopUttrykk<?>, BelopMultiplikasjonsUttrykk>
    implements BelopUttrykk<BelopMultiplikasjonsUttrykk>
{
    public BelopMultiplikasjonsUttrykk(BelopUttrykk beloputtrykk, TallUttrykk tallUttrykk) {
        super(beloputtrykk, tallUttrykk);
    }
}
