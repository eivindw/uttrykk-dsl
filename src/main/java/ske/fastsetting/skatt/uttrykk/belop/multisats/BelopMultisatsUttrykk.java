package ske.fastsetting.skatt.uttrykk.belop.multisats;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ske.fastsetting.skatt.uttrykk.belop.BelopSumUttrykk.sum;

public abstract class BelopMultisatsUttrykk<K,S> extends AbstractUttrykk<Belop,BelopMultisatsUttrykk<K,S>> implements BelopUttrykk {

    private Uttrykk<K> grunnlag;
    private List<BelopUttrykk> alleSatsSteg = new ArrayList<>();
    private SatsStegUttrykk<K,S> gjeldendeSatsSteg;

    public BelopMultisatsUttrykk(Uttrykk<K> grunnlag) {
        this.grunnlag = grunnlag;
        nyttSteg(null, null);
    }

    public OppTilGrenseStegBuilderUttrykk<K,S> medSats(Uttrykk<S> sats) {
        gjeldendeSatsSteg.medSats(sats);
        return new OppTilGrenseStegBuilderUttrykk<>(this);
    }

    @Override
    public Belop eval(UttrykkContext ctx) {
        return ctx.eval(sum(alleSatsSteg));
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return alleSatsSteg.stream().map(ctx::beskriv).collect(Collectors.joining(",", "multisats(", ")"));
    }

    public final void nyttSteg(Uttrykk<K> nedreGrense, Uttrykk<S> sats) {
        gjeldendeSatsSteg = lagSteg()
                .medGrunnlag(grunnlag)
                .medNedreGrense(nedreGrense)
                .medSats(sats);

        alleSatsSteg.add(gjeldendeSatsSteg);
    }

    public void settGjeldendeOevreGrense(Uttrykk<K> oevregrense) {
        gjeldendeSatsSteg.medOevreGrense(oevregrense);
    }

    protected abstract SatsStegUttrykk<K, S> lagSteg();

}
