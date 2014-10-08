package ske.fastsetting.skatt.old.uttrykk;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ske.fastsetting.skatt.old.uttrykk.FeilUttrykk.feil;

public abstract class HvisUttrykk<T, B extends HvisUttrykk<T, B>> extends RegelUttrykk<B> implements Uttrykk<T> {
    private Uttrykk<T> ellersBruk = feil("Hvis-uttrykk mangler en verdi for ellersBruk");
    protected List<BrukUttrykk<T, B>> brukHvis = new ArrayList<>();

    @SuppressWarnings("unchecked")
    private B self = (B) this;

    private T evaluertVerdi = null;

    public T evaluer() {
        if (evaluertVerdi == null) {
            evaluertVerdi = faktiskEvaluering();
        }
        return evaluertVerdi;
    }

    private T faktiskEvaluering() {
        for (BrukUttrykk<T, B> brukUttrykk : brukHvis) {
            if (brukUttrykk.bolskUttrykk.evaluer()) {
                return brukUttrykk.brukDa.evaluer();
            }
        }
        return ellersBruk.evaluer();
    }

    @Override
    public void beskrivEvaluering(UttrykkBeskriver beskriver) {
        UttrykkBeskriver underbeskriver = beskriver.overskrift(evaluer() + RegelUtil.formater(navn));

        underbeskriver.skriv("fordi");

        Optional<BolskUttrykk> bolskUttrykk = brukHvis.stream().map(bh -> bh.bolskUttrykk).filter(Uttrykk::evaluer).findFirst();
        if (bolskUttrykk.isPresent()) {
            bolskUttrykk.get().beskrivEvaluering(underbeskriver);
        } else {
            brukHvis.forEach(bh -> bh.bolskUttrykk.beskrivEvaluering(underbeskriver));
        }

        underbeskriver.skriv("så brukes");
        brukHvis.stream()
                .filter(bh -> bh.bolskUttrykk.evaluer())
                .map(bh -> bh.brukDa).findFirst().orElse(ellersBruk)
                .beskrivEvaluering(underbeskriver);
    }

    @Override
    public void beskrivGeneriskMedRegel(UttrykkBeskriver beskriver) {
        for (int i = 0; i < brukHvis.size(); i++) {
            beskriver.skriv(i == 0 ? "hvis" : "ellers hvis");
            brukHvis.get(i).bolskUttrykk.beskrivGenerisk(beskriver.rykkInn());
            beskriver.skriv("bruk da");
            brukHvis.get(i).brukDa.beskrivGenerisk(beskriver.rykkInn());
        }

        beskriver.skriv("ellers bruk");
        ellersBruk.beskrivGenerisk(beskriver.rykkInn());
    }

    public B ellersBruk(Uttrykk<T> uttrykk) {
        this.ellersBruk = uttrykk;
        return self;
    }

    public BrukUttrykk<T, B> ellersHvis(BolskUttrykk bolskUttrykk) {
        return new BrukUttrykk<>(bolskUttrykk, self);
    }

    public static class BrukUttrykk<T, B extends HvisUttrykk<T, B>>  {

        private final BolskUttrykk bolskUttrykk;
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
