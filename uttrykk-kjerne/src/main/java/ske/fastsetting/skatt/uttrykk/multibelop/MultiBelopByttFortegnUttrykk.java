package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class MultiBelopByttFortegnUttrykk<K> extends AbstractUttrykk<MultiBelop<K>, MultiBelopByttFortegnUttrykk<K>> implements MultiBelopUttrykk<K> {
    private MultiBelopUttrykk<K> belopUttrykk;

    public static <K> MultiBelopByttFortegnUttrykk<K> byttFortegn(MultiBelopUttrykk<K> uttrykk) {
        return new MultiBelopByttFortegnUttrykk<>(uttrykk);
    }

    public MultiBelopByttFortegnUttrykk(MultiBelopUttrykk<K> belopUttrykk) {
        this.belopUttrykk = belopUttrykk;
    }

    @Override
    public MultiBelop<K> eval(UttrykkContext ctx) {
        return ctx.eval(belopUttrykk).byttFortegn();
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return " - " + ctx.beskriv(belopUttrykk);
    }
}
