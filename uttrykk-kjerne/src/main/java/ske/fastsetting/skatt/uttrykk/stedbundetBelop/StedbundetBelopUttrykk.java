package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopDividertMedBelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopPlussMinusUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public interface StedbundetBelopUttrykk<K> extends Uttrykk<StedbundetBelop<K>> {
    default StedbundetBelopPlussMinusUttrykk<K> pluss(StedbundetBelopUttrykk<K> ledd) {
        return StedbundetBelopPlussMinusUttrykk.sum(this, ledd);
    }

    default StedbundetBelopDivisjonsUttrykk<K> dividertMed(TallUttrykk tall) {
        return new StedbundetBelopDivisjonsUttrykk<>(this, tall);
    }

    default BelopDividertMedBelopUttrykk dividertMed(StedbundetBelopUttrykk<K> divident) {
        return new BelopDividertMedBelopUttrykk(this.steduavhengig(), divident.steduavhengig(), Tall.TallUttrykkType.UKJENT);
    }

    default BelopDividertMedBelopUttrykk dividertMed(BelopUttrykk divident) {
        return new BelopDividertMedBelopUttrykk(this.steduavhengig(), divident, Tall.TallUttrykkType.UKJENT);
    }

    default StedbundetBelopMultiplikasjonsUttrykk<K> multiplisertMed(Uttrykk<Tall> tall) {
        return new StedbundetBelopMultiplikasjonsUttrykk<>(this, tall);
    }

    default StedbundetBelopPlussMinusUttrykk<K> minus(StedbundetBelopUttrykk<K> ledd) {
        return StedbundetBelopPlussMinusUttrykk.diff(this, ledd);
    }

    default BelopPlussMinusUttrykk minus(BelopUttrykk ledd) {
        return new TilSteduavhengigBelopUttrykk(this).minus(ledd);
    }

    default BelopPlussMinusUttrykk pluss(BelopUttrykk ledd) {
        return new TilSteduavhengigBelopUttrykk(this).pluss(ledd);
    }

    default StedbundetBelopAvrundUttrykk<K> rundAvTilHeleKroner() { return new StedbundetBelopAvrundUttrykk<K>(this); }

    default TilSteduavhengigBelopUttrykk steduavhengig() {
        return new TilSteduavhengigBelopUttrykk(this);
    }

    default StedbundetBelopForholdsmessigDiffUttrykk<K> minusProporsjonalt(BelopUttrykk ledd) {
        return new StedbundetBelopForholdsmessigDiffUttrykk<>(this, ledd);
    }

    default StedbundetBelopForholdsmessigSumUttrykk<K> plussProporsjonalt(BelopUttrykk belop) {
        return new StedbundetBelopForholdsmessigSumUttrykk<>(this, belop);
    }

    default StedbundetBelopMinusStedUttrykk<K> minusSted(StedbundetBelopUttrykk<K> ledd) {
        return new StedbundetBelopMinusStedUttrykk<>(this, ledd);
    }


}
