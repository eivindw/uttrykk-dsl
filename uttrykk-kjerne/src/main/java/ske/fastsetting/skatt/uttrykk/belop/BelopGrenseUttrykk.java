package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetBelopUttrykk;

import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr0;

public class BelopGrenseUttrykk extends ske.fastsetting.skatt.uttrykk.GrenseUttrykk<Belop, Belop, BelopGrenseUttrykk>
  implements BelopUttrykk {

    protected BelopGrenseUttrykk(Uttrykk<Belop> grunnlag) {
        super(grunnlag);
    }

    public static BelopGrenseUttrykk nedre0(BelopUttrykk grunnlag) {
        return begrens(grunnlag).nedad(kr0());
    }

    public static BelopGrenseUttrykk begrens(Uttrykk<Belop> grunnlag) {
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
