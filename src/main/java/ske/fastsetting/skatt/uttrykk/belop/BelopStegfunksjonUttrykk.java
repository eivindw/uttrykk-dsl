package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

/* TODO: UFULLSTENDIG */
public class BelopStegfunksjonUttrykk extends AbstractUttrykk<Belop,BelopStegfunksjonUttrykk> implements BelopUttrykk {

    private BelopUttrykk xVerdi;
    private TallUttrykk sats;
    private BelopUttrykk til;
    private TallUttrykk deretterMedSats;

    public BelopStegfunksjonUttrykk(BelopUttrykk x) {

        this.xVerdi = x;
    }

    public static BelopStegfunksjonUttrykk stegfunksjonAv(BelopUttrykk x) {
        return new BelopStegfunksjonUttrykk(x);
    }


    @Override
    public Belop eval(UttrykkContext ctx) {
        Belop grunnlag = xVerdi.eval(ctx);
        Belop tilVerdi = til.eval(ctx);
        Tall sats = this.sats.eval(ctx);
        Tall deretterSats = this.deretterMedSats.eval(ctx);

        Belop resultat;

        if(grunnlag.erMindreEnn(tilVerdi)) {
            resultat= grunnlag.multiplisertMed(sats.toBigDecimal());
        } else {
            resultat=tilVerdi.multiplisertMed(sats.toBigDecimal());
        }

        if(grunnlag.erStorreEnn(tilVerdi)) {
            resultat=resultat.pluss(grunnlag.minus(tilVerdi).multiplisertMed(deretterSats.toBigDecimal()));
        }

        return resultat;

    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return null;
    }

    public BelopStegfunksjonUttrykk medSats(TallUttrykk sats) {
        this.sats = sats;

        return this;
    }

    public BelopStegfunksjonUttrykk til(BelopUttrykk til) {
        this.til = til;
        return this;
    }

    public BelopStegfunksjonUttrykk deretterMedSats(TallUttrykk sats) {
        this.deretterMedSats = sats;
        return this;
    }
}
