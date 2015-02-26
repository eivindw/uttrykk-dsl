package ske.fastsetting.skatt.uttrykk.tekst;

import ske.fastsetting.skatt.uttrykk.BolskUttrykk;
import ske.fastsetting.skatt.uttrykk.HvisUttrykk;

public class TekstHvisUttrykk extends HvisUttrykk<String, TekstHvisUttrykk> {
    public static BrukUttrykk<String, TekstHvisUttrykk> hvis(BolskUttrykk bolskUttrykk) {
        return new BrukUttrykk<>(bolskUttrykk, new TekstHvisUttrykk());
    }
}

