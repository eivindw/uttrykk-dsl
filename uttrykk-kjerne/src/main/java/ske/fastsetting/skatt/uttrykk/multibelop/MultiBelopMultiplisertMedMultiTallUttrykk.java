package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.multitall.MultiTallUttrykk;

public class MultiBelopMultiplisertMedMultiTallUttrykk<T>
  extends AbstractUttrykk<MultiBelop<T>,MultiBelopMultiplisertMedMultiTallUttrykk<T>>
  implements MultiBelopUttrykk<T> {

    protected final MultiBelopUttrykk<T> faktor1;
    protected final MultiTallUttrykk<T> faktor2;

    protected MultiBelopMultiplisertMedMultiTallUttrykk(MultiBelopUttrykk<T> faktor1, MultiTallUttrykk<T> faktor2) {
        this.faktor1 = faktor1;
        this.faktor2 = faktor2;
    }

    @Override
    public MultiBelop<T> eval(UttrykkContext ctx) {
        return ctx.eval(faktor1).multiplisertMed(ctx.eval(faktor2));
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(faktor1) + " * " + ctx.beskriv(faktor2);
    }
}
