package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.*;

import java.util.stream.Stream;

public class MinsteAvOldUttrykk extends RegelUttrykk<MinsteAvOldUttrykk> implements BelopOldUttrykk {
    private final BelopOldUttrykk[] uttrykk;
    private Belop evaluertBelop = null;

    private MinsteAvOldUttrykk(BelopOldUttrykk[] uttrykk) {
        this.uttrykk = uttrykk;
    }

    public static MinsteAvOldUttrykk minsteAv(BelopOldUttrykk... uttrykk) {
        return new MinsteAvOldUttrykk(uttrykk);
    }

    @Override
    public Belop evaluer() {
        if (evaluertBelop == null) {
            evaluertBelop = Stream.of(uttrykk)
                .map(OldUttrykk::evaluer)
                .min(Belop::sammenliknMed).get();
        }
        return evaluertBelop;
    }

    @Override
    public void beskrivEvaluering(UttrykkBeskriver beskriver) {
        beskriver.skriv(evaluer() + ", fordi minst av" + RegelUtil.formater(navn) + ":");
        for (BelopOldUttrykk bu : uttrykk) {
            bu.beskrivEvaluering(beskriver.rykkInn());
        }

    }

    @Override
    public void beskrivGeneriskMedRegel(UttrykkBeskriver beskriver) {
        beskriver.skriv("minste av");

        Stream.of(uttrykk).forEach(bu -> bu.beskrivGenerisk(beskriver.rykkInn()));
    }
}
