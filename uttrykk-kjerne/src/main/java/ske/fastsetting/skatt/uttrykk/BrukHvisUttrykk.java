package ske.fastsetting.skatt.uttrykk;

import java.util.ArrayList;
import java.util.List;

import ske.fastsetting.skatt.uttrykk.bolsk.BolskUttrykk;

/**
 * Created by jorn ola birkeland on 16.10.15.
 */
public abstract class BrukHvisUttrykk<T, B extends BrukHvisUttrykk<T,B>> extends AbstractUttrykk<T, B> {

    private final List<Uttrykk<T>> brukUttrykk = new ArrayList<>();
    private final List<BolskUttrykk> bolskUttrykk = new ArrayList<>();
    private Uttrykk<T> ellersBruk;

    public BrukHvisUttrykk(Uttrykk<T> uttrykk) {
        ellersBruk = uttrykk;
    }

    public B hvis(BolskUttrykk bolskUttrykk) {
        this.bolskUttrykk.add(bolskUttrykk);
        this.brukUttrykk.add(ellersBruk);
        this.ellersBruk = null;

        return self;
    }

    public B ellersBruk(Uttrykk<T> uttrykk) {
        if (ellersBruk!=null) {
            throw new IllegalStateException("'ellersBruk' brukt uten foregående 'hvis'");
        }

        ellersBruk = uttrykk;
        return self;
    }

    @Override
    public T eval(UttrykkContext ctx) {

        if (ellersBruk==null) {
            throw new IllegalStateException("Siste operasjon kan ikke være 'hvis'");
        }

        for (int i = 0; i < bolskUttrykk.size(); i++) {
            if (ctx.eval(bolskUttrykk.get(i))) {
                return ctx.eval(brukUttrykk.get(i));
            }
        }

        return ctx.eval(ellersBruk);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        if (ellersBruk==null) {
            throw new IllegalStateException("Siste operasjon kan ikke være 'hvis'");
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bolskUttrykk.size(); i++) {
            stringBuilder.append(i == 0 ? "bruk " : " ellers bruk ");
            stringBuilder.append(ctx.beskriv(brukUttrykk.get(i)));
            stringBuilder.append(" hvis ");
            stringBuilder.append(ctx.beskriv(bolskUttrykk.get(i)));
        }

        stringBuilder.append(" ellers bruk ");
        stringBuilder.append(ctx.beskriv(ellersBruk));
        return stringBuilder.toString();
    }

}