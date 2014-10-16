package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.uttrykk.util.IdUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UttrykkContextImpl<V> implements UttrykkResultat<V>, UttrykkContext {

    private final Map<String, Map> uttrykksmap = new HashMap<>();
    private final String start;

    public static <X> UttrykkResultat<X> beregne(Uttrykk<X, ?> uttrykk) {
        return new UttrykkContextImpl<>(uttrykk, true, false);
    }

    public static <X> UttrykkResultat<X> beskrive(Uttrykk<X, ?> uttrykk) {
        return new UttrykkContextImpl<>(uttrykk, false, true);
    }

    public static <X> UttrykkResultat<X> beregneOgBeskrive(Uttrykk<X, ?> uttrykk) {
        return new UttrykkContextImpl<>(uttrykk, true, true);
    }

    private UttrykkContextImpl(Uttrykk<V, ?> uttrykk, boolean eval, boolean beskriv) {
        this.start = uttrykk.id(this);

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
    public String beskriv(Uttrykk uttrykk) {
        initUttrykk(uttrykk).computeIfAbsent(KEY_UTTRYKK, k -> uttrykk.beskriv(this));

        return IdUtil.idLink(uttrykk.id(this));
    }

    @Override
    public <X> X eval(Uttrykk<X, ?> uttrykk) {
        return (X) initUttrykk(uttrykk).computeIfAbsent(KEY_VERDI, k -> uttrykk.eval(this));
    }

    @Override
    public String nyId() {
        String id = lagTilfeldigId();
        while (uttrykksmap.containsKey(id)) {
            id = lagTilfeldigId();
        }
        return id;
    }

    private String lagTilfeldigId() {
        // TODO - denne er ikke garantert unik - for korthet - kom opp med noe bedre :)
        return Integer.toHexString(UUID.randomUUID().hashCode());
    }

    @Override
    public String toString() {
        return uttrykksmap.toString();
    }

    private Map initUttrykk(Uttrykk uttrykk) {
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
