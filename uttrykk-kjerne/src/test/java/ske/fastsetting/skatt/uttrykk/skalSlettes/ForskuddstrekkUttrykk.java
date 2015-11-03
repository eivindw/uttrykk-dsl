package ske.fastsetting.skatt.uttrykk.skalSlettes;

import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

import java.util.function.Function;

/**
 * Created by jorn ola birkeland on 27.10.15.
 */
public abstract class ForskuddstrekkUttrykk<T> extends AbstractUttrykk<T, ForskuddstrekkUttrykk<T>> {

    static class SkattegrunnlagXml {
    }

    public static class ForskuddXml {
        public String getForskuddsform() { return ""; }
    }

    private static class AjourholdXml extends ForskuddXml {
    }

    private static class ForskuddstrekkXml extends ForskuddXml {
    }


    private Uttrykk<T> uttrykk;
    private final Function<ForskuddXml, T> funksjon;
    private final T nullVerdi;

    public ForskuddstrekkUttrykk(Uttrykk<T> uttrykk, Function<ForskuddXml,T> funksjon, T nullVerdi) {

        this.uttrykk = uttrykk;
        this.funksjon = funksjon;
        this.nullVerdi = nullVerdi;
    }

    @Override
    public T eval(UttrykkContext ctx) {
        T resultat;

        if (ctx.harInput(SkattegrunnlagXml.class))
            resultat = ctx.eval(uttrykk);
        else if (ctx.harInput(AjourholdXml.class))
            resultat = eval(ctx.input(AjourholdXml.class));
        else if (ctx.harInput(ForskuddstrekkXml.class))
            resultat = eval(ctx.input(ForskuddstrekkXml.class));
        else
            resultat = nullVerdi;

        return resultat;
    }

    private T eval(ForskuddXml forskuddXml) {
        return funksjon.apply(forskuddXml);
    }
}
