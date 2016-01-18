package ske.fastsetting.skatt.domene;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MultiTall<T> implements Comparable<Tall>, KalkulerbarVerdi<MultiTall<T>> {


    public static <T> MultiTall<T> NULL() { return new MultiTall<>(new HashMap<>());}

    private final Map<T, Tall> nokkelTallMap;

    public MultiTall(Map<T, Tall> diff) {
        nokkelTallMap = diff;
    }

    @Override
    public int compareTo(Tall o) {
        return 0;
    }

    @Override
    public MultiTall<T> minus(MultiTall<T> ledd) {
        Map<T, Tall> diff = new HashMap<>();

        ledd.nokkelTallMap.entrySet().stream().forEach(e -> diff.put(e.getKey(), e.getValue().byttFortegn()));
        this.nokkelTallMap.entrySet().stream().forEach(e -> diff.merge(e.getKey(), e.getValue(), Tall::pluss));

        return new MultiTall<>(diff);
    }

    @Override
    public MultiTall<T> pluss(MultiTall<T> ledd) {
        Map<T, Tall> sum = new HashMap<>();

        ledd.nokkelTallMap.entrySet().stream().forEach(e -> sum.put(e.getKey(), e.getValue()));
        this.nokkelTallMap.entrySet().stream().forEach(e -> sum.merge(e.getKey(), e.getValue(), Tall::pluss));

        return new MultiTall<>(sum);
    }

    @Override
    public MultiTall<T> multiplisertMed(BigDecimal faktor) {
        Map<T, Tall> prod = new HashMap<>();
        this.nokkelTallMap.entrySet().stream().forEach(e -> prod.put(e.getKey(), e.getValue().multiplisertMed(faktor)));

        return new MultiTall<>(prod);
    }

    @Override
    public MultiTall<T> dividertMed(BigDecimal divisor) {
        Map<T, Tall> kvot = new HashMap<>();
        this.nokkelTallMap.entrySet().stream().forEach(e -> kvot.put(e.getKey(), e.getValue().dividertMed(divisor)));

        return new MultiTall<>(kvot);
    }

    public Tall get(T nokkel) {
        return nokkelTallMap.getOrDefault(nokkel,Tall.ZERO);
    }

    public Tall getOrElse(T key, Tall standard) {
        return nokkelTallMap.getOrDefault(key,standard);
    }

    public static <T> MultiTall<T> heltall(int tall, T nokkel) {
        Map<T, Tall> multitall = new HashMap<>();
        multitall.put(nokkel,Tall.heltall(tall));
        return new MultiTall<>(multitall);
    }
}
