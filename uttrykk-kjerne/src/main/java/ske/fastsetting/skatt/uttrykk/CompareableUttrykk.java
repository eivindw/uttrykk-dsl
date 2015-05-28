package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.uttrykk.bolsk.BolskUttrykk;

public interface CompareableUttrykk<T extends Comparable<T>> extends Uttrykk<T> {

    default BolskUttrykk erStorreEnn(CompareableUttrykk<T> uttrykk) {
        return new ErStorreEnn<>(this, uttrykk);
    }

    default BolskUttrykk erStorreEllerLik(CompareableUttrykk<T> uttrykk) {
        return new ErStorreEllerLik<>(this, uttrykk);
    }

    default BolskUttrykk er(CompareableUttrykk<T> uttrykk) {
        return new ErLik<>(this, uttrykk);
    }

    default BolskUttrykk ikkeEr(CompareableUttrykk<T> uttrykk) {
        return new IkkeErLik<>(this, uttrykk);
    }

    default FraTilUttrykk<T> erFra(CompareableUttrykk<T> uttrykk) {
        return FraTilUttrykk.fra(this, uttrykk);
    }

    default FraTilUttrykk<T> erFraOgMed(CompareableUttrykk<T> uttrykk) {
        return FraTilUttrykk.fraOgMed(this, uttrykk);
    }

    default BolskUttrykk erInntil(CompareableUttrykk<T> uttrykk) {
        return FraTilUttrykk.til(this, uttrykk);
    }

    default BolskUttrykk erTilOgMed(CompareableUttrykk<T> uttrykk) {
        return FraTilUttrykk.tilOgMed(this, uttrykk);
    }

    default BolskUttrykk erMellom(CompareableUttrykk<T> fra, CompareableUttrykk<T> til) {
        return new ErMellom<>(this, fra, til);
    }

    default BolskUttrykk erMindreEnnEllerLik(CompareableUttrykk<T> uttrykk) {
        return new ErMindreEnnEllerLik<>(this, uttrykk);
    }

    default BolskUttrykk erMindreEnn(CompareableUttrykk<T> uttrykk) {
        return new ErMindreEnn<>(this, uttrykk);
    }

    static class ErStorreEnn<T extends Comparable<T>> extends BolskUttrykk {
        private final CompareableUttrykk<T> uttrykk;
        private final CompareableUttrykk<T> sammenliknMed;

        public ErStorreEnn(CompareableUttrykk<T> uttrykk, CompareableUttrykk<T> sammenliknMed) {
            this.uttrykk = uttrykk;
            this.sammenliknMed = sammenliknMed;
        }

        @Override
        public Boolean eval(UttrykkContext ctx) {
            return ctx.eval(uttrykk).compareTo(ctx.eval(sammenliknMed)) > 0;
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(uttrykk) + " > " + ctx.beskriv(sammenliknMed);
        }
    }

    static class ErStorreEllerLik<T extends Comparable<T>> extends BolskUttrykk {
        private final CompareableUttrykk<T> uttrykk;
        private final CompareableUttrykk<T> sammenliknMed;

        public ErStorreEllerLik(CompareableUttrykk<T> uttrykk, CompareableUttrykk<T> sammenliknMed) {
            this.uttrykk = uttrykk;
            this.sammenliknMed = sammenliknMed;
        }

        @Override
        public Boolean eval(UttrykkContext ctx) {
            return ctx.eval(uttrykk).compareTo(ctx.eval(sammenliknMed)) >= 0;
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(uttrykk) + " >= " + ctx.beskriv(sammenliknMed);
        }
    }


    static class ErLik<T extends Comparable<T>> extends BolskUttrykk {
        private final CompareableUttrykk<T> uttrykk;
        private final CompareableUttrykk<T> sammenliknMed;

        public ErLik(CompareableUttrykk<T> belopUttrykk1, CompareableUttrykk<T> sammenliknMed) {
            this.uttrykk = belopUttrykk1;
            this.sammenliknMed = sammenliknMed;
        }

        @Override
        public Boolean eval(UttrykkContext ctx) {
            return ctx.eval(uttrykk).compareTo(ctx.eval(sammenliknMed)) == 0;
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(uttrykk) + " = " + ctx.beskriv(sammenliknMed);
        }
    }

    static class IkkeErLik<T extends Comparable<T>> extends BolskUttrykk {
        private final CompareableUttrykk<T> uttrykk;
        private final CompareableUttrykk<T> sammenliknMed;

        public IkkeErLik(CompareableUttrykk<T> belopUttrykk1, CompareableUttrykk<T> sammenliknMed) {
            this.uttrykk = belopUttrykk1;
            this.sammenliknMed = sammenliknMed;
        }

