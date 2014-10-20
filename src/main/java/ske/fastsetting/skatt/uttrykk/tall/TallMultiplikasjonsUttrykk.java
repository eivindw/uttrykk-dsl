package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.MultiplikasjonsUttrykk;

public class TallMultiplikasjonsUttrykk<C>
    extends MultiplikasjonsUttrykk<Tall, TallUttrykk<C>, TallMultiplikasjonsUttrykk<C>,C>
    implements TallUttrykk<C>
{
    public TallMultiplikasjonsUttrykk(TallUttrykk<C> faktor1, TallUttrykk<C> faktor2) {
        super(faktor1, faktor2);
    }
}