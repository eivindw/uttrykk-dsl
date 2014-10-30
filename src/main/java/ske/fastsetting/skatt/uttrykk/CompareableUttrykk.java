package ske.fastsetting.skatt.uttrykk;

public interface CompareableUttrykk<T extends Comparable<T>> extends Uttrykk<T> {

    default BolskUttrykk erStorreEnn(CompareableUttrykk<T> belop) {
        return new ErStorreEnn<>(this, belop);
    }

    default BolskUttrykk er(CompareableUttrykk<T> belop) {
        return new ErLik<>(this, belop);
    }

    default BolskUttrykk ikkeEr(CompareableUttrykk<T> belop) {
        return new IkkeErLik<>(this, belop);
    }

    default FraTilUttrykk<T> erFra(CompareableUttrykk<T> belop) {
        return FraTilUttrykk.fra(this, belop);
    }

    default FraTilUttrykk<T> erFraOgMed(CompareableUttrykk<T> belop) {
        return FraTilUttrykk.fraOgMed(this, belop);
    }

    default BolskUttrykk erInntil(CompareableUttrykk<T> belop) {
        return FraTilUttrykk.til(this, belop);
    }

    default BolskUttrykk erTilOgMed(CompareableUttrykk<T> belop) {
        return FraTilUttrykk.tilOgMed(this, belop);
    }

    default BolskUttrykk erMellom(CompareableUttrykk<T> fra, CompareableUttrykk<T> til) {
        return new ErMellom<>(this, fra, til);
    }

    default BolskUttrykk erMindreEnnEllerLik(CompareableUttrykk<T> belop) {
        return new ErMindreEnnEllerLik<>(this, belop);
    }

    default BolskUttrykk erMindreEnn(CompareableUttrykk<T> belop) {
        return new ErMindreEnn<>(this, belop);
    }

    static class ErStorreEnn<T extends Comparable<T>> extends BolskUttrykk {
        private final CompareableUttrykk<T> belopUttrykk;
        private final CompareableUttrykk<T> sammenliknMed;

        public ErStorreEnn(CompareableUttrykk<T> belopUttrykk, CompareableUttrykk<T> sammenliknMed) {
            this.belopUttrykk = belopUttrykk;
            this.sammenliknMed = sammenliknMed;
        }

        @Override
        public Boolean eval(UttrykkContext ctx) {
            return ctx.eval(belopUttrykk).compareTo(ctx.eval(sammenliknMed)) > 0;
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(belopUttrykk) + " > " + ctx.beskriv(sammenliknMed);
        }
    }

    static class ErLik<T extends Comparable<T>> extends BolskUttrykk {
        private final CompareableUttrykk<T> belopUttrykk1;
        private final CompareableUttrykk<T> belopUttrykk2;

        public ErLik(CompareableUttrykk<T> belopUttrykk1, CompareableUttrykk<T> belopUttrykk2) {
            this.belopUttrykk1 = belopUttrykk1;
            this.belopUttrykk2 = belopUttrykk2;
        }

        @Override
        public Boolean eval(UttrykkContext ctx) {
            return ctx.eval(belopUttrykk1).compareTo(ctx.eval(belopUttrykk2)) == 0;
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(belopUttrykk1) + " = " + ctx.beskriv(belopUttrykk2);
        }
    }

    static class IkkeErLik<T extends Comparable<T>> extends BolskUttrykk {
        private final CompareableUttrykk<T> belopUttrykk1;
        private final CompareableUttrykk<T> belopUttrykk2;

        public IkkeErLik(CompareableUttrykk<T> belopUttrykk1, CompareableUttrykk<T> belopUttrykk2) {
            this.belopUttrykk1 = belopUttrykk1;
            this.belopUttrykk2 = belopUttrykk2;
        }

        @Override
        public Boolean eval(UttrykkContext ctx) {
            return ctx.eval(belopUttrykk1).compareTo(ctx.eval(belopUttrykk2)) != 0;
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(belopUttrykk1) + " != " + ctx.beskriv(belopUttrykk2);
        }
    }

    static class ErMellom<T extends Comparable<T>> extends BolskUttrykk {
        private final CompareableUttrykk<T> belopUttrykk;
        private final CompareableUttrykk<T> fraBelopUttrykk;
        private final CompareableUttrykk<T> tilBelopUttrykk;

        public ErMellom(CompareableUttrykk<T> belopUttrykk, CompareableUttrykk<T> fra, CompareableUttrykk<T> til) {
            this.belopUttrykk = belopUttrykk;
            fraBelopUttrykk = fra;
            tilBelopUttrykk = til;
        }

        @Override
        public Boolean eval(UttrykkContext ctx) {
            T belop = ctx.eval(belopUttrykk);
            T fra = ctx.eval(fraBelopUttrykk);
            T til = ctx.eval(tilBelopUttrykk);

            return belop.compareTo(fra) > 0 && belop.compareTo(til) < 0;
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return String.format("%2$s < %1$s < %3$s",
                ctx.beskriv(belopUttrykk), ctx.beskriv(fraBelopUttrykk), ctx.beskriv(tilBelopUttrykk));
        }
    }

