package ske.fastsetting.skatt.old.uttrykk.tall;

import ske.fastsetting.skatt.old.domene.Tall;
import ske.fastsetting.skatt.old.uttrykk.RegelUtil;
import ske.fastsetting.skatt.old.uttrykk.RegelUttrykk;
import ske.fastsetting.skatt.old.uttrykk.UttrykkBeskriver;

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
