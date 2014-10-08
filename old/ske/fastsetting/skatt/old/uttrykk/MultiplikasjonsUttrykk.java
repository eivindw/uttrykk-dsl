package ske.fastsetting.skatt.old.uttrykk;

import ske.fastsetting.skatt.old.domene.KalkulerbarVerdi;
import ske.fastsetting.skatt.old.uttrykk.tall.TallUttrykk;

public abstract class MultiplikasjonsUttrykk<V extends KalkulerbarVerdi<V>, T extends Uttrykk<V>, B>  extends CachingRegelUttrykk<V,B>  {
    protected final T faktor1;
    protected final TallUttrykk faktor2;

    protected MultiplikasjonsUttrykk(T faktor1, TallUttrykk faktor2) {
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
