package ske.fastsetting.skatt.uttrykk.tekst;

import com.sun.org.apache.xpath.internal.operations.Bool;

import ske.fastsetting.skatt.uttrykk.Uttrykk;
import ske.fastsetting.skatt.uttrykk.bolsk.BolskUttrykk;
import ske.fastsetting.skatt.uttrykk.HvisUttrykk;

public class TekstHvisUttrykk extends HvisUttrykk<String, TekstHvisUttrykk> implements TekstUttrykk {
    public static BrukUttrykk<String, TekstHvisUttrykk> hvis(Uttrykk<Boolean> bolskUttrykk) {
        return new BrukUttrykk<>(bolskUttrykk, new TekstHvisUttrykk());
    }
}

