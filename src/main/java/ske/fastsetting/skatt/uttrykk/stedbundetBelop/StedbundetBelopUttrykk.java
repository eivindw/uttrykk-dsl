package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

import java.util.Arrays;

/**
 * Created by jorn ola birkeland on 23.02.15.
 */
public interface StedbundetBelopUttrykk<K> extends Uttrykk<StedbundetBelop<K>> {
    default StedbundetBelopSumUttrykk<K> pluss(StedbundetBelopUttrykk<K> ledd) { return StedbundetBelopSumUttrykk.sum(Arrays.asList(this, ledd));}

    default StedbundetBelopDivisjonsUttrykk<K> dividertMed(TallUttrykk tall) {return new StedbundetBelopDivisjonsUttrykk<>(this,tall);}
    default StedbundetBelopMultiplikasjonsUttrykk<K> multiplisertMed(TallUttrykk tall) {return new StedbundetBelopMultiplikasjonsUttrykk<>(this,tall);}

    default BelopUttrykk minus(BelopUttrykk ledd) { return new TilSteduavhengigBelopUttrykk<>(this).minus(ledd);}
    default BelopUttrykk pluss(BelopUttrykk ledd) { return new TilSteduavhengigBelopUttrykk<>(this).pluss(ledd);}

    default BelopUttrykk steduavhengig() {
        return new TilSteduavhengigBelopUttrykk<>(this);
    }

    default ProporsjonalFordelingDiffUttrykk<K> minusProporsjonalt(BelopUttrykk ledd) { return new ProporsjonalFordelingDiffUttrykk<>(this, ledd);}
    default ProporsjonalFordelingSumUttrykk<K> plussProporsjonalt(BelopUttrykk belop) { return new ProporsjonalFordelingSumUttrykk<>(this, belop);}

    default MinusStedUttrykk<K> minusSted(StedbundetBelopUttrykk<K> ledd) { return new MinusStedUttrykk<>(this,ledd);}


}
