package ske.fastsetting.skatt.uttrykk.belop;

import java.util.Collection;
import java.util.stream.Collectors;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class TilBelopUttrykk extends AbstractUttrykk<Belop,TilBelopUttrykk> implements BelopUttrykk {
    private Uttrykk<Belop> uttrykk;

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
        this.uttrykk = uttrykk;
        navn(uttrykk.navn());
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
