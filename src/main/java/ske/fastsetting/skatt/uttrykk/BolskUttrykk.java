package ske.fastsetting.skatt.uttrykk;

public abstract class BolskUttrykk<C> extends AbstractUttrykk<Boolean, BolskUttrykk<C>, C> {
    public BolskUttrykk<C> og(BolskUttrykk<C> uttrykk) {
        return new OgUttrykk<C>(this, uttrykk);
    }

    public BolskUttrykk<C> eller(BolskUttrykk<C> uttrykk) {
        return new EllerUttrykk<C>(this, uttrykk);
    }

    static class OgUttrykk<C> extends BolskUttrykk<C> {

        private final BolskUttrykk<C> forsteUttrykk;
        private final BolskUttrykk<C> andreUttrykk;

        public OgUttrykk(BolskUttrykk<C> forsteUttrykk, BolskUttrykk<C> andreUttrykk) {
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

    static class EllerUttrykk<C> extends BolskUttrykk<C> {
        private final BolskUttrykk<C> forsteUttrykk;
        private final BolskUttrykk<C> andreUttrykk;

        public EllerUttrykk(BolskUttrykk<C> forsteUttrykk, BolskUttrykk<C> andreUttrykk) {
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
