package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.DiffUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;

public class BelopDiffUttrykk
  extends DiffUttrykk<Belop, Uttrykk<Belop>, BelopDiffUttrykk>
  implements BelopUttrykk {
    public BelopDiffUttrykk(Uttrykk<Belop> ledd1, Uttrykk<Belop> ledd2) {
        super(ledd1, ledd2);
    }

    public static BelopDiffUttrykk differanseMellom(Uttrykk<Belop> ledd1, Uttrykk<Belop> ledd2) {
        return new BelopDiffUttrykk(ledd1, ledd2);
    }

}
