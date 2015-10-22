package ske.fastsetting.skatt.uttrykk.skalSlettes;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.bolsk.BolskUttrykk;

import java.util.stream.Stream;

/**
 * Created by jorn ola birkeland on 29.06.15.
 */
public class FinnmarkBeregning {

    public static AbstractUttrykk<Belop,?> finnmarkberegningAv(BelopUttrykk belopUttrykk) {
        return new StartFinnmarkBeregningUttrykk(belopUttrykk);
    }

    public static BolskUttrykk erFinnmarkberegning() {
        return new ErFinnmarkBeregningUttrykk();
    }

    private static class FinnmarkBeregningContext extends UttrykkContextImpl {

        private FinnmarkBeregningContext(Object[] input) {
            super(input);
        }

        public static FinnmarkBeregningContext ny(UttrykkContext ctx) {
            Object[] input = Stream.concat(Stream.of(ctx.input()),Stream.of(new FinnmarkBeregning()))
                    .toArray(Object[]::new);

            return new FinnmarkBeregningContext(input);
        }
    }

    private static class StartFinnmarkBeregningUttrykk extends AbstractUttrykk<Belop, StartFinnmarkBeregningUttrykk> implements BelopUttrykk {

        BelopUttrykk uttrykk;

        public StartFinnmarkBeregningUttrykk(BelopUttrykk belopUttrykk) {
            this.uttrykk=belopUttrykk;
        }

        @Override
        public Belop eval(UttrykkContext ctx) {

            return ctx.harInput(FinnmarkBeregning.class)
                    ? ctx.eval(uttrykk)
                    : FinnmarkBeregningContext.ny(ctx).eval(uttrykk) ;
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return "finnmarksberegning av "+ctx.beskriv(uttrykk);
        }
    }


    private static class ErFinnmarkBeregningUttrykk extends BolskUttrykk {

        @Override
        public Boolean eval(UttrykkContext ctx) {
            return ctx.harInput(FinnmarkBeregning.class);
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return "er finnmarksberegning";
        }
    }

}
