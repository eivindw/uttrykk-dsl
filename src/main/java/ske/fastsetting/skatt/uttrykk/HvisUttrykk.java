package ske.fastsetting.skatt.uttrykk;

import java.util.ArrayList;
import java.util.List;

import static ske.fastsetting.skatt.uttrykk.FeilUttrykk.feil;

public abstract class HvisUttrykk<T, B extends HvisUttrykk<T, B>> extends AbstractUttrykk<T, B> {
    private Uttrykk<T> ellersBruk = feil("Hvis-uttrykk mangler en verdi for ellersBruk");
    protected List<BrukUttrykk<T, B>> brukHvis = new ArrayList<>();

    @SuppressWarnings("unchecked")
    private B self = (B) this;

    @Override
    public T eval(UttrykkContext ctx) {
        for (BrukUttrykk<T, B> brukUttrykk : brukHvis) {
            if (ctx.eval(brukUttrykk.bolskUttrykk)) {
                return ctx.eval(brukUttrykk.brukDa);
            }
        }
        return ctx.eval(ellersBruk);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < brukHvis.size(); i++) {
            stringBuilder.append(i == 0 ? "hvis " : " ellers hvis ");
            stringBuilder.append(ctx.beskriv(brukHvis.get(i).bolskUttrykk));
            stringBuilder.append(" bruk da ");
            stringBuilder.append(ctx.beskriv(brukHvis.get(i).brukDa));
        }

        stringBuilder.append(" ellers bruk ");
        stringBuilder.append(ctx.beskriv(ellersBruk));
        return stringBuilder.toString();
    }

    public B ellersBruk(Uttrykk<T> uttrykk) {
        this.ellersBruk = uttrykk;
        return self;
    }

    public BrukUttrykk<T, B> ellersHvis(BolskUttrykk bolskUttrykk) {
        return new BrukUttrykk<>(bolskUttrykk, self);
    }

    public static class BrukUttrykk<T, B extends HvisUttrykk<T, B>>  {

        private final BolskUttrykk<?> bolskUttrykk;
        private final B hvisUttrykk;
        private Uttrykk<T> brukDa;

        public BrukUttrykk(BolskUttrykk bolskUttrykk, B hvisUttrykk) {

            this.bolskUttrykk = bolskUttrykk;
            this.hvisUttrykk = hvisUttrykk;
            this.hvisUttrykk.brukHvis.add(this);
        }

        public B brukDa(Uttrykk<T> brukDa) {
            this.brukDa = brukDa;
            return hvisUttrykk;
        }
    }
}
