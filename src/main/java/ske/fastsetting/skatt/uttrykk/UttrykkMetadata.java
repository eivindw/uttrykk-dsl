package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.Regel;

import java.util.List;
import java.util.Set;

/**
 * Created by jorn ola birkeland on 20.10.14.
 */
public interface UttrykkMetadata<V, B extends Uttrykk<V,C>, C> {
    B navn(String navn);

    Set<String> tags();

    B tags(String... tags);

    List<Regel> regler();

    B regler(Regel... regel);

}