        @Override
        public Boolean eval(UttrykkContext ctx) {
            return ctx.eval(uttrykk).compareTo(ctx.eval(sammenliknMed)) != 0;
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(uttrykk) + " != " + ctx.beskriv(sammenliknMed);
        }
    }

    static class ErMellom<T extends Comparable<T>> extends BolskUttrykk {
        private final CompareableUttrykk<T> uttrykk;
        private final CompareableUttrykk<T> fraUttrykk;
        private final CompareableUttrykk<T> tilUttrykk;

        public ErMellom(CompareableUttrykk<T> uttrykk, CompareableUttrykk<T> fra, CompareableUttrykk<T> til) {
            this.uttrykk = uttrykk;
            fraUttrykk = fra;
            tilUttrykk = til;
        }

        @Override
        public Boolean eval(UttrykkContext ctx) {
            T belop = ctx.eval(uttrykk);
            T fra = ctx.eval(fraUttrykk);
            T til = ctx.eval(tilUttrykk);

            return belop.compareTo(fra) > 0 && belop.compareTo(til) < 0;
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return String.format("%2$s < %1$s < %3$s",
              ctx.beskriv(uttrykk), ctx.beskriv(fraUttrykk), ctx.beskriv(tilUttrykk));
        }
    }

    static class ErMindreEnnEllerLik<T extends Comparable<T>> extends BolskUttrykk {
        private final CompareableUttrykk<T> uttrykk;
        private final CompareableUttrykk<T> sammenliknMed;

        public ErMindreEnnEllerLik(CompareableUttrykk<T> uttrykk, CompareableUttrykk<T> sammenliknMed) {
            this.uttrykk = uttrykk;
            this.sammenliknMed = sammenliknMed;
        }

        @Override
        public Boolean eval(UttrykkContext ctx) {
            return ctx.eval(uttrykk).compareTo(ctx.eval(sammenliknMed)) <= 0;
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(uttrykk) + " <= " + ctx.beskriv(sammenliknMed);
        }
    }

    static class ErMindreEnn<T extends Comparable<T>> extends BolskUttrykk {
        private final CompareableUttrykk<T> uttrykk;
        private final CompareableUttrykk<T> sammenliknMed;

        public ErMindreEnn(CompareableUttrykk<T> uttrykk, CompareableUttrykk<T> sammenliknMed) {
            this.uttrykk = uttrykk;
            this.sammenliknMed = sammenliknMed;
        }

        @Override
        public Boolean eval(UttrykkContext ctx) {
            return ctx.eval(uttrykk).compareTo(ctx.eval(sammenliknMed)) < 0;
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return ctx.beskriv(uttrykk) + " < " + ctx.beskriv(sammenliknMed);
        }
    }

    static class FraTilUttrykk<T extends Comparable<T>> extends BolskUttrykk {
        private final CompareableUttrykk<T> fraOgMed;
        private final CompareableUttrykk<T> fra;
        private CompareableUttrykk<T> til;
        private CompareableUttrykk<T> tilOgMed;
        private final CompareableUttrykk<T> uttrykk;

        private FraTilUttrykk(CompareableUttrykk<T> uttrykk, CompareableUttrykk<T> fraOgMed, CompareableUttrykk<T>
          fra, CompareableUttrykk<T> tilOgMed, CompareableUttrykk<T> til) {
            this.uttrykk = uttrykk;
            this.fraOgMed = fraOgMed;
            this.fra = fra;
            this.tilOgMed = tilOgMed;
            this.til = til;
        }

        public static <T extends Comparable<T>> FraTilUttrykk<T> fra(CompareableUttrykk<T> uttrykk,
          CompareableUttrykk<T> fra) {
            return new FraTilUttrykk<>(uttrykk, null, fra, null, null);
        }

        public static <T extends Comparable<T>> FraTilUttrykk<T> fraOgMed(CompareableUttrykk<T> uttrykk,
          CompareableUttrykk<T> fraOgMed) {
            return new FraTilUttrykk<>(uttrykk, fraOgMed, null, null, null);
        }

        public static <T extends Comparable<T>> BolskUttrykk til(CompareableUttrykk<T> uttrykk, CompareableUttrykk<T>
          til) {
            return new FraTilUttrykk<>(uttrykk, null, null, null, til);
        }

        public static <T extends Comparable<T>> BolskUttrykk tilOgMed(CompareableUttrykk<T> uttrykk,
          CompareableUttrykk<T> tilOgMed) {
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
