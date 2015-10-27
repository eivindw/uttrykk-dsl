package ske.fastsetting.skatt.uttrykk.distanse;

import ske.fastsetting.skatt.uttrykk.CompareableUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopPerKvantitetUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

public interface DistanseUttrykk extends CompareableUttrykk<Distanse> {

    default DistansePlussMinusUttrykk pluss(DistanseUttrykk ledd) {
        return DistansePlussMinusUttrykk.sum(this, ledd);
    }

    default BelopUttrykk multiplisertMed(BelopPerKvantitetUttrykk<Distanse> belopPerDistanse) {
        return new DistanseMultiplisertMedBelopPerDistanseUttrykk(this, belopPerDistanse);
    }

}
