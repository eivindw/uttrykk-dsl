package ske.fastsetting.skatt.old.uttrykk.belop;

import ske.fastsetting.skatt.old.domene.Belop;
import ske.fastsetting.skatt.old.uttrykk.DiffUttrykk;

public class BelopDiffUttrykk extends DiffUttrykk<Belop,BelopUttrykk, BelopDiffUttrykk> implements BelopUttrykk {


    public BelopDiffUttrykk(BelopUttrykk ledd1, BelopUttrykk ledd2) {
        super(ledd1,ledd2);
    }

//    @Override
//    public BelopUttrykk minus(BelopUttrykk uttrykk) {
//        return new BelopMultiMinusUttrykk(this,uttrykk);
//    }
}
