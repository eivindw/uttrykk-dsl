package ske.fastsetting.skatt.uttrykk.belop;

import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr0;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetBelopUttrykk;

public class GrenseUttrykk extends ske.fastsetting.skatt.uttrykk.GrenseUttrykk<Belop, Belop, GrenseUttrykk>
  implements BelopUttrykk {

    protected GrenseUttrykk(BelopUttrykk grunnlag) {
        super(grunnlag);
    }

    public static GrenseUttrykk nedre0(BelopUttrykk grunnlag) {
        return begrens(grunnlag).nedad(kr0());
    }

    public static GrenseUttrykk begrens(BelopUttrykk grunnlag) {
        return new GrenseUttrykk(grunnlag);
    }

    public GrenseUttrykk nedad(StedbundetBelopUttrykk minimum) {
        return nedad(minimum.steduavhengig());
    }


    public GrenseUttrykk oppad(StedbundetBelopUttrykk maksimum) {
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
