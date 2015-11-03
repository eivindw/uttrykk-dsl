package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.StedbundetBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.KonstantUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

import java.util.function.Function;

import static ske.fastsetting.skatt.domene.StedbundetBelop.kr;
import static ske.fastsetting.skatt.domene.StedbundetBelop.kr0;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;

/**
 * Created by jorn ola birkeland on 02.11.15.
 */
public class StedbundetBelopForHverUttrykk<K>
        extends AbstractUttrykk<StedbundetBelop<K>,StedbundetBelopForHverUttrykk<K>>
        implements StedbundetBelopUttrykk<K>{

    private final StedbundetBelopUttrykk<K> uttrykk;
    private final Function<BelopUttrykk, Uttrykk<Belop>> forHverFunksjon;

    public StedbundetBelopForHverUttrykk(StedbundetBelopUttrykk<K> uttrykk, Function<BelopUttrykk, Uttrykk<Belop>> forHverFunksjon) {

        this.uttrykk = uttrykk;
        this.forHverFunksjon = forHverFunksjon;
    }

    @Override
    public StedbundetBelop<K> eval(UttrykkContext ctx) {

        return ctx.eval(uttrykk).splitt().stream()
                .map(bs -> kr(ctx.eval(forHverFunksjon.apply(kr(bs.getBelop()))), bs.getSted()))
                .reduce(kr0(), StedbundetBelop::pluss);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(forHverFunksjon.apply(new BelopNullUttrykk() {
            public String beskriv(UttrykkContext ctx) { return "hver i "+ctx.beskriv(uttrykk); }
        }));
    }

    private abstract static class BelopNullUttrykk extends KonstantUttrykk<Belop> implements BelopUttrykk {
        protected BelopNullUttrykk() {
            super(Belop.NULL);
        }
    }

}
