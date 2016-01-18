package ske.fastsetting.skatt.uttrykk.belop;

import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr0;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.multibelop.MultiBelopUttrykk;

public class BelopGrenseUttrykk extends ske.fastsetting.skatt.uttrykk.GrenseUttrykk<Belop, Belop, BelopGrenseUttrykk>
  implements BelopUttrykk {

    protected BelopGrenseUttrykk(BelopUttrykk grunnlag) {
        super(grunnlag);
    }

    public static BelopGrenseUttrykk nedre0(BelopUttrykk grunnlag) {
        return begrens(grunnlag).nedad(kr0());
    }

    public static BelopGrenseUttrykk begrens(BelopUttrykk grunnlag) {
        return new BelopGrenseUttrykk(grunnlag);
    }

    public BelopGrenseUttrykk nedad(MultiBelopUttrykk minimum) {
        return nedad(minimum.tilBelop());
    }


    public BelopGrenseUttrykk oppad(MultiBelopUttrykk maksimum) {
        return oppad(maksimum.tilBelop());
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
