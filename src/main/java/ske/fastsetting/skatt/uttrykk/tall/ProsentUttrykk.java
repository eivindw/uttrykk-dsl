package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.RegelUttrykk;

public class ProsentUttrykk extends RegelUttrykk<ProsentUttrykk, Tall> implements TallUttrykk {

    private final Tall verdi;

    public ProsentUttrykk(Tall prosent) {
        this.verdi = prosent;
    }

    public static ProsentUttrykk prosent(double prosent) {
        return new ProsentUttrykk(Tall.prosent(prosent));
    }

    @Override
    public Tall eval(UttrykkContext ctx) {
        return verdi;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return verdi.toString();
    }
}
