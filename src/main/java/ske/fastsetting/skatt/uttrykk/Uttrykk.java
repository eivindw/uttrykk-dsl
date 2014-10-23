package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.Regel;

import java.util.List;
import java.util.Set;

public interface Uttrykk<V>  {
    V eval(UttrykkContext ctx);

    String beskriv(UttrykkContext ctx);

    String id(UttrykkContext ctx);

    String navn();

    Set<String> tags();

    List<Regel> regler();
}
