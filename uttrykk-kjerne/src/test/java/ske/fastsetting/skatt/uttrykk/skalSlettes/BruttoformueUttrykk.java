package ske.fastsetting.skatt.uttrykk.skalSlettes;

import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.multibelop.MultiBelopUttrykk;

public interface BruttoformueUttrykk extends BelopUttrykk {
    public MultiBelopUttrykk fritidseiendomHyttekommune();

    public MultiBelopUttrykk stedbundenFormue();

    public BelopUttrykk ovrigFormue();
}
