package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.RegelUtil;
import ske.fastsetting.skatt.uttrykk.RegelUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkBeskriver;

import java.util.stream.Stream;

public class StorsteAvUttrykk extends RegelUttrykk<MinsteAvUttrykk> implements BelopUttrykk {
    private final BelopUttrykk[] uttrykk;
    private Belop evaluertBelop = null;

    private StorsteAvUttrykk(BelopUttrykk[] uttrykk) {
        this.uttrykk = uttrykk;
    }

    public static StorsteAvUttrykk storsteAv(BelopUttrykk... uttrykk) {
        return new StorsteAvUttrykk(uttrykk);
    }

    @Override
    public Belop evaluer() {
        if (evaluertBelop == null) {
            evaluertBelop = Stream.of(uttrykk)
                    .map(Uttrykk::evaluer)
                    .max(Belop::sammenliknMed).get();
        }
        return evaluertBelop;
    }

    @Override
    public void beskrivEvaluering(UttrykkBeskriver beskriver) {
        final UttrykkBeskriver nyBeskriver = beskriver.overskrift(evaluer() + RegelUtil.formater(navn));

        nyBeskriver.skriv("størst av:");

        for (BelopUttrykk bu : uttrykk) {
            bu.beskrivEvaluering(nyBeskriver);
        }

    }

    @Override
    public void beskrivGeneriskMedRegel(UttrykkBeskriver beskriver) {
        beskriver.skriv("største av");

        Stream.of(uttrykk).forEach(bu -> bu.beskrivGenerisk(beskriver.rykkInn()));
    }
}
