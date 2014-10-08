package ske.fastsetting.skatt.uttrykk;

public interface CompareableOldUttrykk<T extends Comparable<T>> extends OldUttrykk<T> {

    default BolskOldUttrykk erStorreEnn(CompareableOldUttrykk<T> belop) {
        return new ErStorreEnn(this,belop);
    }

    default BolskOldUttrykk er(CompareableOldUttrykk<T> belop) {
        return new ErLik(this,belop);
    }

    default BolskOldUttrykk ikkeEr(CompareableOldUttrykk<T> belop) {
        return new IkkeErLik(this,belop);
    }

    default BolskOldUttrykk erMellom(CompareableOldUttrykk<T> fra, CompareableOldUttrykk<T> til) {
        return new ErMellom(this,fra,til);
    }

    default BolskOldUttrykk erMindreEnnEllerLik(CompareableOldUttrykk<T> belop) {
        return new ErMindreEnnEllerLik(this, belop);
    }

    default BolskOldUttrykk erMindreEnn(CompareableOldUttrykk<T> belop) {
        return new ErMindreEnn(this, belop);
    }
    static class ErStorreEnn<T extends Comparable<T>> implements BolskOldUttrykk {
        private final CompareableOldUttrykk<T> belopUttrykk;
        private final CompareableOldUttrykk<T> sammenliknMed;

        public ErStorreEnn(CompareableOldUttrykk<T> belopUttrykk, CompareableOldUttrykk<T> sammenliknMed) {
            this.belopUttrykk = belopUttrykk;
            this.sammenliknMed = sammenliknMed;
        }

        @Override
        public Boolean evaluer() {
            return belopUttrykk.evaluer().compareTo(sammenliknMed.evaluer())>0;
        }

        @Override
        public void beskrivEvaluering(UttrykkBeskriver beskriver) {
            belopUttrykk.beskrivEvaluering(beskriver);
            beskriver.skriv(evaluer() ? "er større enn" : "ikke er større enn");
            sammenliknMed.beskrivEvaluering(beskriver);
        }

        @Override
        public void beskrivGenerisk(UttrykkBeskriver beskriver) {
            belopUttrykk.beskrivGenerisk(beskriver);
            beskriver.skriv("er større enn");
            sammenliknMed.beskrivGenerisk(beskriver);
        }
    }

    static class ErLik<T extends Comparable<T>> implements BolskOldUttrykk {
        private final CompareableOldUttrykk<T> belopUttrykk1;
        private final CompareableOldUttrykk<T> belopUttrykk2;

        public ErLik(CompareableOldUttrykk<T> belopUttrykk1, CompareableOldUttrykk<T> belopUttrykk2) {
            this.belopUttrykk1 = belopUttrykk1;
            this.belopUttrykk2 = belopUttrykk2;
        }

        @Override
        public Boolean evaluer() {
            return belopUttrykk1.evaluer().compareTo(belopUttrykk2.evaluer())==0;
        }

        @Override
        public void beskrivEvaluering(UttrykkBeskriver beskriver) {
            belopUttrykk1.beskrivEvaluering(beskriver);
            beskriver.skriv(evaluer() ? "er lik" : "ikke er lik");
            belopUttrykk2.beskrivEvaluering(beskriver);

        }

        @Override
        public void beskrivGenerisk(UttrykkBeskriver beskriver) {
            belopUttrykk1.beskrivGenerisk(beskriver);
            beskriver.skriv("er lik");
            belopUttrykk2.beskrivGenerisk(beskriver);

        }

    }

    static class IkkeErLik<T extends Comparable<T>> implements BolskOldUttrykk {
        private final CompareableOldUttrykk<T> belopUttrykk1;
        private final CompareableOldUttrykk<T> belopUttrykk2;

        public IkkeErLik(CompareableOldUttrykk<T> belopUttrykk1, CompareableOldUttrykk<T> belopUttrykk2) {
            this.belopUttrykk1 = belopUttrykk1;
            this.belopUttrykk2 = belopUttrykk2;
        }

        @Override
        public Boolean evaluer() {
            return belopUttrykk1.evaluer().compareTo(belopUttrykk2.evaluer())!=0;
        }

        @Override
        public void beskrivEvaluering(UttrykkBeskriver beskriver) {
            belopUttrykk1.beskrivEvaluering(beskriver);
            beskriver.skriv(evaluer() ? "ikke er lik" : "er lik");
            belopUttrykk2.beskrivEvaluering(beskriver);

        }
        @Override

