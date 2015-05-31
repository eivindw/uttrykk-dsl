package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.PlussMinusUttrykk;

import java.util.Collection;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * Created by jorn ola birkeland on 31.05.15.
 */
public class BelopPlussMinusUttrykk extends PlussMinusUttrykk<Belop,BelopUttrykk,BelopPlussMinusUttrykk> implements BelopUttrykk {

    public static BelopPlussMinusUttrykk sum(BelopUttrykk ledd1, BelopUttrykk ledd2) {
        return new BelopPlussMinusUttrykk(Stream.of(ledd1,ledd2).collect(toList()), emptyList());
    }

    public static BelopPlussMinusUttrykk diff(BelopUttrykk ledd1, BelopUttrykk ledd2) {
        return new BelopPlussMinusUttrykk(Stream.of(ledd1).collect(toList()), Stream.of(ledd2).collect(toList()));
    }

    protected BelopPlussMinusUttrykk(Collection<BelopUttrykk> plussUttrykk, Collection<BelopUttrykk> minusUttrykk) {
        super(plussUttrykk, minusUttrykk);
    }

    @Override
    protected Belop nullVerdi() {
        return Belop.NULL;
    }

    @Override
    protected BelopPlussMinusUttrykk ny(Collection<BelopUttrykk> plussUttrykk, Collection<BelopUttrykk> minusUttrykk) {
        return new BelopPlussMinusUttrykk(plussUttrykk,minusUttrykk);
    }
}
