package ske.fastsetting.skatt.uttrykk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import ske.fastsetting.skatt.uttrykk.util.IdUtil;

@SuppressWarnings("unchecked")
public abstract class UttrykkContextImpl<B extends UttrykkContextImpl> implements UttrykkContext {

    public static final String VM_ARG_DEBUG = "UTTRYKK_DEBUG";
    public static final boolean DEBUG_ENABLED = Boolean.parseBoolean(System.getProperty(VM_ARG_DEBUG));
    private final Map<String, Map> uttrykksmap = new HashMap<>();
    private final Map<Class, Object> inputMedSupertyper = new HashMap<>();
    private final Map<Class, Object> input = new HashMap<>();
    private final List<Overstyring> overstyringer = new ArrayList<>();

    B self = (B)this;

    protected UttrykkContextImpl(Object[] input) {
        Stream.of(input).forEach(this::leggTilInput);
    }



    @Override
    public String beskriv(Uttrykk<?> uttrykk) {

        try {
            initUttrykk(uttrykk).computeIfAbsent(UttrykkResultat.KEY_UTTRYKK, k -> uttrykk.beskriv(this));
            return IdUtil.idLink(uttrykk.id());
        } catch (UttrykkException ue) {
            throw new UttrykkException(ue, uttrykk);
        } catch (Throwable e) {
            throw new UttrykkException(e, uttrykk);
        }

    }

    @Override
    public <X> X eval(Uttrykk<X> uttrykk) {

        if(DEBUG_ENABLED) {
            return evalDebug(uttrykk);
        } else {
            return evalStandard(uttrykk);
        }
    }


    @Override
    public void settInput(Object input) {

        // Trenger ikke å sjekke input hvis typen allerede finnes i input-listen - da overskriver vi.
        // Ellers må vi sjekke for å unngå delvise overskrivninger
        Class<?> clazz = input.getClass();
        boolean sjekkInput = !this.input.containsKey(clazz);

        try {
            settInputRekursivt(clazz, input, sjekkInput);
            this.input.put(clazz, input);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Forsøk på å sette input av type "+clazz+", som allerede er finnes som supertype av en annen input");
        }

    }

    @Override
    public <T> T hentInput(Class<T> clazz) {
        if (inputMedSupertyper.containsKey(clazz)) {
            return (T) inputMedSupertyper.get(clazz);
        } else {
            throw new RuntimeException("Kontekst mangler input av type: " + clazz.getSimpleName());
        }
    }

    @Override
    public void fjernInput(Object input) {

        Class clazz = input.getClass();

        if (!this.input.containsKey(clazz)) {
            throw new IllegalArgumentException("Det finnes ikke input av typen " + clazz);
        }

        fjernInputRekursivt(clazz);
        this.input.put(clazz, input);
    }

    @Override
    public <T> boolean harInput(Class<T> clazz) {
        return inputMedSupertyper.containsKey(clazz);
    }

    @Override
    public UttrykkContext klon() {
        B ny = ny();

        input.values().stream().forEach(v -> ny.settInput(v));
        overstyringer.stream().forEach(os -> os.leggTilOverstyring(ny));

        return ny;
    }

    @Override
    public String toString() {
        return uttrykksmap.toString();
    }

    protected final void leggTilInput(Object... input) {
        Stream.of(input).forEach(verdi -> {
            settInputRekursivt(verdi.getClass(), verdi, true);
            this.input.put(verdi.getClass(),verdi);
        });
    }

    private void fjernInputRekursivt(Class<?> clazz) {
        if (includeClass(clazz)) {
            this.inputMedSupertyper.remove(clazz);
            Stream.of(clazz.getInterfaces()).forEach(this::fjernInputRekursivt);
            Optional.ofNullable(clazz.getSuperclass()).ifPresent(this::fjernInputRekursivt);
        }
    }


    protected abstract B ny();

    public <V> void overstyrVerdi(Uttrykk<V> uttrykk, V verdi) {
        Map map = map(uttrykk);
        map.put(UttrykkResultat.KEY_VERDI,verdi);
        uttrykksmap.put(uttrykk.id(), map);

        overstyringer.add(new Overstyring(uttrykk,verdi));
    }


    protected final <V> UttrykkResultat<V> kalkuler(Uttrykk<V> uttrykk, boolean eval, boolean beskriv) {

        if (eval) {
            eval(uttrykk);
        }

        if (beskriv) {
            beskriv(uttrykk);
        }

        return new UttrykkResultatImpl<>(uttrykk.id());
    }


    private <X> X evalDebug(Uttrykk<X> uttrykk) {

        long start = System.nanoTime();
        X verdi = evalStandard(uttrykk);
        long end = System.nanoTime();

        initUttrykk(uttrykk).computeIfAbsent(UttrykkResultat.KEY_UTTRYKK, k -> uttrykk.beskriv(this));
        initUttrykk(uttrykk).computeIfAbsent(UttrykkResultat.KEY_DEBUG_UTTRYKK_CLASS,k-> uttrykk.getClass().getSimpleName());
        initUttrykk(uttrykk).computeIfAbsent(UttrykkResultat.KEY_DEBUG_UTTRYKK_EVAL_TID_NANOSEK, k -> end - start);

        return verdi;
    }

    private <X> X evalStandard(Uttrykk<X> uttrykk) {
        try {
            return (X) initUttrykk(uttrykk).computeIfAbsent(UttrykkResultat.KEY_VERDI, k -> uttrykk.eval(this));
        } catch (UttrykkException ue) {
            throw new UttrykkException(ue, uttrykk);
        } catch (Throwable e) {
            throw new UttrykkException(e, uttrykk);
        }
    }


    private void settInputRekursivt(Class<?> clazz, Object input, boolean sjekkDuplikat) {
        if (sjekkDuplikat && this.inputMedSupertyper.containsKey(clazz)) {
            throw new IllegalArgumentException("Ugyldig input. Det finnes allerede input som er, implementerer eller arver " + clazz);
        }

        if (includeClass(clazz)) {
            this.inputMedSupertyper.put(clazz, input);
            Stream.of(clazz.getInterfaces()).forEach(i -> settInputRekursivt(i, input, sjekkDuplikat));
            Optional.ofNullable(clazz.getSuperclass()).ifPresent(c -> settInputRekursivt(c, input, sjekkDuplikat));
        }
    }


    private boolean includeClass(Class<?> clazz) {
        return !(
          Object.class.equals(clazz)
            // Hack for å utelukke jaxb-interface'er
            || clazz.getName().contains("org.jvnet.jaxb2_commons.lang.")
        );
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

        public UttrykkResultatImpl(String startId) {
            this.start = startId;
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

    private static class Overstyring {
        private final Uttrykk<?> uttrykk;
        private final Object verdi;

        private Overstyring(Uttrykk<?> uttrykk, Object verdi) {
            this.uttrykk = uttrykk;
            this.verdi = verdi;
        }

        public void leggTilOverstyring(UttrykkContextImpl ny) {
            ny.overstyrVerdi(uttrykk,verdi);
        }
    }
}