        public void beskrivGenerisk(UttrykkBeskriver beskriver) {
            belopUttrykk1.beskrivGenerisk(beskriver);
            beskriver.skriv("ikke er lik");
            belopUttrykk2.beskrivGenerisk(beskriver);

        }
    }

    static class ErMellom<T extends Comparable<T>> implements BolskOldUttrykk {
        private final CompareableOldUttrykk<T> belopUttrykk;
        private final CompareableOldUttrykk<T> fraBelopUttrykk;
        private final CompareableOldUttrykk<T> tilBelopUttrykk;

        public ErMellom(CompareableOldUttrykk<T> belopUttrykk, CompareableOldUttrykk<T> fra, CompareableOldUttrykk<T> til) {
            this.belopUttrykk = belopUttrykk;
            fraBelopUttrykk = fra;
            tilBelopUttrykk = til;
        }

        @Override
        public Boolean evaluer() {
            T belop = belopUttrykk.evaluer();
            T fra = fraBelopUttrykk.evaluer();
            T til = tilBelopUttrykk.evaluer();

            return belop.compareTo(fra)>0 && belop.compareTo(til)<0;
        }

        @Override
        public void beskrivEvaluering(UttrykkBeskriver beskriver) {
            belopUttrykk.beskrivEvaluering(beskriver);
            beskriver.skriv(evaluer() ? "er mellom" : "er ikke mellom");
            fraBelopUttrykk.beskrivEvaluering(beskriver);
            beskriver.skriv("og");
            tilBelopUttrykk.beskrivEvaluering(beskriver);

        }

        @Override
        public void beskrivGenerisk(UttrykkBeskriver beskriver) {
            belopUttrykk.beskrivGenerisk(beskriver);
            beskriver.skriv("er mellom");
            fraBelopUttrykk.beskrivGenerisk(beskriver);
            beskriver.skriv("og");
            tilBelopUttrykk.beskrivGenerisk(beskriver);

        }

    }

    static class ErMindreEnnEllerLik<T extends Comparable<T>> implements BolskOldUttrykk {
        private final CompareableOldUttrykk<T> belopUttrykk;
        private final CompareableOldUttrykk<T> sammenliknMed;

        public ErMindreEnnEllerLik(CompareableOldUttrykk<T> belopUttrykk, CompareableOldUttrykk<T> sammenliknMed) {
            this.belopUttrykk = belopUttrykk;
            this.sammenliknMed = sammenliknMed;
        }

        @Override
        public Boolean evaluer() {
            return belopUttrykk.evaluer().compareTo(sammenliknMed.evaluer())<=0;
        }

        @Override
        public void beskrivEvaluering(UttrykkBeskriver beskriver) {
            belopUttrykk.beskrivEvaluering(beskriver);
            beskriver.skriv(evaluer() ? "er mindre enn eller lik" : "er større enn");
            sammenliknMed.beskrivEvaluering(beskriver);
        }

        @Override
        public void beskrivGenerisk(UttrykkBeskriver beskriver) {
            belopUttrykk.beskrivGenerisk(beskriver);
            beskriver.skriv("er mindre enn eller lik");
            sammenliknMed.beskrivGenerisk(beskriver);
        }

    }

    static class ErMindreEnn<T extends Comparable<T>> implements BolskOldUttrykk {
        private final CompareableOldUttrykk<T> belopUttrykk;
        private final CompareableOldUttrykk<T> sammenliknMed;

        public ErMindreEnn(CompareableOldUttrykk<T> belopUttrykk, CompareableOldUttrykk<T> sammenliknMed) {
            this.belopUttrykk = belopUttrykk;
            this.sammenliknMed = sammenliknMed;
        }

        @Override
        public Boolean evaluer() {
            return belopUttrykk.evaluer().compareTo(sammenliknMed.evaluer())<0;
        }

        @Override
        public void beskrivEvaluering(UttrykkBeskriver beskriver) {
            belopUttrykk.beskrivEvaluering(beskriver);
            beskriver.skriv(evaluer() ? "er mindre enn eller lik" : "er større enn");
            sammenliknMed.beskrivEvaluering(beskriver);
        }

        @Override
        public void beskrivGenerisk(UttrykkBeskriver beskriver) {
            belopUttrykk.beskrivGenerisk(beskriver);
            beskriver.skriv("er mindre enn eller lik");
            sammenliknMed.beskrivGenerisk(beskriver);
        }

    }
}
