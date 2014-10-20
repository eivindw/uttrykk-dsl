package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.Regel;

import java.util.List;
import java.util.Set;

public interface Uttrykk<V, C>  {
    V eval(UttrykkContext<C> ctx);

    String beskriv(UttrykkContext<C> ctx);

    String id(UttrykkContext<C> ctx);

    String navn();

    Set<String> tags();

    List<Regel> regler();
}
