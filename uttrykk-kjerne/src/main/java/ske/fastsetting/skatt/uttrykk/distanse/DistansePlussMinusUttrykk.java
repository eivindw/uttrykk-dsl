package ske.fastsetting.skatt.uttrykk.distanse;

import ske.fastsetting.skatt.uttrykk.PlussMinusUttrykk;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

/**
 * Created by jorn ola birkeland on 31.05.15.
 */
public class DistansePlussMinusUttrykk extends PlussMinusUttrykk<Distanse,DistanseUttrykk,DistansePlussMinusUttrykk> implements DistanseUttrykk {
    protected DistansePlussMinusUttrykk(Collection<DistanseUttrykk> plussUttrykk, Collection<DistanseUttrykk> minusUttrykk) {
        super(plussUttrykk, minusUttrykk);
    }

    @SafeVarargs
    public static DistansePlussMinusUttrykk sum(DistanseUttrykk... uttrykk) {
        return new DistansePlussMinusUttrykk(Stream.of(uttrykk).collect(Collectors.toList()),emptyList());
    }

    @Override
    protected Distanse nullVerdi() {
        return Distanse.km0();
    }

    @Override
    protected DistansePlussMinusUttrykk ny(Collection<DistanseUttrykk> plussUttrykk, Collection<DistanseUttrykk> minusUttrykk) {
        return new DistansePlussMinusUttrykk(plussUttrykk,minusUttrykk);
    }
}
