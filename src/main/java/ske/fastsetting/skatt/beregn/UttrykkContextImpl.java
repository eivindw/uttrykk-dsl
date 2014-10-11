package ske.fastsetting.skatt.beregn;

import java.util.HashMap;
import java.util.Map;

public class UttrykkContextImpl<T> implements UttrykkResultat<T>, UttrykkContext {

    private static final String KEY_NAVN = "navn";
    private static final String KEY_VERDI = "verdi";
    private static final String KEY_UTTRYKK = "uttrykk";

    private final Map<String, Map> uttrykksmap = new HashMap<>();
    private final String start;

    private int nesteId = 1;

    public static <T> UttrykkResultat<T> beregne(Uttrykk<T> uttrykk) {
        return new UttrykkContextImpl<>(uttrykk, true, false);
    }

    public static <T> UttrykkResultat<T> beskrive(Uttrykk<T> uttrykk) {
        return new UttrykkContextImpl<>(uttrykk, false, true);
    }

    public static <T> UttrykkResultat<T> beregneOgBeskrive(Uttrykk<T> uttrykk) {
        return new UttrykkContextImpl<>(uttrykk, true, true);
    }

    private UttrykkContextImpl(Uttrykk<T> uttrykk, boolean eval, boolean beskriv) {
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

        return "<" + uttrykk.id(this) + ">";
    }

    @Override
    public <X> X eval(Uttrykk<X> uttrykk) {
        return (X) initUttrykk(uttrykk).computeIfAbsent(KEY_VERDI, k -> uttrykk.eval(this));
    }

    @Override
    public int nyId() {
        return nesteId++;
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
            put(KEY_NAVN, uttrykk.navn());
        }};
    }
}
