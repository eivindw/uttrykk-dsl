package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

import java.util.Collection;
import java.util.stream.Collectors;

/**
* Created by jorn ola birkeland on 21.03.15.
*/
class TilBelopUttrykk extends AbstractUttrykk<Belop,TilBelopUttrykk> implements BelopUttrykk {
    private Uttrykk<Belop> uttrykk;

    public static Collection<BelopUttrykk> tilBelopUttrykk(Collection<Uttrykk<Belop>> uttrykk) {
        return uttrykk.stream().map(TilBelopUttrykk::tilBelopUttrykk).collect(Collectors.toList());
    }

    public static BelopUttrykk tilBelopUttrykk(Uttrykk<Belop> uttrykk) {
        return new TilBelopUttrykk(uttrykk);
    }

    public TilBelopUttrykk(Uttrykk<Belop> uttrykk) {
        this.uttrykk = uttrykk;
    }

    @Override
    public Belop eval(UttrykkContext ctx) {
        return uttrykk.eval(ctx);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return uttrykk.beskriv(ctx);
    }
}
