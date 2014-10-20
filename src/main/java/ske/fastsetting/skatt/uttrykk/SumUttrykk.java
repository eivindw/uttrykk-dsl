package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.KalkulerbarVerdi;

import java.util.Collection;
import java.util.stream.Collectors;

public abstract class SumUttrykk<T extends KalkulerbarVerdi<T>, U extends Uttrykk<T, C>, B extends SumUttrykk<T,U,B,C>, C>
    extends AbstractUttrykk<T, B, C>
{

    private final Collection<U> uttrykk;

    protected SumUttrykk(Collection<U> uttrykk) {
        this.uttrykk = uttrykk;
    }

    @Override
    public T eval(UttrykkContext<C> ctx) {
        return uttrykk.stream()
            .map(ctx::eval)
            .reduce((verdi1, verdi2) -> verdi1.pluss(verdi2))
            .orElse(nullVerdi());
    }

    protected abstract T nullVerdi();

    @Override
    public String beskriv(UttrykkContext<C> ctx) {
        return uttrykk.stream()
            .map(ctx::beskriv)
            .collect(Collectors.joining(",", "sum(", ")"));
    }
}
