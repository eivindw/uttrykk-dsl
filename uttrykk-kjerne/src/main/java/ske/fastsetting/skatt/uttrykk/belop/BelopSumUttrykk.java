package ske.fastsetting.skatt.uttrykk.belop;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.SumUttrykk;

public class BelopSumUttrykk
  extends SumUttrykk<Belop, BelopUttrykk, BelopSumUttrykk>
  implements BelopUttrykk {

    private BelopSumUttrykk(Collection<BelopUttrykk> uttrykk) {
        super(uttrykk);
    }

    @Override
    protected Belop nullVerdi() {
        return Belop.NULL;
    }

    @SafeVarargs
    public static BelopSumUttrykk sum(BelopUttrykk... uttrykk) {
        return new BelopSumUttrykk(Stream.of(uttrykk).collect(Collectors.toList()));
    }

    public static BelopSumUttrykk sum(Collection<BelopUttrykk> uttrykk) {
        return new BelopSumUttrykk(uttrykk);
    }

    public BelopSumUttrykk pluss(BelopSumUttrykk sumUttrykk) {
        if (this.navn() == null && sumUttrykk.navn() == null) {
            return summer(sumUttrykk.uttrykk.stream());
        } else {
            return BelopSumUttrykk.sum(this, sumUttrykk);
        }
    }

    public BelopSumUttrykk pluss(BelopUttrykk uttrykk) {
        if (this.navn() == null) {
            return summer(Stream.of(uttrykk));
        } else {
            return BelopSumUttrykk.sum(this, uttrykk);
        }
    }

    private BelopSumUttrykk summer(Stream<BelopUttrykk> stream) {
        return new BelopSumUttrykk(Stream.concat(this.uttrykk.stream(), stream).collect
          (Collectors.toList()));
    }
}
