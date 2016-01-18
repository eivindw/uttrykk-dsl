package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.Kvantitet;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.CompareableUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.multibelop.MultiBelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

import java.math.BigDecimal;

import static ske.fastsetting.skatt.uttrykk.belop.BelopPlussMinusUttrykk.diff;
import static ske.fastsetting.skatt.uttrykk.belop.BelopPlussMinusUttrykk.sum;
import static ske.fastsetting.skatt.uttrykk.belop.TilMultiBelopUttrykk.tilStedbundet;

public interface BelopUttrykk extends CompareableUttrykk<Belop> {


    default BelopMultiplikasjonsUttrykk multiplisertMed(Uttrykk<Tall> verdi) {
        return new BelopMultiplikasjonsUttrykk(this, verdi);
    }

    default BelopDivisjonsUttrykk dividertMed(TallUttrykk verdi) {
        return new BelopDivisjonsUttrykk(this, verdi);
    }

    default BelopPlussMinusUttrykk minus(BelopUttrykk uttrykk) { return diff(this, uttrykk); }

    default BelopPlussMinusUttrykk pluss(BelopUttrykk uttrykk) {
        return sum(this, uttrykk);
    }

    default <T> TilMultiBelopUttrykk<T> i(T sted) {return tilStedbundet(this,sted); }

    default BelopDividertMedBelopUttrykk dividertMed(BelopUttrykk divident) {
        return new BelopDividertMedBelopUttrykk(this, divident, Tall.TallUttrykkType.UKJENT);
    }

    default BelopDividertMedBelopUttrykk dividertMedTilProsent(BelopUttrykk divident) {
        return new BelopDividertMedBelopUttrykk(this, divident, Tall.TallUttrykkType.PROSENT);
    }

    default BelopByttFortegnUttrykk byttFortegn() {
        return new BelopByttFortegnUttrykk(this);
    }

    default BelopAvrundUttrykk rundAvTilHeleKroner() {
        return new BelopAvrundUttrykk(this);
    }

    default BelopAvrundOppTilDeleligMedUttrykk rundAvOppTilHoyesteDeleligMed(BigDecimal verdi) {
        return new BelopAvrundOppTilDeleligMedUttrykk(this,verdi);
    }

    default BelopAvrundNedTilNaermeste100Uttrykk rundAvNedTilNaermeste100() {
        return new BelopAvrundNedTilNaermeste100Uttrykk(this);
    }

    default <K extends Kvantitet> BelopPerKvantitetUttrykk<K> per(Uttrykk<K> uttrykk) {
        return new BelopPerKvantitetUttrykk<>(this, uttrykk);
    }

     default <T> BelopFordelSomStebundetBelopUttrykk<T> fordelSom(MultiBelopUttrykk<T> uttrykk) {
        return new BelopFordelSomStebundetBelopUttrykk<>(this,uttrykk);
    }

}
