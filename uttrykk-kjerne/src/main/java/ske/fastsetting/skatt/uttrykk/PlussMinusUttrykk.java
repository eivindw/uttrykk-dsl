package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.KalkulerbarVerdi;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Stream.concat;

public abstract class PlussMinusUttrykk <T extends KalkulerbarVerdi<T>, U extends Uttrykk<T>, B extends PlussMinusUttrykk<T, U, B>>
        extends AbstractUttrykk<T, B> {

    protected final Collection<U> plussUttrykk;
    protected final Collection<U> minusUttrykk;

    @SuppressWarnings("unchecked")
    private U self = (U) this; // Forventer at this implemeneterer interface't U

    protected PlussMinusUttrykk(Collection<U> plussUttrykk, Collection<U> minusUttrykk) {

        this.plussUttrykk = plussUttrykk;
        this.minusUttrykk = minusUttrykk;
    }

    public B pluss(U uttrykk) {
        if (this.navn() == null) {
            return ny(concat(plussUttrykk.stream(),Stream.of(uttrykk)).collect(Collectors.toList()),minusUttrykk);
        } else {
            return ny(Stream.of(self, uttrykk).collect(Collectors.toList()),emptyList());
        }
    }

    public B minus(U uttrykk) {
        if (this.navn() == null) {
            return ny(plussUttrykk, concat(minusUttrykk.stream(),Stream.of(uttrykk)).collect(Collectors.toList()));
        } else {
            return ny(Stream.of(self).collect(Collectors.toList()),Stream.of(uttrykk).collect(Collectors.toList()));
        }
    }

    @Override
    public T eval(UttrykkContext ctx) {
        T plussVerdi = plussUttrykk.stream()
                .map(ctx::eval)
                .reduce(KalkulerbarVerdi::pluss)
                .orElse(nullVerdi());

        T minusVerdi = minusUttrykk.stream()
                .map(ctx::eval)
                .reduce(KalkulerbarVerdi::pluss)
                .orElse(nullVerdi());

        return plussVerdi.minus(minusVerdi);
    }


    @Override
    public String beskriv(UttrykkContext ctx) {
        String plussString = plussUttrykk.stream()
                .map(ctx::beskriv)
                .collect(Collectors.joining(" + "));

        String minusString = minusUttrykk.stream()
                .map(ctx::beskriv)
                .collect(Collectors.joining(" - "));

        return "".equals(minusString) ? plussString : plussString + " - " + minusString;
    }

    protected abstract T nullVerdi();

    protected abstract B ny(Collection<U> plussUttrykk, Collection<U> minusUttrykk);

}
