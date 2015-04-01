package ske.fastsetting.skatt.uttrykk.skalSlettes;

import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.stedbundetBelop.StedbundetBelopUttrykk;

interface BruttoformueUttrykk extends BelopUttrykk {
    public StedbundetBelopUttrykk fritidseiendomHyttekommune();

    public StedbundetBelopUttrykk stedbundenFormue();

    public BelopUttrykk ovrigFormue();
}
