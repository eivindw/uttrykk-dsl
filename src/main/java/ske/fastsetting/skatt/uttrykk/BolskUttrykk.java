package ske.fastsetting.skatt.uttrykk;

public abstract class BolskUttrykk<B extends BolskUttrykk<B,C>, C> extends AbstractUttrykk<Boolean, B, C> {
    public BolskUttrykk og(BolskUttrykk<?,C> uttrykk) {
        return new OgUttrykk<C>(this, uttrykk);
    }

    public BolskUttrykk eller(BolskUttrykk<?,C> uttrykk) {
        return new EllerUttrykk<C>(this, uttrykk);
    }

    static class OgUttrykk<C> extends BolskUttrykk<OgUttrykk<C>, C> {

        private final BolskUttrykk<?,C> forsteUttrykk;
        private final BolskUttrykk<?,C> andreUttrykk;

        public OgUttrykk(BolskUttrykk<?,C> forsteUttrykk, BolskUttrykk<?,C> andreUttrykk) {
            this.forsteUttrykk = forsteUttrykk;
            this.andreUttrykk = andreUttrykk;
        }

        @Override
        public Boolean eval(UttrykkContext<C> ctx) {
            return ctx.eval(forsteUttrykk) && ctx.eval(andreUttrykk);
        }

        @Override
        public String beskriv(UttrykkContext<C> ctx) {
            return ctx.beskriv(forsteUttrykk) + " og " + ctx.beskriv(andreUttrykk);
        }
    }

    static class EllerUttrykk<C> extends BolskUttrykk<EllerUttrykk<C>,C> {
        private final BolskUttrykk<?,C> forsteUttrykk;
        private final BolskUttrykk<?,C> andreUttrykk;

        public EllerUttrykk(BolskUttrykk<?,C> forsteUttrykk, BolskUttrykk<?,C> andreUttrykk) {
            this.forsteUttrykk = forsteUttrykk;
            this.andreUttrykk = andreUttrykk;
        }

        @Override
        public Boolean eval(UttrykkContext<C> ctx) {
            return ctx.eval(forsteUttrykk) || ctx.eval(andreUttrykk);
        }

        @Override
        public String beskriv(UttrykkContext<C> ctx) {
            return ctx.beskriv(forsteUttrykk) + " eller " + ctx.beskriv(andreUttrykk);
        }
    }
}
