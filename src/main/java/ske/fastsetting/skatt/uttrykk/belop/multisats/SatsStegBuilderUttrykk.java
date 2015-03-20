package ske.fastsetting.skatt.uttrykk.belop.multisats;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

/**
* Created by jorn ola birkeland on 20.03.15.
*/
public abstract class SatsStegBuilderUttrykk<K,S,B extends SatsStegBuilderUttrykk<K,S,B>> extends AbstractUttrykk<Belop,B> implements BelopUttrykk {
    protected final BelopMultisatsUttrykk<K,S> funksjon;

    protected SatsStegBuilderUttrykk(BelopMultisatsUttrykk<K, S> funksjon) {
        this.funksjon = funksjon;
    }

    @Override
    public Belop eval(UttrykkContext ctx) {
        return ctx.eval(funksjon);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(funksjon);
    }

    @Override
    public B navn(String navn) {
        funksjon.navn(navn);
        return self;
    }

    @Override
    public B tags(String... tags) {
        funksjon.tags(tags);
        return self;
    }

}
