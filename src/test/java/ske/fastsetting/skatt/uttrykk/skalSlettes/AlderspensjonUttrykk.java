package ske.fastsetting.skatt.uttrykk.skalSlettes;

import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

public interface AlderspensjonUttrykk extends BelopUttrykk {
    TallUttrykk pensjonAntallMaaneder();
    ProsentUttrykk pensjonsgrad();
}
