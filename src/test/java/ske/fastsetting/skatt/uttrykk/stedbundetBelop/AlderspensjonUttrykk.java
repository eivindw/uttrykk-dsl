package ske.fastsetting.skatt.uttrykk.stedbundetBelop;

import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.TallUttrykk;

/**
* Created by jorn ola birkeland on 26.02.15.
*/
public interface AlderspensjonUttrykk extends BelopUttrykk {
    TallUttrykk pensjonAntallMaaneder();
    ProsentUttrykk pensjonsgrad();
}
