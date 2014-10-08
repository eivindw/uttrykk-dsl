package ske.fastsetting.skatt.old.uttrykk.belop;

import ske.fastsetting.skatt.old.domene.Belop;
import ske.fastsetting.skatt.old.uttrykk.RegelUttrykk;
import ske.fastsetting.skatt.old.uttrykk.UttrykkBeskriver;
import ske.fastsetting.skatt.old.uttrykk.RegelUtil;

public class GrenseUttrykk extends RegelUttrykk<GrenseUttrykk> implements BelopUttrykk {

    private final BelopUttrykk uttrykk;
    private BelopUttrykk minimum;
    private BelopUttrykk maksimum;

    private Belop evaluertBelop = null;

    private GrenseUttrykk(BelopUttrykk uttrykk) {
        this.uttrykk = uttrykk;
    }

    public static GrenseUttrykk begrens(BelopUttrykk uttrykk) {
        return new GrenseUttrykk(uttrykk);
    }

    public GrenseUttrykk nedad(BelopUttrykk minimum) {
        this.minimum = minimum;
        return this;
    }

    public GrenseUttrykk oppad(BelopUttrykk maksimum) {
        this.maksimum = maksimum;
        return this;
    }

    @Override
    public Belop evaluer() {
        if (null == evaluertBelop) {
            Belop e = uttrykk.evaluer();
            if (null != minimum) {
                Belop min = minimum.evaluer();
                if (e.erMindreEnn(min)) {
                    e = min;
                }
            }
            if (null != maksimum) {
                Belop max = maksimum.evaluer();
                if (e.erStorreEnn(max) ) {
                    e = max;
                }
            }
            evaluertBelop = e;
        }
        return evaluertBelop;
    }

    @Override
    public void beskrivEvaluering(UttrykkBeskriver beskriver) {
        beskriver.skriv(evaluer() + " " + RegelUtil.formater(navn) + " fordi");
        uttrykk.beskrivEvaluering(beskriver.rykkInn());
        if (null == minimum && null == maksimum) {
            beskriver.skriv("Advarsel: Uttrykket mangler øvre og/eller nedre grense");
        }
        if (null != minimum) {
            beskriver.skriv("begrenset nedad til");
            minimum.beskrivEvaluering(beskriver.rykkInn());
        }
        if (null != maksimum) {
            beskriver.skriv("begrenset oppad til");
            maksimum.beskrivEvaluering(beskriver.rykkInn());
        }
    }

    @Override
    public void beskrivGeneriskMedRegel(UttrykkBeskriver beskriver) {
        uttrykk.beskrivGenerisk(beskriver);
        if (null == minimum && null == maksimum) {
            beskriver.skriv("Advarsel: Uttrykket mangler øvre og/eller nedre grense");
        }
        if (null != minimum) {
            beskriver.skriv("begrenset nedad til");
            minimum.beskrivGenerisk(beskriver.rykkInn());
        }
        if (null != maksimum) {
            beskriver.skriv("begrenset oppad til");
            maksimum.beskrivGenerisk(beskriver.rykkInn());
        }
    }
}
