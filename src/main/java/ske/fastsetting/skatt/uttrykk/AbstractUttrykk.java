package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.Regel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public abstract class AbstractUttrykk<V, B extends Uttrykk> implements Uttrykk<V, B> {

    private String id;
    private String navn;
    private Set<String> tags;
    private List<Regel> regler;

    @SuppressWarnings("unchecked")
    private B self = (B) this;

    @Override
    public B navn(String navn) {
        this.navn = navn;
        return self;
    }

    @Override
    public B tags(String... tags) {
        if (this.tags == null) {
            this.tags = new HashSet<>();
        }

        Stream.of(tags).forEach(this.tags::add);

        return self;
    }

    @Override
    public B regler(Regel... regel) {
        if (regler == null) {
            regler = new ArrayList<>();
        }

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
