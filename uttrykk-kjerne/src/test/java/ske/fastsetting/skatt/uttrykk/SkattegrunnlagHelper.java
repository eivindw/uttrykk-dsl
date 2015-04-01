package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk;

public class SkattegrunnlagHelper {
    public static KroneUttrykk kr(int belop) {
        return KroneUttrykk.kr(belop);
    }

    public static ProsentUttrykk prosent(double prosent) {
        return new ProsentUttrykk(Tall.prosent(prosent));
    }

}
