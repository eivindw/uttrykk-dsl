package ske.fastsetting.skatt.uttrykk.bolsk;

import static java.util.stream.Stream.concat;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class EllerUttrykk extends AbstractBolskUttrykk {
    private final Collection<BolskUttrykk> uttrykk;

    public static EllerUttrykk minstEnSann(BolskUttrykk ... uttrykk) {
        return new EllerUttrykk(Stream.of(uttrykk).collect(Collectors.toList()));
    }

    EllerUttrykk(BolskUttrykk forsteUttrykk, BolskUttrykk andreUttrykk) {
        this(Stream.of(forsteUttrykk, andreUttrykk).collect(Collectors.toList()));
    }

    EllerUttrykk(Collection<BolskUttrykk> uttrykk) {
        this.uttrykk = uttrykk;
    }


    @Override
    public Boolean eval(UttrykkContext ctx) {
        return uttrykk.stream()
          .map(ctx::eval)
          .reduce(false,(result,v)->result || v);

    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return uttrykk.stream()
          .map(ctx::beskriv)
          .collect(Collectors.joining(" eller "));
    }

    @Override
    public AbstractBolskUttrykk eller(BolskUttrykk uttrykk) {
        if(navn()==null && tags()==null)
            return new EllerUttrykk(concat(this.uttrykk.stream(),Stream.of(uttrykk)).collect(Collectors.toList()));
        else
            return new EllerUttrykk(this,uttrykk);
    }
}
