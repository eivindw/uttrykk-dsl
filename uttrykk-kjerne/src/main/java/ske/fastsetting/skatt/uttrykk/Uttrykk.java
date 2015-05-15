package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.Hjemmel;
import ske.fastsetting.skatt.domene.Regel;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface Uttrykk<V> {
    V eval(UttrykkContext ctx);

    String beskriv(UttrykkContext ctx);

    String id();

    String navn();

    Set<String> tags();

    @Deprecated
    List<Regel> regler();

    List<Hjemmel> hjemler();

    default BolskUttrykk erEnAv(Collection<V> verdier) {
        return new ErEnAvUttrykk<>(this, verdier);
    }

    default BolskUttrykk erIkkeEnAv(Collection<V> verdier) {
        return new ErIkkeEnAvUttrykk<>(this, verdier);
    }

    static class ErEnAvUttrykk<T> extends BolskUttrykk {
        private final Uttrykk<T> uttrykk;
        private final Collection<T> verdier;

        private ErEnAvUttrykk(Uttrykk<T> uttrykk, Collection<T> verdier) {
            this.uttrykk = uttrykk;
            this.verdier = verdier;
        }

        public Boolean eval(UttrykkContext ctx) {
            return verdier.contains(ctx.eval(uttrykk));
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return verdier.stream().map(T::toString)
              .collect(Collectors.joining(", ", ctx.beskriv(uttrykk) + " er en av (", ")"));
        }
    }

    static class ErIkkeEnAvUttrykk<T> extends BolskUttrykk {
        private final Uttrykk<T> uttrykk;
        private final Collection<T> verdier;

        private ErIkkeEnAvUttrykk(Uttrykk<T> uttrykk, Collection<T> verdier) {
            this.uttrykk = uttrykk;
            this.verdier = verdier;
        }

        public Boolean eval(UttrykkContext ctx) {
            return !verdier.contains(ctx.eval(uttrykk));
        }

        @Override
        public String beskriv(UttrykkContext ctx) {
            return verdier.stream().map(T::toString)
                    .collect(Collectors.joining(", ", ctx.beskriv(uttrykk) + " er ikke en av (", ")"));
        }
    }

}
