package ske.fastsetting.skatt.beregn;

public class ProsentUttrykk extends AbstractUttrykk<Double> {

    private final int prosent;

    public ProsentUttrykk(int prosent) {
        this.prosent = prosent;
    }

    @Override
    public Double eval(UttrykkContext ctx) {
        return (double) prosent / 100;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return prosent + "%";
    }

    public static ProsentUttrykk prosent(int prosent) {
        return new ProsentUttrykk(prosent);
    }
}
