package ske.fastsetting.skatt.beregn;

import java.util.UUID;

public abstract class AbstractUttrykk<T> implements Uttrykk<T> {

    private final String id = UUID.randomUUID().toString();
    private String navn = "";

    public Uttrykk<T> navn(String navn) {
        this.navn = navn;
        return this;
    }

    @Override
    public String navn() {
        return navn;
    }

    @Override
    public String id() {
        return id;
    }
}
