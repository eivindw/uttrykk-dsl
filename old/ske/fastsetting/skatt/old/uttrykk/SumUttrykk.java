package ske.fastsetting.skatt.old.uttrykk;

import ske.fastsetting.skatt.old.domene.KalkulerbarVerdi;

import java.util.Collection;

public abstract class SumUttrykk<T extends KalkulerbarVerdi<T>, U extends Uttrykk<T>> extends RegelUttrykk<U> implements Uttrykk<T> {

    private final Collection<U> uttrykk;

    private T evaluertVerdi = null;

    protected SumUttrykk(Collection<U> uttrykk) {
        this.uttrykk = uttrykk;
    }

    @Override
    public final T evaluer() {
        if (evaluertVerdi == null) {
            evaluertVerdi = uttrykk.stream()
                .map(Uttrykk::evaluer)
                .reduce(nullVerdi(), this::pluss);
        }
        return evaluertVerdi;
    }

    @Override
    public final void beskrivEvaluering(UttrykkBeskriver beskriver) {
        final String tekst = evaluer() + RegelUtil.formater(navn);
        UttrykkBeskriver nyBeskriver = beskriver.overskrift(tekst);

        nyBeskriver.skriv("summen av:");

        uttrykk.forEach(u -> u.beskrivEvaluering(nyBeskriver));
    }

    @Override
    public void beskrivGeneriskMedRegel(UttrykkBeskriver beskriver) {
        beskriver.skriv("summen av:");
        uttrykk.forEach(u -> u.beskrivGenerisk(beskriver));
    }

    protected abstract T nullVerdi();

    private T pluss(T ledd1, T ledd2) {
        return ledd1.pluss(ledd2);
    }
}
