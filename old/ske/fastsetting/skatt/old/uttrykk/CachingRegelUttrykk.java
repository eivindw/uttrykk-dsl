package ske.fastsetting.skatt.old.uttrykk;

public abstract class CachingRegelUttrykk<V, B> extends RegelUttrykk<B> {
    private V evaluertBelop = null;

    public final V evaluer() {
        if (evaluertBelop == null) {
            evaluertBelop = lagVerdi();
        }
        return evaluertBelop;

    }

    protected abstract V lagVerdi();
}
