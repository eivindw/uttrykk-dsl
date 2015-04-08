package ske.fastsetting.skatt.domene;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StedbundetBelop<T> implements KalkulerbarVerdi<StedbundetBelop<T>> {


    public static class BelopSted<T> {
        private final T sted;
        private final Belop belop;

        public BelopSted(T sted, Belop belop) {
            this.sted = sted;
            this.belop = belop;
        }

        public T getSted() {
            return sted;
        }

        public Belop getBelop() {
            return belop;
        }
    }

    private final Map<T, Belop> stedBelopMap;

    public static <T> StedbundetBelop<T> kr0() {
        return new StedbundetBelop<>();
    }

    public static <T> StedbundetBelop<T> kr(int belop, T sted) {
        return new StedbundetBelop<>(sted, Belop.kr(belop));
    }

    public static <T> StedbundetBelop<T> kr(Belop belop, T sted) {
        return new StedbundetBelop<>(sted, belop);
    }

    public static <T> StedbundetBelop<T> kr(double belop, T sted) {
        return new StedbundetBelop<>(sted, Belop.kr(belop));
    }

    public StedbundetBelop(T sted, Belop belop) {
        stedBelopMap = new HashMap<>();
        stedBelopMap.put(sted, belop);
    }

    public StedbundetBelop() {
        stedBelopMap = new HashMap<>();
    }

    private StedbundetBelop(Map<T, Belop> stedBelopMap) {
        this.stedBelopMap = stedBelopMap;
    }

    @Override
    public StedbundetBelop<T> minus(StedbundetBelop<T> ledd) {
        Map<T, Belop> diff = new HashMap<>();

        ledd.stedBelopMap.entrySet().stream().forEach(e -> diff.put(e.getKey(), e.getValue().byttFortegn()));
        this.stedBelopMap.entrySet().stream().forEach(e -> diff.merge(e.getKey(), e.getValue(), Belop::pluss));

        return new StedbundetBelop<>(diff);
    }

    @Override
    public StedbundetBelop<T> pluss(StedbundetBelop<T> ledd) {
        Map<T, Belop> sum = new HashMap<>();

        ledd.stedBelopMap.entrySet().stream().forEach(e -> sum.put(e.getKey(), e.getValue()));
        this.stedBelopMap.entrySet().stream().forEach(e -> sum.merge(e.getKey(), e.getValue(), Belop::pluss));

        return new StedbundetBelop<>(sum);
    }

    public StedbundetBelop<T> pluss(BelopSted<T> belopSted) {
        return pluss(kr(belopSted.getBelop(),belopSted.getSted()));
    }

    @Override
    public StedbundetBelop<T> multiplisertMed(BigDecimal faktor) {
        Map<T, Belop> prod = new HashMap<>();
        this.stedBelopMap.entrySet().stream().forEach(e -> prod.put(e.getKey(), e.getValue().multiplisertMed(faktor)));

        return new StedbundetBelop<>(prod);
    }

    @Override
    public StedbundetBelop<T> dividertMed(BigDecimal divisor) {
        Map<T, Belop> kvot = new HashMap<>();
        this.stedBelopMap.entrySet().stream().forEach(e -> kvot.put(e.getKey(), e.getValue().dividertMed(divisor)));

        return new StedbundetBelop<>(kvot);
    }

    public StedbundetBelop<T> fordelProporsjonalt(Belop belop) {
        Map<T, Belop> resultat = new HashMap<>();

        Belop sum = abs().steduavhengig();

        this.stedBelopMap.entrySet().stream().forEach(e -> resultat.put(e.getKey(), e.getValue()));

        if (sum.erStorreEnn(Belop.NULL)) {
            this.stedBelopMap.entrySet().stream()
              .forEach(e -> {
                  final Belop value = belop.multiplisertMed(e.getValue().abs().dividertMed(sum));
                  resultat.merge(e.getKey(), value, Belop::pluss);
              });
        } else if (this.stedBelopMap.size() > 0) {
            Belop andel = belop.dividertMed(stedBelopMap.size());
            this.stedBelopMap.entrySet().stream()
              .forEach(e -> resultat.merge(e.getKey(), andel, Belop::pluss));
        }
        return new StedbundetBelop<>(resultat);
    }

    public Belop steduavhengig() {
        return stedBelopMap.values().stream().reduce(Belop.NULL, Belop::pluss);
    }


    public List<T> steder() {
        return stedBelopMap.keySet().stream().collect(Collectors.toList());
    }

    public Set<BelopSted<T>> splitt() {
        return stedBelopMap.entrySet().stream().map(e -> new BelopSted<>(e.getKey(), e.getValue())).collect
          (Collectors.toSet());
    }

    public Belop get(Object sted) {
        return stedBelopMap.getOrDefault(sted, Belop.NULL);
    }

    @Override
    public String toString() {
        return stedBelopMap.toString();
    }

    public StedbundetBelop abs() {

        Map<T, Belop> resultat = stedBelopMap.entrySet()
          .stream()
          .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().abs()));

        return new StedbundetBelop<>(resultat);

    }

    public StedbundetBelop<T> byttFortegn() {

        Map<T, Belop> resultat = stedBelopMap.entrySet()
          .stream()
          .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().byttFortegn()));

        return new StedbundetBelop<>(resultat);

    }


    public boolean harSted(T sted) {
        return stedBelopMap.containsKey(sted);
    }

    public StedbundetBelop<T> rundAvTilHeleKroner() {
        Map<T, Belop> resultat = stedBelopMap.entrySet()
          .stream()
          .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().rundAvTilHeleKroner()));

        return new StedbundetBelop<>(resultat);
    }

    public StedbundetBelop<T> nyMedSammeFordeling(Belop belop) {
        return fordelProporsjonalt(belop).minus(this);
    }
}
