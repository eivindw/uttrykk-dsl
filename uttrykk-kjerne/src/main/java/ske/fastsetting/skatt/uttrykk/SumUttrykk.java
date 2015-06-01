package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.KalkulerbarVerdi;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 *
 * @deprecated Bruk PlussMinusUttrykk i stedet
 */
@Deprecated
public abstract class SumUttrykk<T extends KalkulerbarVerdi<T>, U extends Uttrykk<T>, B extends SumUttrykk<T, U, B>>
  extends AbstractUttrykk<T, B> {

    protected final Collection<U> uttrykk;

    protected SumUttrykk(Collection<U> uttrykk) {
        this.uttrykk = uttrykk;
    }

    @Override
    public T eval(UttrykkContext ctx) {
        return uttrykk.stream()
          .map(ctx::eval)
          .reduce(KalkulerbarVerdi::pluss)
          .orElse(nullVerdi());
    }

    protected abstract T nullVerdi();

    @Override
    public String beskriv(UttrykkContext ctx) {
        return uttrykk.stream()
          .map(ctx::beskriv)
          .collect(Collectors.joining(" + "));
    }


}
