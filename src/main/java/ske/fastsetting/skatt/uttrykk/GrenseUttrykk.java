package ske.fastsetting.skatt.uttrykk;

// G - type på grunnlag
// L - type på øvre og nedre grense ("limit")
// B - baseklasse - brukes for self-referanser
public abstract class GrenseUttrykk<G, L, B extends GrenseUttrykk<G, L, B>> extends AbstractUttrykk<G, B> {

    private final Uttrykk<G> grunnlag;
    private Uttrykk<L> minimum;
    private Uttrykk<L> maksimum;

    protected GrenseUttrykk(Uttrykk<G> grunnlag) {
        this.grunnlag = grunnlag;
    }

    public B nedad(Uttrykk<L> minimum) {
        this.minimum = minimum;
        return self;
    }

    public B oppad(Uttrykk<L> maksimum) {
        this.maksimum = maksimum;
        return self;
    }

    @Override
    public final G eval(UttrykkContext ctx) {
        G verdi = ctx.eval(grunnlag);

        if (null != minimum) {
            L min = ctx.eval(minimum);
            verdi = begrensNedad(verdi, min);
        }
        if (null != maksimum) {
            L max = ctx.eval(maksimum);
            verdi = begrensOppad(verdi, max);
        }
        return verdi;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        StringBuilder stringBuilder = new StringBuilder(ctx.beskriv(grunnlag));
        if (null == minimum && null == maksimum) {
            stringBuilder.append(" Advarsel: Uttrykket mangler øvre og/eller nedre grense ");
        }
        if (null != minimum) {
            stringBuilder.append(" begrenset nedad til ");
            stringBuilder.append(ctx.beskriv(minimum));
        }
        if (null != maksimum) {
            stringBuilder.append(" begrenset oppad til ");
            stringBuilder.append(ctx.beskriv(maksimum));
        }
        return stringBuilder.toString();
    }

    protected abstract G begrensNedad(G verdi, L min);

    protected abstract G begrensOppad(G verdi, L max);

}
