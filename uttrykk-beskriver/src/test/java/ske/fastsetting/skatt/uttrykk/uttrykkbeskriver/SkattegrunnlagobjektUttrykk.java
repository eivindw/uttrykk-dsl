package ske.fastsetting.skatt.uttrykk.uttrykkbeskriver;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

public class SkattegrunnlagobjektUttrykk extends AbstractUttrykk<Belop, SkattegrunnlagobjektUttrykk>
  implements BelopUttrykk {

    private String skatteobjekttype;

    public SkattegrunnlagobjektUttrykk(String postnr) {
        this.skatteobjekttype = postnr;
    }

    public static SkattegrunnlagobjektUttrykk skattegrunnlagobjekt(String postnummer) {
        return new SkattegrunnlagobjektUttrykk(postnummer);
    }

    @Override
    public Belop eval(UttrykkContext ctx) {
        return ctx.input(Skattegrunnlag.class).getPostBelop(skatteobjekttype);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return "Post " + skatteobjekttype;
    }
}
