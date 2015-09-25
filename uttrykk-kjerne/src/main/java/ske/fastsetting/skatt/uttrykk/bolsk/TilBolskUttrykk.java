package ske.fastsetting.skatt.uttrykk.bolsk;

import java.util.Collection;
import java.util.stream.Collectors;

import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class TilBolskUttrykk extends BolskUttrykk {
    private Uttrykk<Boolean> uttrykk;

    public static Collection<BolskUttrykk> tilBolskUttrykk(Collection<Uttrykk<Boolean>> uttrykk) {
        return uttrykk.stream().map(TilBolskUttrykk::tilTalUttrykk).collect(Collectors.toList());
    }

    public static TilBolskUttrykk tilTalUttrykk(Uttrykk<Boolean> uttrykk) {
        return new TilBolskUttrykk(uttrykk);
    }

    public static TilBolskUttrykk pakkInn(Uttrykk<Boolean> uttrykk) {
        return new TilBolskUttrykk(uttrykk);
    }

    public TilBolskUttrykk(Uttrykk<Boolean> uttrykk) {
        this.uttrykk = uttrykk;
        navn(uttrykk.navn());
    }

    @Override
    public Boolean eval(UttrykkContext ctx) {
        return ctx.eval(uttrykk);
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return ctx.beskriv(uttrykk);
    }
}
