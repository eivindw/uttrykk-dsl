package ske.fastsetting.skatt.uttrykk.belop;

import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.StorsteAvUttrykk;

public class BelopStorsteAvUttrykk extends StorsteAvUttrykk<Belop, BelopStorsteAvUttrykk> implements BelopUttrykk {

    private BelopStorsteAvUttrykk(BelopUttrykk[] uttrykk) {
        super(uttrykk);
    }

    public static BelopStorsteAvUttrykk storsteAv(BelopUttrykk... uttrykk) {
        return new BelopStorsteAvUttrykk(uttrykk);
    }
}
