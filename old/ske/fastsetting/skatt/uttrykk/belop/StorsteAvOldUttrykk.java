package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.*;

import java.util.stream.Stream;

public class StorsteAvOldUttrykk extends RegelUttrykk<MinsteAvOldUttrykk> implements BelopOldUttrykk {
    private final BelopOldUttrykk[] uttrykk;
    private Belop evaluertBelop = null;

    private StorsteAvOldUttrykk(BelopOldUttrykk[] uttrykk) {
        this.uttrykk = uttrykk;
    }

    public static StorsteAvOldUttrykk storsteAv(BelopOldUttrykk... uttrykk) {
        return new StorsteAvOldUttrykk(uttrykk);
    }

    @Override
    public Belop evaluer() {
        if (evaluertBelop == null) {
            evaluertBelop = Stream.of(uttrykk)
                    .map(OldUttrykk::evaluer)
                    .max(Belop::sammenliknMed).get();
        }
        return evaluertBelop;
    }

    @Override
    public void beskrivEvaluering(UttrykkBeskriver beskriver) {
        final UttrykkBeskriver nyBeskriver = beskriver.overskrift(evaluer() + RegelUtil.formater(navn));

        nyBeskriver.skriv("størst av:");

        for (BelopOldUttrykk bu : uttrykk) {
            bu.beskrivEvaluering(nyBeskriver);
        }

    }

    @Override
    public void beskrivGeneriskMedRegel(UttrykkBeskriver beskriver) {
        beskriver.skriv("største av");

        Stream.of(uttrykk).forEach(bu -> bu.beskrivGenerisk(beskriver.rykkInn()));
    }
}
