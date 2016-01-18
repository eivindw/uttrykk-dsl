package ske.fastsetting.skatt.uttrykk;

public class WrapperUttrykk<V, B extends WrapperUttrykk<V, B>> extends AbstractUttrykk<V,B> {

    private final Uttrykk<V> uttrykk;

    protected WrapperUttrykk(Uttrykk<V> uttrykk) {
        this.uttrykk = uttrykk;
        navn(uttrykk.navn());
    }

    @Override
    public V eval(UttrykkContext ctx) {
        return ctx.eval(uttrykk);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(uttrykk);
    }
}
