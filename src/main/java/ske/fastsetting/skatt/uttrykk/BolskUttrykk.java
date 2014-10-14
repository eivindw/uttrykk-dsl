package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.beregn.Uttrykk;
import ske.fastsetting.skatt.beregn.UttrykkContext;

public interface BolskUttrykk extends Uttrykk<Boolean> {
    default BolskUttrykk og(BolskUttrykk uttrykk) {
        return new OgUttrykk(this, uttrykk);
    }

    default BolskUttrykk eller(BolskUttrykk uttrykk) {
        return new EllerUttrykk(this, uttrykk);
    }

    static class OgUttrykk extends RegelUttrykk<OgUttrykk, Boolean> implements BolskUttrykk {

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

    static class EllerUttrykk extends RegelUttrykk<EllerUttrykk, Boolean> implements BolskUttrykk {
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
