package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class ProsentUttrykk<C> extends AbstractUttrykk<Tall, ProsentUttrykk<C>, C> implements TallUttrykk<ProsentUttrykk<C>, C> {

    private final Tall verdi;

    public ProsentUttrykk(Tall prosent) {
        this.verdi = prosent;
    }

    public static <C> ProsentUttrykk<C> prosent(double prosent) {
        return new ProsentUttrykk<C>(Tall.prosent(prosent));
    }

    @Override
    public Tall eval(UttrykkContext<C> ctx) {
        return verdi;
    }

    @Override
    public String beskriv(UttrykkContext<C> ctx) {
        return verdi.toString();
    }

}




