package ske.fastsetting.skatt.uttrykk.multibelop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.MultiBelop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

import java.util.Map;

public class TilEnkeltBelopUttrykk extends AbstractUttrykk<Belop, TilEnkeltBelopUttrykk> implements
  BelopUttrykk {
    private MultiBelopUttrykk<?> multiBelopUttrykk;

    public static TilEnkeltBelopUttrykk steduavhengig(MultiBelopUttrykk<?> multiBelopUttrykk) {
        return new TilEnkeltBelopUttrykk(multiBelopUttrykk);
    }

    public TilEnkeltBelopUttrykk(MultiBelopUttrykk<?> multiBelopUttrykk) {
        this.multiBelopUttrykk = multiBelopUttrykk;
    }

    public void test(Map<?, Belop> map) {
        map.get("asdas");
    }

    @Override
    public Belop eval(UttrykkContext ctx) {

        final MultiBelop<?> multiBelop = ctx.eval(multiBelopUttrykk);

        return multiBelop.steder().stream()
          .map(multiBelop::get)
          .reduce(Belop.NULL, Belop::pluss);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(multiBelopUttrykk);
    }
}
