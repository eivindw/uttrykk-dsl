package ske.fastsetting.skatt.domene;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MultiBelop<T> implements Comparable<Belop>, KalkulerbarVerdi<MultiBelop<T>> {


    @Override
    public int compareTo(Belop belop) {
        return this.tilBelop().compareTo(belop);
    }

    public static class BelopSted<T> {
        private final T nokkel;
        private final Belop belop;

        public BelopSted(T nokkel, Belop belop) {
            this.nokkel = nokkel;
            this.belop = belop;
        }

        public T getNokkel() {
            return nokkel;
        }

        public Belop getBelop() {
            return belop;
        }
    }

    private final Map<T, Belop> nokkelBelopMap;

    public static <T> MultiBelop<T> kr0() {
        return new MultiBelop<>();
    }

    public static <T> MultiBelop<T> kr(int belop, T sted) {
        return new MultiBelop<>(sted, Belop.kr(belop));
    }

    public static <T> MultiBelop<T> kr(Belop belop, T sted) {
        return new MultiBelop<>(sted, belop);
    }

    public static <T> MultiBelop<T> kr(double belop, T sted) {
        return new MultiBelop<>(sted, Belop.kr(belop));
    }

    public MultiBelop(T sted, Belop belop) {
        nokkelBelopMap = new HashMap<>();
        nokkelBelopMap.put(sted, belop);
    }

    public MultiBelop() {
        nokkelBelopMap = new HashMap<>();
    }

    private MultiBelop(Map<T, Belop> nokkelBelopMap) {
        this.nokkelBelopMap = nokkelBelopMap;
    }

    @Override
    public MultiBelop<T> minus(MultiBelop<T> ledd) {
        Map<T, Belop> diff = new HashMap<>();

        ledd.nokkelBelopMap.entrySet().stream().forEach(e -> diff.put(e.getKey(), e.getValue().byttFortegn()));
        this.nokkelBelopMap.entrySet().stream().forEach(e -> diff.merge(e.getKey(), e.getValue(), Belop::pluss));

        return new MultiBelop<>(diff);
    }

    @Override
    public MultiBelop<T> pluss(MultiBelop<T> ledd) {
        Map<T, Belop> sum = new HashMap<>();

        ledd.nokkelBelopMap.entrySet().stream().forEach(e -> sum.put(e.getKey(), e.getValue()));
        this.nokkelBelopMap.entrySet().stream().forEach(e -> sum.merge(e.getKey(), e.getValue(), Belop::pluss));

        return new MultiBelop<>(sum);
    }

    public MultiBelop<T> pluss(BelopSted<T> belopSted) {
        return pluss(kr(belopSted.getBelop(),belopSted.getNokkel()));
    }

    @Override
    public MultiBelop<T> multiplisertMed(BigDecimal faktor) {
        Map<T, Belop> prod = new HashMap<>();
        this.nokkelBelopMap.entrySet().stream().forEach(e -> prod.put(e.getKey(), e.getValue().multiplisertMed(faktor)));

        return new MultiBelop<>(prod);
    }

    public MultiBelop<T> multiplisertMed(MultiTall<T> faktor) {
        Map<T, Belop> prod = new HashMap<>();
        this.nokkelBelopMap.entrySet().stream().forEach(e -> prod.put(e.getKey(), e.getValue().multiplisertMed(faktor.getOrElse(e.getKey(),Tall.ZERO).toBigDecimal())));

        return new MultiBelop<>(prod);
    }


    @Override
    public MultiBelop<T> dividertMed(BigDecimal divisor) {
        Map<T, Belop> kvot = new HashMap<>();
        this.nokkelBelopMap.entrySet().stream().forEach(e -> kvot.put(e.getKey(), e.getValue().dividertMed(divisor)));

        return new MultiBelop<>(kvot);
    }

    public MultiTall<T> dividertMed(MultiBelop<T> divisor, Tall.TallUttrykkType type) {
        Map<T, Tall> kvot = new HashMap<>();
        this.nokkelBelopMap.entrySet().stream().forEach(e -> kvot.put(e.getKey(), Tall.nytt(e.getValue().dividertMed(divisor.get(e.getKey())),type)));

        return new MultiTall<>(kvot);
    }


    public MultiBelop<T> fordelProporsjonalt(Belop belop) {
        Map<T, Belop> resultat = new HashMap<>();

        Belop sum = abs().tilBelop();

        this.nokkelBelopMap.entrySet().stream().forEach(e -> resultat.put(e.getKey(), e.getValue()));

        if (sum.erStorreEnn(Belop.NULL)) {
            this.nokkelBelopMap.entrySet().stream()
              .forEach(e -> {
                  final Belop value = belop.multiplisertMed(e.getValue().abs().dividertMed(sum));
                  resultat.merge(e.getKey(), value, Belop::pluss);
              });
        } else if (this.nokkelBelopMap.size() > 0) {
            Belop andel = belop.dividertMed(nokkelBelopMap.size());
            this.nokkelBelopMap.entrySet().stream()
              .forEach(e -> resultat.merge(e.getKey(), andel, Belop::pluss));
        }
        return new MultiBelop<>(resultat);
    }

    public Belop tilBelop() {
        return nokkelBelopMap.values().stream().reduce(Belop.NULL, Belop::pluss);
    }


    public List<T> steder() {
        return nokkelBelopMap.keySet().stream().collect(Collectors.toList());
    }

    public Set<BelopSted<T>> splitt() {
        return nokkelBelopMap.entrySet().stream().map(e -> new BelopSted<>(e.getKey(), e.getValue())).collect
          (Collectors.toSet());
    }

    public Belop get(Object sted) {
        return nokkelBelopMap.getOrDefault(sted, Belop.NULL);
    }

    @Override
    public String toString() {
        return nokkelBelopMap.size()>0 ? nokkelBelopMap.toString() : "kr 0";
    }

    public MultiBelop abs() {

        Map<T, Belop> resultat = nokkelBelopMap.entrySet()
          .stream()
          .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().abs()));

        return new MultiBelop<>(resultat);

    }

    public MultiBelop<T> byttFortegn() {

        Map<T, Belop> resultat = nokkelBelopMap.entrySet()
          .stream()
          .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().byttFortegn()));

        return new MultiBelop<>(resultat);

    }


    public boolean harSted(T sted) {
        return nokkelBelopMap.containsKey(sted);
    }

    public MultiBelop<T> rundAvTilHeleKroner() {
        Map<T, Belop> resultat = nokkelBelopMap.entrySet()
          .stream()
          .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().rundAvTilHeleKroner()));

        return new MultiBelop<>(resultat);
    }

    public MultiBelop<T> nyMedSammeFordeling(Belop belop) {
        return fordelProporsjonalt(belop).minus(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MultiBelop other = (MultiBelop) o;

        if (nokkelBelopMap.size()!= nokkelBelopMap.size()) {
            return false;
        }

        for(T key : nokkelBelopMap.keySet())
        if (!other.nokkelBelopMap.containsKey(key)) {
            return false;
        }

        for(T key : nokkelBelopMap.keySet()) {
            if(!nokkelBelopMap.get(key).equals(other.nokkelBelopMap.get(key))) {
                return false;
            }
        }

        return true;
    }

    public MultiBelop<T> fjernNullBelop() {
        Map<T,Belop> resultat =
          nokkelBelopMap.entrySet()
            .stream()
            .filter(p -> !p.getValue().equals(Belop.NULL))
            .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));

        return new MultiBelop<>(resultat);
    }

    @Override
    public int hashCode() {

        int result = nokkelBelopMap.keySet().stream()
          .map(T::hashCode).reduce(Integer.MAX_VALUE,(a,b)->a^b);

        return nokkelBelopMap.values().stream()
          .map(Belop::hashCode).reduce(result,(a,b)->a^b);

    }
}
