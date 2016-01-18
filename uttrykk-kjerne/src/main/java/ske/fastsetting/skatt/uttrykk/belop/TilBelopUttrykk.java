package ske.fastsetting.skatt.uttrykk.belop;

import java.util.Collection;
import java.util.stream.Collectors;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.WrapperUttrykk;

public class TilBelopUttrykk extends WrapperUttrykk<Belop,TilBelopUttrykk> implements BelopUttrykk {

    public static Collection<BelopUttrykk> tilBelopUttrykk(Collection<Uttrykk<Belop>> uttrykk) {
        return uttrykk.stream().map(TilBelopUttrykk::tilBelopUttrykk).collect(Collectors.toList());
    }

    public static BelopUttrykk tilBelopUttrykk(Uttrykk<Belop> uttrykk) {
        return new TilBelopUttrykk(uttrykk);
    }

    public static TilBelopUttrykk pakkInn(Uttrykk<Belop> uttrykk) {
        return new TilBelopUttrykk(uttrykk);
    }

    public TilBelopUttrykk(Uttrykk<Belop> uttrykk) {
        super(uttrykk);
    }

}
