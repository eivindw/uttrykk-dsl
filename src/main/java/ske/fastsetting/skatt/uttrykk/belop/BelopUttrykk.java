package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.Kvantitet;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.CompareableUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public interface BelopUttrykk extends CompareableUttrykk<Belop> {


    default BelopMultiplikasjonsUttrykk multiplisertMed(Uttrykk<Tall> verdi) {
        return new BelopMultiplikasjonsUttrykk(this, verdi);
    }

    default BelopDivisjonsUttrykk dividertMed(TallUttrykk verdi) {
        return new BelopDivisjonsUttrykk(this, verdi);
    }

    default BelopDiffUttrykk minus(BelopUttrykk uttrykk) {
        return new BelopDiffUttrykk(this, uttrykk);
    }

    default BelopSumUttrykk pluss(BelopUttrykk uttrykk) {
        return BelopSumUttrykk.sum(this, uttrykk);
    }

    default BelopDividertMedBelopUttrykk dividertMed(BelopUttrykk divident) {
        return new BelopDividertMedBelopUttrykk(this, divident);
    }

    default BelopUttrykk byttFortegn() {
        return new ByttFortegnBelopUttrykk(this);
    }

    default <K extends Kvantitet> BelopPerKvantitetUttrykk<K> per(Uttrykk<K> uttrykk) {
        return new BelopPerKvantitetUttrykk<>(this, uttrykk);
    }

}
