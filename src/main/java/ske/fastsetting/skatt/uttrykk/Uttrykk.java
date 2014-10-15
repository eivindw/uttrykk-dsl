package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.Regel;

import java.util.List;
import java.util.Set;

public interface Uttrykk<V, B> {

    V eval(UttrykkContext ctx);

    String beskriv(UttrykkContext ctx);

    String navn();

    B navn(String navn);

    Set<String> tags();

    B tag(String tag);

    List<Regel> regler();

    B regler(Regel... regel);

    String id(UttrykkContext ctx);
}
