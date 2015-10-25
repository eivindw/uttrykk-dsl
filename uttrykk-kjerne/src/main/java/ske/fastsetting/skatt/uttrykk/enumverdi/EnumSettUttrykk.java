package ske.fastsetting.skatt.uttrykk.enumverdi;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ske.fastsetting.skatt.uttrykk.AbstractUttrykk;
import ske.fastsetting.skatt.uttrykk.UttrykkContext;

public class EnumSettUttrykk<T extends Enum<T>> extends AbstractUttrykk<Set<T>,EnumSettUttrykk<T>>  {

    private List<EnumUttrykk<T>> uttrykk;

    public EnumSettUttrykk(List<EnumUttrykk<T>> uttrykk) {

        this.uttrykk = uttrykk;
    }

    @SafeVarargs
    public static <T extends Enum<T>> EnumSettUttrykk<T> enumSettAv(EnumUttrykk<T> ... uttrykk) {
        return new EnumSettUttrykk<>(Stream.of(uttrykk).collect(Collectors.toList()));
    }

    @Override
    public Set<T> eval(UttrykkContext ctx) {
        return uttrykk.stream()
          .map(ctx::eval)
          .collect(Collectors.toSet());
    }

    @Override
    public String beskriv(UttrykkContext ctx) {
        return
          uttrykk.stream()
          .map(ctx::beskriv)
          .collect(Collectors.joining(", "));
    }
}
