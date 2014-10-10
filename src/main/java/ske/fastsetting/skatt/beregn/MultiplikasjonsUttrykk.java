package ske.fastsetting.skatt.beregn;

public class MultiplikasjonsUttrykk extends AbstractUttrykk<Integer> {

    private final Uttrykk<Integer> uttrykk1;
    private final Uttrykk<Double> uttrykk2;

    public MultiplikasjonsUttrykk(Uttrykk<Integer> uttrykk1, Uttrykk<Double> uttrykk2) {
        this.uttrykk1 = uttrykk1;
        this.uttrykk2 = uttrykk2;
    }

    @Override
    public Integer eval() {
        return (int)(uttrykk1.eval() * uttrykk2.eval());
    }

    @Override
    public String beskrivUttrykk(UttrykkContext ctx) {
        return "<" + uttrykk1.beskriv(ctx) + "> * <" + uttrykk2.beskriv(ctx) + ">";
    }

    public static MultiplikasjonsUttrykk mult(Uttrykk<Integer> uttrykk1, Uttrykk<Double> uttrykk2) {
        return new MultiplikasjonsUttrykk(uttrykk1, uttrykk2);
    }
}
