package ske.fastsetting.skatt.domene;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by jorn ola birkeland on 23.02.15.
 */
public class StedbundetBelop implements KalkulerbarVerdi<StedbundetBelop> {
    private final Map<String,Belop> stedBelopMap;

    public static final StedbundetBelop NULL = new StedbundetBelop(null,0);

    public StedbundetBelop(String sted, int belop) {
        stedBelopMap = new HashMap<>();
        stedBelopMap.put(sted,Belop.kr(belop));
    }

    private StedbundetBelop(Map<String,Belop> stedBelopMap) {
        this.stedBelopMap = stedBelopMap;
    }


    @Override
    public StedbundetBelop minus(StedbundetBelop ledd) {
        Map<String,Belop> diff = new HashMap<>();

        ledd.stedBelopMap.entrySet().stream().forEach(e->diff.put(e.getKey(), e.getValue().byttFortegn()));
        this.stedBelopMap.entrySet().stream().forEach(e->diff.merge(e.getKey(), e.getValue(), Belop::pluss));

        return new StedbundetBelop(diff);
    }

    @Override
    public StedbundetBelop pluss(StedbundetBelop ledd) {
        Map<String,Belop> sum = new HashMap<>();

        ledd.stedBelopMap.entrySet().stream().forEach(e->sum.put(e.getKey(), e.getValue()));
        this.stedBelopMap.entrySet().stream().forEach(e->sum.merge(e.getKey(), e.getValue(), Belop::pluss));

        return new StedbundetBelop(sum);
    }

    @Override
    public StedbundetBelop multiplisertMed(BigDecimal faktor) {
        Map<String,Belop> prod = new HashMap<>();
        this.stedBelopMap.entrySet().stream().forEach(e->prod.put(e.getKey(), e.getValue().multiplisertMed(faktor)));

        return new StedbundetBelop(prod);
    }

    @Override
    public StedbundetBelop dividertMed(BigDecimal divisor) {
        Map<String,Belop> kvot = new HashMap<>();
        this.stedBelopMap.entrySet().stream().forEach(e->kvot.put(e.getKey(), e.getValue().dividertMed(divisor)));

        return new StedbundetBelop(kvot);
    }

    public StedbundetBelop fordelProporsjonalt(Belop belop) {
        Map<String,Belop> resultat = new HashMap<>();

        Belop sum = steduavhengig();

        this.stedBelopMap.entrySet().stream().forEach(e->resultat.put(e.getKey(), e.getValue()));
        this.stedBelopMap.entrySet().stream().forEach(e->resultat.merge(e.getKey(), belop.multiplisertMed(belop.dividertMed(sum)), Belop::pluss));

        return new StedbundetBelop(resultat);
    }

    public Belop steduavhengig() {
        return stedBelopMap.values().stream().reduce(Belop.NULL,Belop::pluss);
    }

    public StedbundetBelop filtrer(Predicate<String> filter) {
        Map<String,Belop> filtrertMap = stedBelopMap.entrySet()
                .stream().filter(e->filter.test(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new StedbundetBelop(filtrertMap);
    }


    @Override
    public String toString() {
        return stedBelopMap.toString();
    }
}
