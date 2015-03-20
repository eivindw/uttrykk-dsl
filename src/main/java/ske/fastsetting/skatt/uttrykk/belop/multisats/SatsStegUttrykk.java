package ske.fastsetting.skatt.uttrykk.belop.multisats;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

/**
* Created by jorn ola birkeland on 20.03.15.
*/
public abstract class SatsStegUttrykk<K,S> extends AbstractUttrykk<Belop,SatsStegUttrykk<K,S>> implements BelopUttrykk {
    protected Uttrykk<S> sats;
    protected Uttrykk<K> oevreGrense;
    protected Uttrykk<K> nedreGrense;
    protected Uttrykk<K> grunnlag;

    protected SatsStegUttrykk() {
    }

    @Override
    public final String beskriv(UttrykkContext ctx) {
        String stegResultat;

        if (oevreGrense != null && nedreGrense!=null) {
            stegResultat = "satsFraTil(" + ctx.beskriv(grunnlag)+"," + ctx.beskriv(sats) + "," + ctx.beskriv(nedreGrense) + "," + ctx.beskriv(oevreGrense) + ")";
        } else if(nedreGrense!=null) {
            stegResultat = "satsFra(" + ctx.beskriv(grunnlag)+"," + ctx.beskriv(sats) + "," + ctx.beskriv(nedreGrense) + ")";
        } else if(oevreGrense!=null) {
            stegResultat = "satsTil(" + ctx.beskriv(grunnlag)+"," + ctx.beskriv(sats) + "," + ctx.beskriv(oevreGrense) + ")";
        } else {
            stegResultat = "sats(" + ctx.beskriv(grunnlag)+"," + ctx.beskriv(sats) +")";
        }
        return stegResultat;
    }

    public SatsStegUttrykk<K,S> medOevreGrense(Uttrykk<K> oevregrense) {
        if(oevregrense!=null) { this.oevreGrense = oevregrense; }
        return this;
    }

    public SatsStegUttrykk<K,S> medNedreGrense(Uttrykk<K> nedregrense) {
        if(nedregrense!=null) { this.nedreGrense = nedregrense; }
        return this;
    }

    public SatsStegUttrykk<K,S> medSats(Uttrykk<S> sats) {
        if(sats!=null) { this.sats = sats; }
        return this;
    }

    public SatsStegUttrykk<K,S> medGrunnlag(Uttrykk<K> grunnlag) {
        if(grunnlag==null) { throw new IllegalArgumentException("grunnlag kan ikke være null");}

        this.grunnlag = grunnlag;
        return this;
    }

}
