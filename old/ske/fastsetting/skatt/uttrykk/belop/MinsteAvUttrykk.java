package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.RegelUtil;
import ske.fastsetting.skatt.uttrykk.RegelUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkBeskriver;

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
