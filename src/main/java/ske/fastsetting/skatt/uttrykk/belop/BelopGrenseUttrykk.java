package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetBelopUttrykk;

public class BelopGrenseUttrykk extends ske.fastsetting.skatt.uttrykk.GrenseUttrykk<Belop,Belop, BelopGrenseUttrykk> implements BelopUttrykk {

    protected BelopGrenseUttrykk(BelopUttrykk grunnlag) {
        super(grunnlag);
    }

    public static BelopGrenseUttrykk nedre0(BelopUttrykk grunnlag) {
        return begrens(grunnlag).nedad(KroneUttrykk.KR_0);
    }

    public static BelopGrenseUttrykk begrens(BelopUttrykk grunnlag) {
        return new BelopGrenseUttrykk(grunnlag);
    }

    public BelopGrenseUttrykk nedad(StedbundetBelopUttrykk minimum) {
        return nedad(minimum.steduavhengig());
    }


    public BelopGrenseUttrykk oppad(StedbundetBelopUttrykk maksimum) {
        return oppad(maksimum.steduavhengig());
    }

    @Override
    protected Belop begrensNedad(Belop verdi, Belop min) {
        return verdi.erMindreEnn(min) ? min : verdi;
    }

    @Override
    protected Belop begrensOppad(Belop verdi, Belop max) {
        return verdi.erStorreEnn(max) ? max : verdi;
    }

}
