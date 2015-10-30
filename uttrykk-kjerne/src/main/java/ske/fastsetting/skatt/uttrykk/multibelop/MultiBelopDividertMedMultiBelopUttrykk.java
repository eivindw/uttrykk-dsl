package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.MultiTall;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.multitall.MultiTallUttrykk;

public class MultiBelopDividertMedMultiBelopUttrykk<T>
  extends AbstractUttrykk<MultiTall<T>,MultiBelopDividertMedMultiBelopUttrykk<T>>
  implements MultiTallUttrykk<T> {
    private final MultiBelopUttrykk<T> divident;
    private final MultiBelopUttrykk<T> divisor;
    private Tall.TallUttrykkType type;

    public MultiBelopDividertMedMultiBelopUttrykk(MultiBelopUttrykk<T> divident, MultiBelopUttrykk<T> divisor, Tall.TallUttrykkType
      type) {
        this.divident = divident;
        this.divisor = divisor;
        this.type = type;
    }

    @Override
    public MultiTall<T> eval(UttrykkContext ctx) {
        return ctx.eval(divident).dividertMed(ctx.eval(divisor),type);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(divident) + " / " + ctx.beskriv(divisor);
    }
}
