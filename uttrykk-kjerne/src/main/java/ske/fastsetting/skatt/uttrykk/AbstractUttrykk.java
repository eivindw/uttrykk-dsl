package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.Hjemmel;
import ske.fastsetting.skatt.domene.Regel;

import java.util.*;
import java.util.stream.Stream;

public abstract class AbstractUttrykk<V, B extends AbstractUttrykk<V, B>> implements Uttrykk<V> {

    private String id = UUID.randomUUID().toString();
    private String navn;
    private Set<String> tags;
    private List<Regel> regler;
    private List<Hjemmel> hjemler;

    @SuppressWarnings("unchecked")
    protected B self = (B) this;

    public B navn(String navn) {
        if (this.navn == null) {
            this.navn = navn;
            return self;
        } else {
            throw new RuntimeException(
              String.format("Navn kan bare settes en gang! Gammel: %s ny: %s", this.navn, navn)
            );
        }
    }

    public B tags(String... tags) {
        if (this.tags == null) {
            this.tags = new HashSet<>();
        }

        Stream.of(tags).forEach(this.tags::add);

        return self;
    }

    @Deprecated
    public B regler(Regel... regel) {
        if (regler == null) {
            regler = new ArrayList<>();
        }

        Stream.of(regel).forEach(regler::add);

        return self;
    }

    public B hjemler(Hjemmel... hjemler) {
        regler(hjemler);

        if (this.hjemler == null) {
            this.hjemler = new ArrayList<>();
        }

        Stream.of(hjemler).forEach(this.hjemler::add);

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

    public List<Hjemmel> hjemler() {
        return hjemler;
    }

    @Override
    public String id() {
        return id;
    }
}
