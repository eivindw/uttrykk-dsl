package ske.fastsetting.skatt.beregn;

import ske.fastsetting.skatt.domene.Tall;

public class ProsentUttrykk extends AbstractUttrykk<Tall> {

    private final Tall prosent;

    public ProsentUttrykk(Tall prosent) {
        this.prosent = prosent;
    }

    @Override
    public Tall eval(UttrykkContext ctx) {
        return prosent;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return prosent + "%";
    }

    public static ProsentUttrykk prosent(double prosent) {
        return new ProsentUttrykk(Tall.prosent(prosent));
    }
}
