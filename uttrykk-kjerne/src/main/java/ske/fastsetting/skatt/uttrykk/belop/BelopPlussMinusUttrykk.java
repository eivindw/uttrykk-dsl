package ske.fastsetting.skatt.uttrykk.belop;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.stream.Stream;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.PlussMinusUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;

public class BelopPlussMinusUttrykk extends PlussMinusUttrykk<Belop,Uttrykk<Belop>,BelopPlussMinusUttrykk> implements BelopUttrykk {

    public static BelopPlussMinusUttrykk sum(BelopUttrykk ledd1, BelopUttrykk ledd2) {
        return new BelopPlussMinusUttrykk(Stream.of(ledd1,ledd2).collect(toList()), emptyList());
    }

    public static BelopPlussMinusUttrykk diff(Uttrykk<Belop> ledd1, Uttrykk<Belop> ledd2) {
        return new BelopPlussMinusUttrykk(Stream.of(ledd1).collect(toList()), Stream.of(ledd2).collect(toList()));
    }

    protected BelopPlussMinusUttrykk(Collection<Uttrykk<Belop>> plussUttrykk, Collection<Uttrykk<Belop>> minusUttrykk) {
        super(plussUttrykk, minusUttrykk);
    }

    @Override
    protected Belop nullVerdi() {
        return Belop.NULL;
    }

    @Override
    protected BelopPlussMinusUttrykk ny(Collection<Uttrykk<Belop>> plussUttrykk, Collection<Uttrykk<Belop>> minusUttrykk) {
        return new BelopPlussMinusUttrykk(plussUttrykk,minusUttrykk);
    }
}
