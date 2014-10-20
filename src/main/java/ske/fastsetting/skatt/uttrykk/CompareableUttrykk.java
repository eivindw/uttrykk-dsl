package ske.fastsetting.skatt.uttrykk;

public interface CompareableUttrykk<T extends Comparable<T>, B extends CompareableUttrykk<T,B,C>, C> extends Uttrykk<T, B,C> {

    default BolskUttrykk<?,C> erStorreEnn(CompareableUttrykk<T, ?,C> belop) {
        return new ErStorreEnn<T,C>(this, belop);
    }

    default BolskUttrykk<?,C> er(CompareableUttrykk<T, ?,C> belop) {
        return new ErLik<T,C>(this, belop);
    }

    default BolskUttrykk<?,C> ikkeEr(CompareableUttrykk<T, ?,C> belop) {
        return new IkkeErLik<T,C>(this, belop);
    }

    default BolskUttrykk<?,C> erMellom(CompareableUttrykk<T, ?,C> fra, CompareableUttrykk<T, ?,C> til) {
        return new ErMellom<T,C>(this, fra, til);
    }

    default BolskUttrykk<?,C> erMindreEnnEllerLik(CompareableUttrykk<T, ?,?> belop) {
        return new ErMindreEnnEllerLik(this, belop);
    }

    default BolskUttrykk<?,?> erMindreEnn(CompareableUttrykk<T, ?,?> belop) {
        return new ErMindreEnn(this, belop);
    }

    static class ErStorreEnn<T extends Comparable<T>,C> extends BolskUttrykk<ErStorreEnn<T,C>,C> {
        private final CompareableUttrykk<T, ?,C> belopUttrykk;
        private final CompareableUttrykk<T, ?, C> sammenliknMed;

        public ErStorreEnn(CompareableUttrykk<T, ?, C> belopUttrykk, CompareableUttrykk<T, ?, C> sammenliknMed) {
            this.belopUttrykk = belopUttrykk;
            this.sammenliknMed = sammenliknMed;
        }

        @Override
        public Boolean eval(UttrykkContext<C> ctx) {
            return ctx.eval(belopUttrykk).compareTo(ctx.eval(sammenliknMed)) > 0;
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(belopUttrykk) + " er større enn " + ctx.beskriv(sammenliknMed);
        }
    }

    static class ErLik<T extends Comparable<T>,C> extends BolskUttrykk<ErLik<T,C>,C> {
        private final CompareableUttrykk<T, ?, C> belopUttrykk1;
        private final CompareableUttrykk<T, ?, C> belopUttrykk2;

        public ErLik(CompareableUttrykk<T, ?, C> belopUttrykk1, CompareableUttrykk<T, ?, C> belopUttrykk2) {
            this.belopUttrykk1 = belopUttrykk1;
            this.belopUttrykk2 = belopUttrykk2;
        }

        @Override
        public Boolean eval(UttrykkContext<C> ctx) {
            return ctx.eval(belopUttrykk1).compareTo(ctx.eval(belopUttrykk2)) == 0;
        }

        @Override
        public String beskriv(UttrykkContext<C> ctx) {
            return ctx.beskriv(belopUttrykk1) + " er lik " + ctx.beskriv(belopUttrykk2);
        }
    }

    static class IkkeErLik<T extends Comparable<T>, C> extends BolskUttrykk<IkkeErLik<T,C>, C> {
        private final CompareableUttrykk<T, ?, C> belopUttrykk1;
        private final CompareableUttrykk<T, ?, C> belopUttrykk2;

        public IkkeErLik(CompareableUttrykk<T, ?, C> belopUttrykk1, CompareableUttrykk<T, ?,C> belopUttrykk2) {
            this.belopUttrykk1 = belopUttrykk1;
            this.belopUttrykk2 = belopUttrykk2;
        }

        @Override
        public Boolean eval(UttrykkContext<C> ctx) {
            return ctx.eval(belopUttrykk1).compareTo(ctx.eval(belopUttrykk2)) != 0;
        }

        @Override
        public String beskriv(UttrykkContext<C> ctx) {
            return ctx.beskriv(belopUttrykk1) + " ikke er lik " + ctx.beskriv(belopUttrykk2);
        }
    }

    static class ErMellom<T extends Comparable<T>, C> extends BolskUttrykk<ErMellom<T,C>,C> {
        private final CompareableUttrykk<T, ?, C> belopUttrykk;
        private final CompareableUttrykk<T, ?, C> fraBelopUttrykk;
        private final CompareableUttrykk<T, ?, C> tilBelopUttrykk;

        public ErMellom(CompareableUttrykk<T, ?, C> belopUttrykk, CompareableUttrykk<T, ?, C> fra, CompareableUttrykk<T, ?, C> til) {
            this.belopUttrykk = belopUttrykk;
            fraBelopUttrykk = fra;
            tilBelopUttrykk = til;
        }

        @Override
        public Boolean eval(UttrykkContext<C> ctx) {
            T belop = ctx.eval(belopUttrykk);
            T fra = ctx.eval(fraBelopUttrykk);
            T til = ctx.eval(tilBelopUttrykk);

            return belop.compareTo(fra) > 0 && belop.compareTo(til) < 0;
        }

        @Override
        public String beskriv(UttrykkContext<C> ctx) {
            return String.format("%s er mellom %s og %s",
                ctx.beskriv(belopUttrykk), ctx.beskriv(fraBelopUttrykk), ctx.beskriv(tilBelopUttrykk));
        }


    }

    static class ErMindreEnnEllerLik<T extends Comparable<T>, C> extends BolskUttrykk<ErMindreEnnEllerLik<T,C>, C> {
        private final CompareableUttrykk<T, ?, C> belopUttrykk;
        private final CompareableUttrykk<T, ?, C> sammenliknMed;

        public ErMindreEnnEllerLik(CompareableUttrykk<T, ?, C> belopUttrykk, CompareableUttrykk<T, ?, C> sammenliknMed) {
            this.belopUttrykk = belopUttrykk;
            this.sammenliknMed = sammenliknMed;
        }

        @Override
        public Boolean eval(UttrykkContext<C> ctx) {
            return ctx.eval(belopUttrykk).compareTo(ctx.eval(sammenliknMed)) <= 0;
        }

        @Override
        public String beskriv(UttrykkContext<C> ctx) {
            return ctx.beskriv(belopUttrykk) + " er mindre enn eller lik " + ctx.beskriv(sammenliknMed);
        }
    }

    static class ErMindreEnn<T extends Comparable<T>, C> extends BolskUttrykk<ErMindreEnn<T,C>, C> {
        private final CompareableUttrykk<T, ?, C> belopUttrykk;
        private final CompareableUttrykk<T, ?, C> sammenliknMed;

        public ErMindreEnn(CompareableUttrykk<T, ?, C> belopUttrykk, CompareableUttrykk<T, ?, C> sammenliknMed) {
            this.belopUttrykk = belopUttrykk;
            this.sammenliknMed = sammenliknMed;
        }

        @Override
        public Boolean eval(UttrykkContext<C> ctx) {
            return ctx.eval(belopUttrykk).compareTo(ctx.eval(sammenliknMed)) < 0;
        }

        @Override
        public String beskriv(UttrykkContext<C> ctx) {
            return ctx.beskriv(belopUttrykk) + " er mindre enn eller lik " + ctx.beskriv(sammenliknMed);
        }
    }
}
