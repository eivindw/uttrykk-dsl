package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.DiffUttrykk;

public class BelopDiffUttrykk<C>
    extends DiffUttrykk<Belop, BelopUttrykk<?,C>, BelopDiffUttrykk<C>,C>
    implements BelopUttrykk<BelopDiffUttrykk<C>,C>
{
    public BelopDiffUttrykk(BelopUttrykk<?,C> ledd1, BelopUttrykk<?,C> ledd2) {
        super(ledd1,ledd2);
    }
}
