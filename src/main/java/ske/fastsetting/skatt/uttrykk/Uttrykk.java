package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.Regel;

import java.util.List;
import java.util.Set;

public interface Uttrykk<V, B extends Uttrykk<V,B,C>, C>  {
    V eval(UttrykkContext<C> ctx);

    String beskriv(UttrykkContext<C> ctx);

    String id(UttrykkContext<C> ctx);

    String navn();

    B navn(String navn);

    Set<String> tags();

    B tags(String... tags);

    List<Regel> regler();

    B regler(Regel... regel);

}