    static class ErMindreEnnEllerLik<T extends Comparable<T>> extends BolskUttrykk {
        private final CompareableUttrykk<T> belopUttrykk;
        private final CompareableUttrykk<T> sammenliknMed;

        public ErMindreEnnEllerLik(CompareableUttrykk<T> belopUttrykk, CompareableUttrykk<T> sammenliknMed) {
            this.belopUttrykk = belopUttrykk;
            this.sammenliknMed = sammenliknMed;
        }

        @Override
        public Boolean eval(UttrykkContext ctx) {
            return ctx.eval(belopUttrykk).compareTo(ctx.eval(sammenliknMed)) <= 0;
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(belopUttrykk) + " <= " + ctx.beskriv(sammenliknMed);
        }
    }

    static class ErMindreEnn<T extends Comparable<T>> extends BolskUttrykk {
        private final CompareableUttrykk<T> belopUttrykk;
        private final CompareableUttrykk<T> sammenliknMed;

        public ErMindreEnn(CompareableUttrykk<T> belopUttrykk, CompareableUttrykk<T> sammenliknMed) {
            this.belopUttrykk = belopUttrykk;
            this.sammenliknMed = sammenliknMed;
        }

        @Override
        public Boolean eval(UttrykkContext ctx) {
            return ctx.eval(belopUttrykk).compareTo(ctx.eval(sammenliknMed)) < 0;
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(belopUttrykk) + " < " + ctx.beskriv(sammenliknMed);
        }
    }

    static class FraTilUttrykk<T extends Comparable<T>> extends BolskUttrykk {
        private final CompareableUttrykk<T> fraOgMed;
        private final CompareableUttrykk<T> fra;
        private CompareableUttrykk<T> til;
        private CompareableUttrykk<T> tilOgMed;
        private final CompareableUttrykk<T> uttrykk;

        private FraTilUttrykk(CompareableUttrykk<T> uttrykk, CompareableUttrykk<T> fraOgMed, CompareableUttrykk<T> fra, CompareableUttrykk<T> tilOgMed, CompareableUttrykk<T> til) {
            this.uttrykk = uttrykk;
            this.fraOgMed = fraOgMed;
            this.fra = fra;
            this.tilOgMed = tilOgMed;
            this.til = til;
        }

        public static <T extends Comparable<T>> FraTilUttrykk<T> fra(CompareableUttrykk<T> uttrykk, CompareableUttrykk<T> fra) {
            return new FraTilUttrykk<>(uttrykk, null, fra, null, null);
        }

        public static <T extends Comparable<T>> FraTilUttrykk<T> fraOgMed(CompareableUttrykk<T> uttrykk, CompareableUttrykk<T> fraOgMed) {
            return new FraTilUttrykk<>(uttrykk, fraOgMed, null, null, null);
        }

        public static <T extends Comparable<T>> BolskUttrykk til(CompareableUttrykk<T> uttrykk, CompareableUttrykk<T> til) {
            return new FraTilUttrykk<>(uttrykk, null, null, null, til);
        }

        public static <T extends Comparable<T>> BolskUttrykk tilOgMed(CompareableUttrykk<T> uttrykk, CompareableUttrykk<T> tilOgMed) {
            return new FraTilUttrykk<>(uttrykk, null, null, tilOgMed, null);
        }

        public BolskUttrykk ogTil(CompareableUttrykk<T> til) {
            this.til = til;
            return this;
        }

        public BolskUttrykk ogTilOgMed(CompareableUttrykk<T> tilOgMed) {
            this.tilOgMed = tilOgMed;
            return this;
        }

        public Boolean eval(UttrykkContext ctx) {
            boolean resultat = true;

            resultat &= fra == null || ctx.eval(uttrykk).compareTo(ctx.eval(fra)) > 0;
            resultat &= fraOgMed == null || ctx.eval(uttrykk).compareTo(ctx.eval(fraOgMed)) >= 0;
            resultat &= til == null || ctx.eval(uttrykk).compareTo(ctx.eval(til)) < 0;
            resultat &= tilOgMed == null || ctx.eval(uttrykk).compareTo(ctx.eval(tilOgMed)) <= 0;

            return resultat;
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            StringBuilder sb = new StringBuilder();
            if (fra != null) {
                sb.append(ctx.beskriv(fra)).append(" < ");
            } else if (fraOgMed != null) {
                sb.append(ctx.beskriv(fraOgMed)).append(" <= ");
            }

            sb.append(ctx.beskriv(uttrykk));

            if (til != null) {
                sb.append(" < ").append(ctx.beskriv(til));
            } else if (tilOgMed != null) {
                sb.append(" <= ").append(ctx.beskriv(tilOgMed));
            }


            return sb.toString();
        }
    }
}
