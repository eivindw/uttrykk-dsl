package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.uttrykk.util.IdUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class UttrykkContextImpl implements UttrykkContext {

    private final Map<String, Map> uttrykksmap = new HashMap<>();
    private final Map<Class, Object> input = new HashMap<>();

    public static <X> UttrykkResultat<X> beregne(Uttrykk<X> uttrykk, Object... input) {
        UttrykkContextImpl uttrykkContext = new UttrykkContextImpl(input);
        return uttrykkContext.kalkuler(uttrykk, true, false);
    }

    public static <X> UttrykkResultat<X> beskrive(Uttrykk<X> uttrykk, Object... input) {
        UttrykkContextImpl uttrykkContext = new UttrykkContextImpl(input);
        return uttrykkContext.kalkuler(uttrykk, false, true);
    }

    public static <X> UttrykkResultat<X> beregneOgBeskrive(Uttrykk<X> uttrykk, Object... input) {
        UttrykkContextImpl uttrykkContext = new UttrykkContextImpl(input);
        return uttrykkContext.kalkuler(uttrykk, true, true);
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

    protected <V> void overstyrVerdi(Uttrykk<V> uttrykk, V verdi) {
        initUttrykk(uttrykk).computeIfAbsent(UttrykkResultat.KEY_VERDI,k->verdi);
    }

    protected <V> UttrykkResultat<V> kalkuler(Uttrykk<V> uttrykk, boolean eval, boolean beskriv) {

        if (eval) {
            eval(uttrykk);
        }

        if (beskriv) {
            beskriv(uttrykk);
        }

        return new UttrykkResultatImpl<>(uttrykk.id(), uttrykksmap);
    }


    @Override
    public String beskriv(Uttrykk<?> uttrykk) {

        try {
            initUttrykk(uttrykk).computeIfAbsent(UttrykkResultat.KEY_UTTRYKK, k -> uttrykk.beskriv(this));
            return IdUtil.idLink(uttrykk.id());
        } catch (Throwable e) {
            throw new UttrykkException(e, uttrykk);
        }

    }

    @Override
    public <X> X eval(Uttrykk<X> uttrykk) {

        try {
            return (X) initUttrykk(uttrykk).computeIfAbsent(UttrykkResultat.KEY_VERDI, k -> uttrykk.eval(this));
        } catch (Throwable e) {
            throw new UttrykkException(e, uttrykk);
        }
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

        map.computeIfAbsent(UttrykkResultat.KEY_NAVN, k -> uttrykk.navn());
        map.computeIfAbsent(UttrykkResultat.KEY_REGLER, k -> uttrykk.regler());
        map.computeIfAbsent(UttrykkResultat.KEY_TAGS, k -> uttrykk.tags());

        return map;
    }

    private class UttrykkResultatImpl<V> implements UttrykkResultat<V> {
        private final String start;
        private final Map<String, Map> uttrykksmap;

        public UttrykkResultatImpl(String startId, Map<String, Map> uttrykksmap) {
            this.start = startId;
            this.uttrykksmap = uttrykksmap;
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
    }
}
