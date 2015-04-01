package ske.fastsetting.skatt.uttrykk;

public abstract class BolskUttrykk extends AbstractUttrykk<Boolean, BolskUttrykk> {
    public BolskUttrykk og(BolskUttrykk uttrykk) {
        return new OgUttrykk(this, uttrykk);
    }

    public BolskUttrykk eller(BolskUttrykk uttrykk) {
        return new EllerUttrykk(this, uttrykk);
    }

    static class OgUttrykk extends BolskUttrykk {

        private final BolskUttrykk forsteUttrykk;
        private final BolskUttrykk andreUttrykk;

        public OgUttrykk(BolskUttrykk forsteUttrykk, BolskUttrykk andreUttrykk) {
            this.forsteUttrykk = forsteUttrykk;
            this.andreUttrykk = andreUttrykk;
        }

        @Override
        public Boolean eval(UttrykkContext ctx) {
            return ctx.eval(forsteUttrykk) && ctx.eval(andreUttrykk);
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(forsteUttrykk) + " og " + ctx.beskriv(andreUttrykk);
        }
    }

    static class EllerUttrykk extends BolskUttrykk {
        private final BolskUttrykk forsteUttrykk;
        private final BolskUttrykk andreUttrykk;

        public EllerUttrykk(BolskUttrykk forsteUttrykk, BolskUttrykk andreUttrykk) {
            this.forsteUttrykk = forsteUttrykk;
            this.andreUttrykk = andreUttrykk;
        }

        @Override
        public Boolean eval(UttrykkContext ctx) {
            return ctx.eval(forsteUttrykk) || ctx.eval(andreUttrykk);
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(forsteUttrykk) + " eller " + ctx.beskriv(andreUttrykk);
        }
    }
}
