package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.SumUttrykk;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @deprecated Bruk TallPlussMinusUttrykk i stedet
 */
@Deprecated
public class TallSumUttrykk
  extends SumUttrykk<Tall, TallUttrykk, TallSumUttrykk>
  implements TallUttrykk {
    private TallSumUttrykk(Collection<TallUttrykk> uttrykk) {
        super(uttrykk);
    }

    @Override
    protected Tall nullVerdi() {
        return Tall.ZERO;
    }

    @SafeVarargs
    public static TallSumUttrykk sum(TallUttrykk... uttrykk) {
        return new TallSumUttrykk(Stream.of(uttrykk).collect(Collectors.toList()));
    }

    public static TallSumUttrykk sum(Collection<TallUttrykk> uttrykk) {
        return new TallSumUttrykk(uttrykk);
    }
}
