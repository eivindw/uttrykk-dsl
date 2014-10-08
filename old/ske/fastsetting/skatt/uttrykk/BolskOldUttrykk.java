package ske.fastsetting.skatt.uttrykk;

public interface BolskOldUttrykk extends OldUttrykk<Boolean> {
    default BolskOldUttrykk og(BolskOldUttrykk uttrykk) {
        return new OgOldUttrykk(this, uttrykk);
    }

    default BolskOldUttrykk eller(BolskOldUttrykk uttrykk) {
        return new EllerOldUttrykk(this, uttrykk);
    }

    static class OgOldUttrykk extends RegelUttrykk<OgOldUttrykk> implements BolskOldUttrykk {

        private final BolskOldUttrykk forsteUttrykk;
        private final BolskOldUttrykk andreUttrykk;


        public OgOldUttrykk(BolskOldUttrykk forsteUttrykk, BolskOldUttrykk andreUttrykk) {

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

    static class EllerOldUttrykk extends RegelUttrykk<EllerOldUttrykk> implements BolskOldUttrykk {
        private final BolskOldUttrykk forsteUttrykk;
        private final BolskOldUttrykk andreUttrykk;

        public EllerOldUttrykk(BolskOldUttrykk forsteUttrykk, BolskOldUttrykk andreUttrykk) {
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
