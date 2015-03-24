package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public class BelopDividertMedBelopUttrykk extends AbstractUttrykk<Tall, BelopDividertMedBelopUttrykk> implements
  TallUttrykk {
    private final BelopUttrykk divident;
    private final BelopUttrykk divisor;

    public BelopDividertMedBelopUttrykk(BelopUttrykk divident, BelopUttrykk divisor) {
        this.divident = divident;
        this.divisor = divisor;
    }

    @Override
    public Tall eval(UttrykkContext ctx) {
        return Tall.ukjent(ctx.eval(divident).dividertMed(ctx.eval(divisor)));
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(divident) + " / " + ctx.beskriv(divisor);
    }
}
