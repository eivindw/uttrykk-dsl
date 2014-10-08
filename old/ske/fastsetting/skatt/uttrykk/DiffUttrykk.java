package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.KalkulerbarVerdi;

public abstract class DiffUttrykk<V extends KalkulerbarVerdi<V>,T extends OldUttrykk<V>,B> extends CachingRegelUttrykk<V,B>  {
    private final T ledd1;
    private final T ledd2;

    protected DiffUttrykk(T ledd1, T ledd2) {
        this.ledd1=ledd1;
        this.ledd2=ledd2;
    }

    @Override
    protected final V lagVerdi() {
        return ledd1.evaluer().minus(ledd2.evaluer());
    }


    public final void beskrivEvaluering(UttrykkBeskriver beskriver) {
        UttrykkBeskriver nyBeskriver =  beskriver.overskrift(evaluer() + RegelUtil.formater(navn));
        nyBeskriver.skriv("differansen mellom");
        ledd1.beskrivEvaluering(nyBeskriver);
        nyBeskriver.skriv("og");
        ledd2.beskrivEvaluering(nyBeskriver);
    }

    protected final void beskrivGeneriskMedRegel(UttrykkBeskriver beskriver) {
        beskriver.skriv("differansen mellom");
        ledd1.beskrivGenerisk(beskriver.rykkInn());
        beskriver.skriv("og");
        ledd2.beskrivGenerisk(beskriver.rykkInn());

    }


}
