package ske.fastsetting.skatt.uttrykk;

public interface BolskUttrykk extends Uttrykk<Boolean> {
    default BolskUttrykk og(BolskUttrykk uttrykk) {
        return new OgUttrykk(this, uttrykk);
    }

    default BolskUttrykk eller(BolskUttrykk uttrykk) {
        return new EllerUttrykk(this, uttrykk);
    }

    static class OgUttrykk extends RegelUttrykk<OgUttrykk> implements BolskUttrykk {

        private final BolskUttrykk forsteUttrykk;
        private final BolskUttrykk andreUttrykk;


        public OgUttrykk(BolskUttrykk forsteUttrykk, BolskUttrykk andreUttrykk) {

            this.forsteUttrykk = forsteUttrykk;
            this.andreUttrykk = andreUttrykk;
        }


        @Override
        public Boolean evaluer() {
            return forsteUttrykk.evaluer() && andreUttrykk.evaluer();
        }

        @Override
        public void beskrivEvaluering(UttrykkBeskriver beskriver) {
            forsteUttrykk.beskrivEvaluering(beskriver);
            beskriver.skriv("og");
            andreUttrykk.beskrivEvaluering(beskriver);
        }

        @Override
        public void beskrivGeneriskMedRegel(UttrykkBeskriver beskriver) {
            forsteUttrykk.beskrivGenerisk(beskriver);
            beskriver.skriv("og");
            andreUttrykk.beskrivGenerisk(beskriver);
        }
    }

    static class EllerUttrykk extends RegelUttrykk<EllerUttrykk> implements BolskUttrykk {
        private final BolskUttrykk forsteUttrykk;
        private final BolskUttrykk andreUttrykk;

        public EllerUttrykk(BolskUttrykk forsteUttrykk, BolskUttrykk andreUttrykk) {
            this.forsteUttrykk = forsteUttrykk;
            this.andreUttrykk = andreUttrykk;
        }

        @Override
        public Boolean evaluer() {
            return forsteUttrykk.evaluer() || andreUttrykk.evaluer();
        }

        @Override
        public void beskrivEvaluering(UttrykkBeskriver beskriver) {
            if (forsteUttrykk.evaluer()) {
                forsteUttrykk.beskrivEvaluering(beskriver);
            } else if (andreUttrykk.evaluer()) {
                andreUttrykk.beskrivEvaluering(beskriver);
            } else {
                forsteUttrykk.beskrivEvaluering(beskriver);
                beskriver.skriv("og");
                andreUttrykk.beskrivEvaluering(beskriver);
            }
        }

        @Override
        public void beskrivGeneriskMedRegel(UttrykkBeskriver beskriver) {
            forsteUttrykk.beskrivGenerisk(beskriver);
            beskriver.skriv("eller");
            andreUttrykk.beskrivGenerisk(beskriver);
        }
    }
}
