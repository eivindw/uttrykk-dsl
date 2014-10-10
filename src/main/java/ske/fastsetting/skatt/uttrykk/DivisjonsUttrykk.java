package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.KalkulerbarVerdi;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public abstract class DivisjonsUttrykk<V extends KalkulerbarVerdi<V>, T extends Uttrykk<V>, B> extends CachingRegelUttrykk<V,B>  {
    protected final T divident;
    protected final TallUttrykk divisor;

    protected DivisjonsUttrykk(T divident, TallUttrykk tallUttrykk) {
        this.divident = divident;
        this.divisor = tallUttrykk;
    }

    public final void beskrivEvaluering(UttrykkBeskriver beskriver) {
        UttrykkBeskriver nyBeskriver = beskriver.overskrift(evaluer() + RegelUtil.formater(navn));
        divident.beskrivEvaluering(nyBeskriver);
        nyBeskriver.skriv("dividert med");
        divisor.beskrivEvaluering(nyBeskriver);
    }

    protected final void beskrivGeneriskMedRegel(UttrykkBeskriver beskriver) {
        divident.beskrivGenerisk(beskriver.rykkInn());
        beskriver.skriv("dividert med");
        divisor.beskrivGenerisk(beskriver.rykkInn());
    }

    @Override
    protected final V lagVerdi() {
        return divident.evaluer().dividertMed(divisor.evaluer().toBigDecimal());
    }

}