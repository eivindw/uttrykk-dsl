package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.DiffUttrykk;

public class BelopDiffUttrykk
    extends DiffUttrykk<Belop, BelopUttrykk, BelopDiffUttrykk>
    implements BelopUttrykk {
    public BelopDiffUttrykk(BelopUttrykk ledd1, BelopUttrykk ledd2) {
        super(ledd1,ledd2);
    }
}
