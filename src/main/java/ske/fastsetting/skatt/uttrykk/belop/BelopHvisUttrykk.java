package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.BolskUttrykk;
import ske.fastsetting.skatt.uttrykk.HvisUttrykk;

public class BelopHvisUttrykk<C> extends HvisUttrykk<Belop, BelopHvisUttrykk<C>,C> implements BelopUttrykk<BelopHvisUttrykk<C>,C> {
    public static <C> BrukUttrykk<Belop,BelopHvisUttrykk<C>, C> hvis(BolskUttrykk<?,C> uttrykk) {
        return new BrukUttrykk<Belop, BelopHvisUttrykk<C>, C>(uttrykk, new BelopHvisUttrykk<C>());
    }
}
