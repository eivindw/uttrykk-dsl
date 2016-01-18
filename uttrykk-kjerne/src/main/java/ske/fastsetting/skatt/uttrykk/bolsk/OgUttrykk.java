package ske.fastsetting.skatt.uttrykk.bolsk;

import static java.util.stream.Stream.concat;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class OgUttrykk extends AbstractBolskUttrykk {

    private final Collection<BolskUttrykk> uttrykk;

    public OgUttrykk(BolskUttrykk forsteUttrykk, BolskUttrykk andreUttrykk) {
        this(Stream.of(forsteUttrykk, andreUttrykk).collect(Collectors.toList()));
    }

    public OgUttrykk(Collection<BolskUttrykk> uttrykk) {
        this.uttrykk = uttrykk;
    }

    @Override
    public Boolean eval(UttrykkContext ctx) {
        return uttrykk.stream()
          .map(ctx::eval)
          .reduce(true,(result,v)->result && v);

    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return uttrykk.stream()
          .map(ctx::beskriv)
          .collect(Collectors.joining(" og "));
    }

    @Override
    public AbstractBolskUttrykk og(BolskUttrykk uttrykk) {
        if(navn()==null && tags()==null)
            return new OgUttrykk(concat(this.uttrykk.stream(),Stream.of(uttrykk)).collect(Collectors.toList()));
        else
            return new OgUttrykk(this,uttrykk);
    }
}
