package ske.fastsetting.skatt.uttrykk.tall;

import java.util.Collection;
import java.util.stream.Collectors;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class TilTallUttrykk extends AbstractUttrykk<Tall,TilTallUttrykk> implements TallUttrykk {
    private Uttrykk<Tall> uttrykk;

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
        this.uttrykk = uttrykk;
        navn(uttrykk.navn());
    }

    @Override
    public Tall eval(UttrykkContext ctx) {
        return uttrykk.eval(ctx);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return uttrykk.beskriv(ctx);
    }

}
