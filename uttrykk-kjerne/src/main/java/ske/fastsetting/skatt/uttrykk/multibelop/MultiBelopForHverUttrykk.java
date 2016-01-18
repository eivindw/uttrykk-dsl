package ske.fastsetting.skatt.uttrykk.multibelop;

import static ske.fastsetting.skatt.domene.MultiBelop.kr;
import static ske.fastsetting.skatt.domene.MultiBelop.kr0;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;

import java.util.function.Function;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.KonstantUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

/**
 * Created by jorn ola birkeland on 02.11.15.
 */
public class MultiBelopForHverUttrykk<K>
  extends AbstractUttrykk<MultiBelop<K>,MultiBelopForHverUttrykk<K>>
  implements MultiBelopUttrykk<K> {

    private final MultiBelopUttrykk<K> uttrykk;
    private final Function<BelopUttrykk, Uttrykk<Belop>> forHverFunksjon;

    public MultiBelopForHverUttrykk(MultiBelopUttrykk<K> uttrykk, Function<BelopUttrykk, Uttrykk<Belop>> forHverFunksjon) {

        this.uttrykk = uttrykk;
        this.forHverFunksjon = forHverFunksjon;
    }

    @Override
    public MultiBelop<K> eval(UttrykkContext ctx) {

        return ctx.eval(uttrykk).splitt().stream()
          .map(bs -> kr(ctx.eval(forHverFunksjon.apply(kr(bs.getBelop()))), bs.getNokkel()))
          .reduce(kr0(), MultiBelop::pluss);
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