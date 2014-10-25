package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.uttrykk.util.IdUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class UttrykkContextImpl<V> implements UttrykkResultat<V>, UttrykkContext {

    private final String start;
    private final Map<String, Map> uttrykksmap = new HashMap<>();
    private final Map<Class, Object> input = new HashMap<>();

    public static <X> UttrykkResultat<X> beregne(Uttrykk<X> uttrykk, Object... input) {
        return new UttrykkContextImpl<>(uttrykk, true, false, input);
    }

    public static <X> UttrykkResultat<X> beskrive(Uttrykk<X> uttrykk, Object... input) {
        return new UttrykkContextImpl<>(uttrykk, false, true, input);
    }

    public static <X> UttrykkResultat<X> beregneOgBeskrive(Uttrykk<X> uttrykk, Object... input) {
        return new UttrykkContextImpl<>(uttrykk, true, true, input);
    }

    private UttrykkContextImpl(Uttrykk<V> uttrykk, boolean eval, boolean beskriv, Object... input) {
        this.start = uttrykk.id(this);

        Stream.of(input).forEach(verdi -> {
            this.input.put(verdi.getClass(), verdi);
            Stream.of(verdi.getClass().getInterfaces()).forEach(i -> this.input.put(i, verdi));
        });

        if (eval) {
            eval(uttrykk);
        }

        if (beskriv) {
            beskriv(uttrykk);
        }
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

        return IdUtil.idLink(uttrykk.id(this));
    }

    @Override
    public <X> X eval(Uttrykk<X> uttrykk) {
        return (X) initUttrykk(uttrykk).computeIfAbsent(KEY_VERDI, k -> uttrykk.eval(this));
    }

    @Override
    public String nyId() {
        String id = IdUtil.lagTilfeldigId();
        while (uttrykksmap.containsKey(id)) {
            id = IdUtil.lagTilfeldigId();
        }
        return id;
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
    public String toString() {
        return uttrykksmap.toString();
    }

    private Map initUttrykk(Uttrykk<?> uttrykk) {
        return uttrykksmap.computeIfAbsent(uttrykk.id(this), k -> map(uttrykk));
    }

    private Map map(Uttrykk uttrykk) {
        Map<String, Object> map = new HashMap<>();

        map.computeIfAbsent(KEY_NAVN, k -> uttrykk.navn());
        map.computeIfAbsent(KEY_REGLER, k -> uttrykk.regler());
        map.computeIfAbsent(KEY_TAGS, k -> uttrykk.tags());

        return map;
    }
}
