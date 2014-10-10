package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.RegelUtil;
import ske.fastsetting.skatt.uttrykk.RegelUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkBeskriver;

public class ProsentUttrykk extends RegelUttrykk<ProsentUttrykk> implements TallUttrykk {

    private final Tall verdi;

    public ProsentUttrykk(Tall prosent) {
        this.verdi = prosent;
    }

    public static ProsentUttrykk prosent(double prosent) {
        return new ProsentUttrykk(Tall.prosent(prosent));
    }

    public Tall evaluer() {
        return verdi;
    }


    public void beskrivEvaluering(UttrykkBeskriver beskriver) {
       beskriver.skriv(verdi + RegelUtil.formater(navn));
    }

    @Override
    protected void beskrivGeneriskMedRegel(UttrykkBeskriver beskriver) {
        beskriver.skriv(verdi + RegelUtil.formater(navn));
    }
}
