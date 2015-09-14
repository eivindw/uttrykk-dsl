package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.PlussMinusUttrykk;

import java.util.Collection;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * Created by jorn ola birkeland on 31.05.15.
 */
public class TallPlussMinusUttrykk extends PlussMinusUttrykk<Tall,TallUttrykk,TallPlussMinusUttrykk> implements TallUttrykk  {

    @SafeVarargs
    public static TallPlussMinusUttrykk sum(TallUttrykk... uttrykk) {
        return new TallPlussMinusUttrykk(Stream.of(uttrykk).collect(toList()),emptyList());
    }

    public static TallPlussMinusUttrykk sum(Collection<TallUttrykk> uttrykk) {
        return new TallPlussMinusUttrykk(uttrykk,emptyList());
    }

    public static TallPlussMinusUttrykk diff(TallUttrykk ledd1, TallUttrykk ledd2) {
        return new TallPlussMinusUttrykk(Stream.of(ledd1).collect(toList()), Stream.of(ledd2).collect(toList()));
    }

    protected TallPlussMinusUttrykk(Collection<TallUttrykk> plussUttrykk, Collection<TallUttrykk> minusUttrykk) {
        super(plussUttrykk, minusUttrykk);
    }

    @Override
    protected Tall nullVerdi() {
        return Tall.ZERO;
    }

    @Override
    protected TallPlussMinusUttrykk ny(Collection<TallUttrykk> plussUttrykk, Collection<TallUttrykk> minusUttrykk) {
        return new TallPlussMinusUttrykk(plussUttrykk,minusUttrykk);
    }
}
