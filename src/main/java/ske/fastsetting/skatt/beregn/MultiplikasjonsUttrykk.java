package ske.fastsetting.skatt.beregn;

public class MultiplikasjonsUttrykk extends AbstractUttrykk<Integer> {

    private final Uttrykk<Integer> uttrykk1;
    private final Uttrykk<Double> uttrykk2;

    public MultiplikasjonsUttrykk(Uttrykk<Integer> uttrykk1, Uttrykk<Double> uttrykk2) {
        this.uttrykk1 = uttrykk1;
        this.uttrykk2 = uttrykk2;
    }

    @Override
    public Integer eval(UttrykkContext ctx) {
        return (int)((int)ctx.eval(uttrykk1) * (double)ctx.eval(uttrykk2));
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(uttrykk1) + " * " + ctx.beskriv(uttrykk2);
    }

    public static MultiplikasjonsUttrykk mult(Uttrykk<Integer> uttrykk1, Uttrykk<Double> uttrykk2) {
        return new MultiplikasjonsUttrykk(uttrykk1, uttrykk2);
    }
}
