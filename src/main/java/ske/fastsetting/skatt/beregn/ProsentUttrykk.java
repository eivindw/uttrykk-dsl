package ske.fastsetting.skatt.beregn;

public class ProsentUttrykk extends AbstractUttrykk<Double> {

    private final int prosent;

    public ProsentUttrykk(int prosent) {
        this.prosent = prosent;
    }

    @Override
    public Double eval() {
        return (double) prosent / 100;
    }

    @Override
    public String beskrivUttrykk(UttrykkContext ctx) {
        return prosent + "%";
    }

    public static ProsentUttrykk prosent(int prosent) {
        return new ProsentUttrykk(prosent);
    }
}
