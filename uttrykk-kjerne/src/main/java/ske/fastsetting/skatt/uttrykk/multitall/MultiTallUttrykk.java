package ske.fastsetting.skatt.uttrykk.multitall;

import ske.fastsetting.skatt.domene.MultiTall;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.multibelop.MultiBelopPlussMinusUttrykk;

public interface MultiTallUttrykk<T> extends Uttrykk<MultiTall<T>> {
    default MultitallPlussMinusUttrykk<T> pluss(MultiTallUttrykk<T> ledd) {
        return MultitallPlussMinusUttrykk.sum(this, ledd);
    }

}
