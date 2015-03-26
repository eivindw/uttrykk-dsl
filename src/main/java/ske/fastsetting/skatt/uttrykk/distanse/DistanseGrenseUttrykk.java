package ske.fastsetting.skatt.uttrykk.distanse;

import ske.fastsetting.skatt.uttrykk.Uttrykk;

public class DistanseGrenseUttrykk extends ske.fastsetting.skatt.uttrykk.GrenseUttrykk<Distanse, Distanse, DistanseGrenseUttrykk> implements DistanseUttrykk {

    DistanseGrenseUttrykk(Uttrykk<Distanse> grunnlag) {
        super(grunnlag);
    }

    public static DistanseGrenseUttrykk begrens(DistanseUttrykk grunnlag) {
        return new DistanseGrenseUttrykk(grunnlag);
    }

    @Override
    protected Distanse begrensNedad(Distanse verdi, Distanse min) {
        return verdi.erMindreEnn(min) ? min : verdi;
    }

    @Override
    protected Distanse begrensOppad(Distanse verdi, Distanse max) {
        return verdi.erStorreEnn(max) ? max : verdi;
    }
}
