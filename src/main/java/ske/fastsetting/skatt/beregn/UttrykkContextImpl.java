package ske.fastsetting.skatt.beregn;

import ske.fastsetting.skatt.beregn.util.IdUtil;

import java.util.HashMap;
import java.util.Map;

public class UttrykkContextImpl<T> implements UttrykkResultat<T>, UttrykkContext {

    private final Map<String, Map> uttrykksmap = new HashMap<>();
    private final String start;

    private int nesteId = 1;

    public static UttrykkResultat beregne(Uttrykk<?> uttrykk) {
        return new UttrykkContextImpl<>(uttrykk, true, false);
    }

    public static UttrykkResultat beskrive(Uttrykk<?> uttrykk) {
        return new UttrykkContextImpl<>(uttrykk, false, true);
    }

    public static UttrykkResultat beregneOgBeskrive(Uttrykk<?> uttrykk) {
        return new UttrykkContextImpl<>(uttrykk, true, true);
    }

    private UttrykkContextImpl(Uttrykk<?> uttrykk, boolean eval, boolean beskriv) {
        this.start = uttrykk.id(this);

        if (beskriv) {
            beskriv(uttrykk);
        }

        if (eval) {
            eval(uttrykk);
        }
    }

    @Override
    public Map<String, Map> uttrykk() {
        return uttrykksmap;
    }

    @Override
    public T verdi() {
        return (T) uttrykksmap.get(start).get(KEY_VERDI);
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
    public <X> X eval(Uttrykk<X> uttrykk) {
        return (X) initUttrykk(uttrykk).computeIfAbsent(KEY_VERDI, k -> uttrykk.eval(this));
    }

    @Override
    public String nyId() {
        return String.valueOf(nesteId++);
    }

    @Override
    public String toString() {
        return uttrykksmap.toString();
    }

    private Map initUttrykk(Uttrykk uttrykk) {
        return uttrykksmap.computeIfAbsent(uttrykk.id(this), k -> map(uttrykk));
    }

    private Map map(Uttrykk uttrykk) {
        return new HashMap<String, Object>() {{
            final String navn = uttrykk.navn();
            if (navn != null && !navn.equals("")) {
                put(KEY_NAVN, navn);
            }
        }};
    }
}
