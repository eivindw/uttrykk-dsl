package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.Regel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public abstract class AbstractUttrykk<V, B extends Uttrykk<V,B,C>, C> implements Uttrykk<V, B, C> {

    private String id;
    private String navn = "";
    private Set<String> tags = new HashSet<>();
    private List<Regel> regler = new ArrayList<>();

    @SuppressWarnings("unchecked")
    private B self = (B) this;

    public B navn(String navn) {
        this.navn = navn;
        return self;
    }

    public B tag(String tag) {
        tags.add(tag);

        return self;
    }

    public B regler(Regel... regel) {
        Stream.of(regel).forEach(regler::add);

        return self;
    }

    public String navn() {
        return navn;
    }

    public Set<String> tags() {
        return tags;
    }

    public List<Regel> regler() {
        return regler;
    }

    @Override
    public String id(UttrykkContext<C> ctx) {
        if (id == null) {
            id = ctx.nyId();
        }
        return id;
    }
}
