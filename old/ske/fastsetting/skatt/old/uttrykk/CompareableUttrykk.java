package ske.fastsetting.skatt.old.uttrykk;

public interface CompareableUttrykk<T extends Comparable<T>> extends Uttrykk<T> {

    default BolskUttrykk erStorreEnn(CompareableUttrykk<T> belop) {
        return new ErStorreEnn(this,belop);
    }

    default BolskUttrykk er(CompareableUttrykk<T> belop) {
        return new ErLik(this,belop);
    }

    default BolskUttrykk ikkeEr(CompareableUttrykk<T> belop) {
        return new IkkeErLik(this,belop);
    }

    default BolskUttrykk erMellom(CompareableUttrykk<T> fra, CompareableUttrykk<T> til) {
        return new ErMellom(this,fra,til);
    }

    default BolskUttrykk erMindreEnnEllerLik(CompareableUttrykk<T> belop) {
        return new ErMindreEnnEllerLik(this, belop);
    }

    default BolskUttrykk erMindreEnn(CompareableUttrykk<T> belop) {
        return new ErMindreEnn(this, belop);
    }
    static class ErStorreEnn<T extends Comparable<T>> implements BolskUttrykk {
        private final CompareableUttrykk<T> belopUttrykk;
        private final CompareableUttrykk<T> sammenliknMed;

        public ErStorreEnn(CompareableUttrykk<T> belopUttrykk, CompareableUttrykk<T> sammenliknMed) {
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

    static class ErLik<T extends Comparable<T>> implements BolskUttrykk {
        private final CompareableUttrykk<T> belopUttrykk1;
        private final CompareableUttrykk<T> belopUttrykk2;

        public ErLik(CompareableUttrykk<T> belopUttrykk1, CompareableUttrykk<T> belopUttrykk2) {
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

    static class IkkeErLik<T extends Comparable<T>> implements BolskUttrykk {
        private final CompareableUttrykk<T> belopUttrykk1;
        private final CompareableUttrykk<T> belopUttrykk2;

        public IkkeErLik(CompareableUttrykk<T> belopUttrykk1, CompareableUttrykk<T> belopUttrykk2) {
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

    static class ErMellom<T extends Comparable<T>> implements BolskUttrykk {
        private final CompareableUttrykk<T> belopUttrykk;
        private final CompareableUttrykk<T> fraBelopUttrykk;
        private final CompareableUttrykk<T> tilBelopUttrykk;

        public ErMellom(CompareableUttrykk<T> belopUttrykk, CompareableUttrykk<T> fra, CompareableUttrykk<T> til) {
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

    static class ErMindreEnnEllerLik<T extends Comparable<T>> implements BolskUttrykk {
        private final CompareableUttrykk<T> belopUttrykk;
        private final CompareableUttrykk<T> sammenliknMed;

        public ErMindreEnnEllerLik(CompareableUttrykk<T> belopUttrykk, CompareableUttrykk<T> sammenliknMed) {
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

    static class ErMindreEnn<T extends Comparable<T>> implements BolskUttrykk {
        private final CompareableUttrykk<T> belopUttrykk;
        private final CompareableUttrykk<T> sammenliknMed;

        public ErMindreEnn(CompareableUttrykk<T> belopUttrykk, CompareableUttrykk<T> sammenliknMed) {
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
