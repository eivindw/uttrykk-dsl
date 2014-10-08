package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.DiffUttrykk;

public class BelopDiffOldUttrykk extends DiffUttrykk<Belop,BelopOldUttrykk, BelopDiffOldUttrykk> implements BelopOldUttrykk {


    public BelopDiffOldUttrykk(BelopOldUttrykk ledd1, BelopOldUttrykk ledd2) {
        super(ledd1,ledd2);
    }

//    @Override
//    public BelopUttrykk minus(BelopUttrykk uttrykk) {
//        return new BelopMultiMinusUttrykk(this,uttrykk);
//    }
}
