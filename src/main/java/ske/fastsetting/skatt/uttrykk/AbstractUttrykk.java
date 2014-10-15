package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.Regel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public abstract class AbstractUttrykk<V, B extends Uttrykk> implements Uttrykk<V, B> {

    private String id;
    private String navn = "";
    private Set<String> tags = new HashSet<>();
    private List<Regel> regler = new ArrayList<>();

    @SuppressWarnings("unchecked")
    private B self = (B) this;

    @Override
    public B navn(String navn) {
        this.navn = navn;
        return self;
    }

    @Override
    public B tag(String tag) {
        tags.add(tag);

        return self;
    }

    @Override
    public B regler(Regel... regel) {
        Stream.of(regel).forEach(regler::add);

        return self;
    }

    @Override
    public String navn() {
        return navn;
    }

    @Override
    public Set<String> tags() {
        return tags;
    }

    @Override
    public List<Regel> regler() {
        return regler;
    }

    @Override
    public String id(UttrykkContext ctx) {
        if (id == null) {
            id = ctx.nyId();
        }
        return id;
    }
}
