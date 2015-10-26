package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.CompareableUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopDividertMedBelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopPlussMinusUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.bolsk.BolskUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public interface MultiBelopUttrykk<K> extends Uttrykk<StedbundetBelop<K>> {
    default MultiBelopPlussMinusUttrykk<K> pluss(MultiBelopUttrykk<K> ledd) {
        return MultiBelopPlussMinusUttrykk.sum(this, ledd);
    }

    default MultiBelopDivisjonsUttrykk<K> dividertMed(TallUttrykk tall) {
        return new MultiBelopDivisjonsUttrykk<>(this, tall);
    }

    default BelopDividertMedBelopUttrykk dividertMed(MultiBelopUttrykk<K> divident) {
        return new BelopDividertMedBelopUttrykk(this.tilBelop(), divident.tilBelop(), Tall.TallUttrykkType.UKJENT);
    }

    default BelopDividertMedBelopUttrykk dividertMed(BelopUttrykk divident) {
        return new BelopDividertMedBelopUttrykk(this.tilBelop(), divident, Tall.TallUttrykkType.UKJENT);
    }

    default MultiBelopMultiplikasjonsUttrykk<K> multiplisertMed(Uttrykk<Tall> tall) {
        return new MultiBelopMultiplikasjonsUttrykk<>(this, tall);
    }

    default MultiBelopPlussMinusUttrykk<K> minus(MultiBelopUttrykk<K> ledd) {
        return MultiBelopPlussMinusUttrykk.diff(this, ledd);
    }

    default BelopPlussMinusUttrykk minus(BelopUttrykk ledd) {
        return new TilEnkeltBelopUttrykk(this).minus(ledd);
    }

    default BelopPlussMinusUttrykk pluss(BelopUttrykk ledd) {
        return new TilEnkeltBelopUttrykk(this).pluss(ledd);
    }

    default MultiBelopAvrundUttrykk<K> rundAvTilHeleKroner() { return new MultiBelopAvrundUttrykk<K>(this); }

    default TilEnkeltBelopUttrykk tilBelop() {
        return new TilEnkeltBelopUttrykk(this);
    }

    default MultiBelopForholdsmessigDiffUttrykk<K> minusProporsjonalt(BelopUttrykk ledd) {
        return new MultiBelopForholdsmessigDiffUttrykk<>(this, ledd);
    }

    default MultiBelopForholdsmessigSumUttrykk<K> plussProporsjonalt(BelopUttrykk belop) {
        return new MultiBelopForholdsmessigSumUttrykk<>(this, belop);
    }

    default MultiBelopMinusStedUttrykk<K> minusSted(MultiBelopUttrykk<K> ledd) {
        return new MultiBelopMinusStedUttrykk<>(this, ledd);
    }

    default BolskUttrykk erStorreEnn(BelopUttrykk uttrykk) {
        return new CompareableUttrykk.ErStorreEnn<>(this.tilBelop(), uttrykk);
    }

    default BolskUttrykk erMindreEnn(BelopUttrykk uttrykk) {
        return new CompareableUttrykk.ErMindreEnn<>(this.tilBelop(), uttrykk);
    }



}
