package ske.fastsetting.skatt.uttrykk;

public abstract class KonstantUttrykk<V> extends AbstractUttrykk<V,KonstantUttrykk<V>> {


    private V verdi;

    protected KonstantUttrykk(V verdi) {
        this.verdi = verdi;
    }

    @Override
    public final V eval(UttrykkContext ctx) {
        return verdi;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return verdi.toString();
    }
}