package ske.fastsetting.skatt.beregn;

public class HvisUttrykk<T> extends AbstractUttrykk<T> {

    private final BoolskUttrykk boolskUttrykk;
    private final Uttrykk<T> santUttrykk;
    private final Uttrykk<T> usantUttrykk;

    public HvisUttrykk(BoolskUttrykk boolskUttrykk, Uttrykk<T> santUttrykk, Uttrykk<T> usantUttrykk) {
        this.boolskUttrykk = boolskUttrykk;
        this.santUttrykk = santUttrykk;
        this.usantUttrykk = usantUttrykk;
    }

    @Override
    public T eval(UttrykkContext ctx) {
        return ctx.eval(boolskUttrykk) ? ctx.eval(santUttrykk) : ctx.eval(usantUttrykk);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return
            "hvis " + ctx.beskriv(boolskUttrykk) +
            " så " + ctx.beskriv(santUttrykk) +
            " ellers " + ctx.beskriv(usantUttrykk);
    }

    public static HvisUttrykkBuilder hvis(BoolskUttrykk boolskUttrykk) {
        return new HvisUttrykkBuilderImpl(boolskUttrykk);
    }

    public interface EllersUttrykkBuilder {
        HvisUttrykk ellers(Uttrykk uttrykk);
    }

    public interface HvisUttrykkBuilder {
        EllersUttrykkBuilder saa(Uttrykk uttrykk);
    }

    private static class HvisUttrykkBuilderImpl implements HvisUttrykkBuilder, EllersUttrykkBuilder {

        private final BoolskUttrykk boolskUttrykk;
        private Uttrykk<?> santUttrykk;

        public HvisUttrykkBuilderImpl(BoolskUttrykk boolskUttrykk) {
            this.boolskUttrykk = boolskUttrykk;
        }

        @Override
        public EllersUttrykkBuilder saa(Uttrykk uttrykk) {
            santUttrykk = uttrykk;
            return this;
        }

        @Override
        public HvisUttrykk ellers(Uttrykk uttrykk) {
            return new HvisUttrykk<>(boolskUttrykk, santUttrykk, uttrykk);
        }
    }
}
