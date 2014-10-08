package ske.fastsetting.skatt.old.uttrykk.belop;

import ske.fastsetting.skatt.old.domene.Belop;
import ske.fastsetting.skatt.old.uttrykk.Uttrykk;
import ske.fastsetting.skatt.old.uttrykk.RegelUtil;
import ske.fastsetting.skatt.old.uttrykk.RegelUttrykk;
import ske.fastsetting.skatt.old.uttrykk.UttrykkBeskriver;

import java.util.stream.Stream;

public class MinsteAvUttrykk extends RegelUttrykk<MinsteAvUttrykk> implements BelopUttrykk {
    private final BelopUttrykk[] uttrykk;
    private Belop evaluertBelop = null;

    private MinsteAvUttrykk(BelopUttrykk[] uttrykk) {
        this.uttrykk = uttrykk;
    }

    public static MinsteAvUttrykk minsteAv(BelopUttrykk... uttrykk) {
        return new MinsteAvUttrykk(uttrykk);
    }

    @Override
    public Belop evaluer() {
        if (evaluertBelop == null) {
            evaluertBelop = Stream.of(uttrykk)
                .map(Uttrykk::evaluer)
                .min(Belop::sammenliknMed).get();
        }
        return evaluertBelop;
    }

    @Override
    public void beskrivEvaluering(UttrykkBeskriver beskriver) {
        beskriver.skriv(evaluer() + ", fordi minst av" + RegelUtil.formater(navn) + ":");
        for (BelopUttrykk bu : uttrykk) {
            bu.beskrivEvaluering(beskriver.rykkInn());
        }

    }

    @Override
    public void beskrivGeneriskMedRegel(UttrykkBeskriver beskriver) {
        beskriver.skriv("minste av");

        Stream.of(uttrykk).forEach(bu -> bu.beskrivGenerisk(beskriver.rykkInn()));
    }
}
