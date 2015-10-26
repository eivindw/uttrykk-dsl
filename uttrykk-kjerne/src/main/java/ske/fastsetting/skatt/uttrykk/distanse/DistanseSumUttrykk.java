package ske.fastsetting.skatt.uttrykk.distanse;

import ske.fastsetting.skatt.uttrykk.SumUttrykk;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @deprecated Bruk MultiBelopPlussMinusUttrykk i stedet
 */
@Deprecated
public class DistanseSumUttrykk extends SumUttrykk<Distanse, DistanseUttrykk, DistanseSumUttrykk> implements
  DistanseUttrykk {

    @SafeVarargs
    public static ske.fastsetting.skatt.uttrykk.distanse.DistanseSumUttrykk sum(DistanseUttrykk... uttrykk) {
        return new ske.fastsetting.skatt.uttrykk.distanse.DistanseSumUttrykk(Stream.of(uttrykk).collect(Collectors.toList()));
    }

    protected DistanseSumUttrykk(Collection<DistanseUttrykk> uttrykk) {
        super(uttrykk);
    }

    @Override
    protected Distanse nullVerdi() {
        return new Distanse(0d);
    }
}
