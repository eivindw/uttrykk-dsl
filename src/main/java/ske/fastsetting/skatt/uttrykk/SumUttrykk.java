package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.KalkulerbarVerdi;

import java.util.Collection;
import java.util.stream.Collectors;

public abstract class SumUttrykk<T extends KalkulerbarVerdi<T>, U extends Uttrykk<T>> extends AbstractUttrykk<T, U> {

    private final Collection<U> uttrykk;

    private T evaluertVerdi = null;

    protected SumUttrykk(Collection<U> uttrykk) {
        this.uttrykk = uttrykk;
    }

    @Override
    public T eval(UttrykkContext ctx) {
        if (evaluertVerdi == null) {
            evaluertVerdi = uttrykk.stream()
                .map(ctx::eval)
                .reduce((verdi1, verdi2) -> verdi1.pluss(verdi2))
                .get();
        }
        return evaluertVerdi;
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return uttrykk.stream()
            .map(ctx::beskriv)
            .collect(Collectors.joining(",", "sum(", ")"));
    }
}
