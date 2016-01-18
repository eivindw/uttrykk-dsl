package ske.fastsetting.skatt.uttrykk.tall;

import java.util.Collection;
import java.util.stream.Collectors;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.WrapperUttrykk;

public class TilTallUttrykk extends WrapperUttrykk<Tall,TilTallUttrykk> implements TallUttrykk {

    public static Collection<TallUttrykk> tilTallUttrykk(Collection<Uttrykk<Tall>> uttrykk) {
        return uttrykk.stream().map(TilTallUttrykk::tilTalUttrykk).collect(Collectors.toList());
    }

    public static TilTallUttrykk tilTalUttrykk(Uttrykk<Tall> uttrykk) {
        return new TilTallUttrykk(uttrykk);
    }

    public static TilTallUttrykk pakkInn(Uttrykk<Tall> uttrykk) {
        return new TilTallUttrykk(uttrykk);
    }

    public TilTallUttrykk(Uttrykk<Tall> uttrykk) {
        super(uttrykk);
    }

}
