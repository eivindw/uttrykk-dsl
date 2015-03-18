package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.uttrykk.util.IdUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class UttrykkContextImpl<V> implements UttrykkResultat<V>, UttrykkContext {

    private String start;
    private final Map<String, Map> uttrykksmap = new HashMap<>();
    private final Map<Class, Object> input = new HashMap<>();

    public static <X> UttrykkResultat<X> beregne(Uttrykk<X> uttrykk, Object... input) {
        UttrykkContextImpl uttrykkContext = new UttrykkContextImpl<>(input);
        uttrykkContext.kalkuler(uttrykk, true, false);
        return uttrykkContext;
    }

    public static <X> UttrykkResultat<X> beskrive(Uttrykk<X> uttrykk, Object... input) {
        UttrykkContextImpl uttrykkContext = new UttrykkContextImpl<>(input);
        uttrykkContext.kalkuler(uttrykk, false, true);
        return uttrykkContext;
    }

    public static <X> UttrykkResultat<X> beregneOgBeskrive(Uttrykk<X> uttrykk, Object... input) {
        UttrykkContextImpl uttrykkContext = new UttrykkContextImpl<>(input);
        uttrykkContext.kalkuler(uttrykk, true, true);
        return uttrykkContext;
    }

    protected UttrykkContextImpl(Object[] input) {

        Stream.of(input).forEach(this::leggTilInput);

    }

    protected void leggTilInput(Object... input) {
        Stream.of(input).forEach(verdi -> {
            this.input.put(verdi.getClass(), verdi);
            Stream.of(verdi.getClass().getInterfaces()).forEach(i -> this.input.put(i, verdi));
        });
    }

    protected UttrykkResultat<V> kalkuler(Uttrykk<V> uttrykk, boolean eval, boolean beskriv) {

//        if (this.start == null) {
            this.start = uttrykk.id();
//        }


        if (eval) {
            eval(uttrykk);
        }

        if (beskriv) {
            beskriv(uttrykk);
        }

        return this;
    }

    @Override
    public Map<String, Map> uttrykk() {
        return uttrykksmap;
    }

    @Override
    public Map uttrykk(String id) {
        return uttrykksmap.get(id);
    }

    @Override
    public V verdi() {
        return (V) uttrykk(start).get(KEY_VERDI);
    }

    @Override
    public String start() {
        return start;
    }

    @Override
    public String beskriv(Uttrykk<?> uttrykk) {


        initUttrykk(uttrykk).computeIfAbsent(KEY_UTTRYKK, k -> uttrykk.beskriv(this));

        return IdUtil.idLink(uttrykk.id());
    }

    @Override
    public <X> X eval(Uttrykk<X> uttrykk) {

        return (X) initUttrykk(uttrykk).computeIfAbsent(KEY_VERDI, k -> uttrykk.eval(this));
    }


    @Override
    public <T> T input(Class<T> clazz) {
        if (input.containsKey(clazz)) {
            return (T) input.get(clazz);
        } else {
            throw new RuntimeException("Context mangler input av type: " + clazz.getSimpleName());
        }
    }

    @Override
    public <T> boolean harInput(Class<T> clazz) {
        return input.containsKey(clazz);
    }

    @Override
    public String toString() {
        return uttrykksmap.toString();
    }

    private Map initUttrykk(Uttrykk<?> uttrykk) {
        return uttrykksmap.computeIfAbsent(uttrykk.id(), k -> map(uttrykk));
    }

    private Map map(Uttrykk uttrykk) {
        Map<String, Object> map = new HashMap<>();

        map.computeIfAbsent(KEY_NAVN, k -> uttrykk.navn());
        map.computeIfAbsent(KEY_REGLER, k -> uttrykk.regler());
        map.computeIfAbsent(KEY_TAGS, k -> uttrykk.tags());

        return map;
    }

}
