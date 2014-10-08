package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.KalkulerbarVerdi;
import ske.fastsetting.skatt.uttrykk.tall.TallOldUttrykk;

public abstract class MultiplikasjonsUttrykk<V extends KalkulerbarVerdi<V>, T extends OldUttrykk<V>, B>  extends CachingRegelUttrykk<V,B>  {
    protected final T faktor1;
    protected final TallOldUttrykk faktor2;

    protected MultiplikasjonsUttrykk(T faktor1, TallOldUttrykk faktor2) {
        this.faktor1 = faktor1;
        this.faktor2 = faktor2;
    }

    @Override
    protected final V lagVerdi() {
        return faktor1.evaluer().multiplisertMed(faktor2.evaluer().toBigDecimal());
    }

    public final void beskrivEvaluering(UttrykkBeskriver beskriver) {
        UttrykkBeskriver nyBeskriver = beskriver.overskrift(evaluer() + RegelUtil.formater(navn));
        faktor1.beskrivEvaluering(nyBeskriver);
        nyBeskriver.skriv("multiplisert med");
        faktor2.beskrivEvaluering(nyBeskriver);
    }

    public final void beskrivGeneriskMedRegel(UttrykkBeskriver beskriver) {
        beskriver.skriv("produktet av");
        faktor1.beskrivGenerisk(beskriver.rykkInn());
        beskriver.skriv("og");
        faktor2.beskrivGenerisk(beskriver.rykkInn());
    }
}
