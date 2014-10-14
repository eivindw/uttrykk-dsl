package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.Regel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public abstract class RegelUttrykk<B, C> extends AbstractUttrykk<C> {

    protected String navn;
    private Set<String> tags;
    private List<Regel> regler;

    @SuppressWarnings("unchecked")
    private B self = (B) this;

    public B medNavn(String navn) {
        this.navn = navn;
        return self;
    }

    public B tag(String tag) {
        if (tags == null) {
            tags = new HashSet<>();
        }

        tags.add(tag);

        return self;
    }

    public B medRegel(Regel... regel) {
        if (regler == null) {
            regler = new ArrayList<>();
        }

        Stream.of(regel).forEach(regler::add);

        return self;
    }

    public String getNavn() {
        return navn;
    }

    public Set<String> getTags() {
        return tags;
    }

    public List<Regel> getRegler() {
        if (regler == null) {
            regler = new ArrayList<>();
        }

        return regler;
    }
/*
    public final void beskrivGenerisk(UttrykkBeskriver beskriver) {
        if (navn != null && !navn.isEmpty()) {
            beskriver = beskriver.overskrift(navn);
        }

        if (tags != null) {
            beskriver.tags(tags.toArray(new String[tags.size()]));
        }

        if (regler != null) {
            beskriver.regler(regler.toArray(new Regel[regler.size()]));
        }

        //beskrivGeneriskMedRegel(beskriver);
    }
*/
    //protected abstract void beskrivGeneriskMedRegel(UttrykkBeskriver beskriver);
}