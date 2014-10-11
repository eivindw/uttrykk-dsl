package ske.fastsetting.skatt.beregn;

import java.util.UUID;

public abstract class AbstractUttrykk<T> implements Uttrykk<T> {

    private String id;
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
    public String id(UttrykkContext ctx) {
        if (id == null) {
            id = navn() + "#" + ctx.nyId();
        }
        return id;
    }
}
