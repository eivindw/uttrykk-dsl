package ske.fastsetting.skatt.eksempel.beregning;

import java.util.Optional;

import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;
import ske.fastsetting.skatt.uttrykk.UttrykkResultat;

public class SkattyterKontekst extends UttrykkContextImpl<SkattyterKontekst> {

    private SkattyterKontekst ektefelle;

    protected SkattyterKontekst(Object... input) {
        super(input);
    }

    @Override
    protected SkattyterKontekst ny() {
        return new SkattyterKontekst();
    }

    public static SkattyterKontekst ny(Object... input) {
        return new SkattyterKontekst(input);
    }

    public SkattyterKontekst medEktefelle(Object... input) {
        ektefelle = ny(input);
        this.leggTilInput(ektefelle);
        ektefelle.leggTilInput(this);
        return this;
    }

    public <T> void overstyrVerdiEktefelle(Uttrykk<T> uttrykk, T overstyrtVerdi) {
        hentInput(getClass()).overstyrVerdi(uttrykk, overstyrtVerdi);
    }

    public static Optional<UttrykkContext> ektefelleKontekst(UttrykkContext ctx) {

        UttrykkContext ektefelleCtx = null;

        if (ctx.harInput(UttrykkContext.class)) {
            ektefelleCtx = ctx.hentInput(UttrykkContext.class);
        }

        return Optional.ofNullable(ektefelleCtx);
    }

    public <V> UttrykkResultat<V> beskrivResultat(Uttrykk<V> uttrykk) {
        return kalkuler(uttrykk, false, true);
    }

    public <V> UttrykkResultat<V> beregnResultat(Uttrykk<V> uttrykk) {
        return kalkuler(uttrykk, true, false);
    }

    public <V> UttrykkResultat<V> beregnOgBeskrivResultat(Uttrykk<V> uttrykk) {
        return kalkuler(uttrykk, true, true);
    }

    public <V> V verdiAv(Uttrykk<V> uttrykk) {
        return beregnResultat(uttrykk).verdi();
    }

}