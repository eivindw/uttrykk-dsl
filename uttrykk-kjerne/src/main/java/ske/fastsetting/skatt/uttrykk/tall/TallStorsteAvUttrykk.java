package ske.fastsetting.skatt.uttrykk.tall;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.CompareableUttrykk;
import ske.fastsetting.skatt.uttrykk.StorsteAvUttrykk;

public class TallStorsteAvUttrykk extends StorsteAvUttrykk<Tall,TallStorsteAvUttrykk> implements TallUttrykk {


    private TallStorsteAvUttrykk(CompareableUttrykk<Tall>[] uttrykk) {
        super(uttrykk);
    }

    public static TallStorsteAvUttrykk storsteAv(CompareableUttrykk<Tall>... uttrykk) {
        return new TallStorsteAvUttrykk(uttrykk);
    }

}
