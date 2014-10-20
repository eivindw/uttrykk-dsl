package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.MultiplikasjonsUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public class BelopMultiplikasjonsUttrykk<C>
    extends MultiplikasjonsUttrykk<Belop, BelopUttrykk<C>, BelopMultiplikasjonsUttrykk<C>,C>
    implements BelopUttrykk<C>
{
    public BelopMultiplikasjonsUttrykk(BelopUttrykk<C> beloputtrykk, TallUttrykk<C> tallUttrykk) {
        super(beloputtrykk, tallUttrykk);
    }
}
