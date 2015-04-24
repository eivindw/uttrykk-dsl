package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public class BelopDividertMedBelopUttrykk extends AbstractUttrykk<Tall, BelopDividertMedBelopUttrykk> implements
  TallUttrykk {
    private final BelopUttrykk divident;
    private final BelopUttrykk divisor;
    private Tall.TallUttrykkType type;

    public BelopDividertMedBelopUttrykk(BelopUttrykk divident, BelopUttrykk divisor, Tall.TallUttrykkType type) {
        this.divident = divident;
        this.divisor = divisor;
        this.type = type;
    }

    @Override
    public Tall eval(UttrykkContext ctx) {
        return Tall.nytt(ctx.eval(divident).dividertMed(ctx.eval(divisor)),type);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(divident) + " / " + ctx.beskriv(divisor);
    }
}
