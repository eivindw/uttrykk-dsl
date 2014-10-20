package ske.fastsetting.skatt.uttrykk;

import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk;

/**
* Created by jorn ola birkeland on 20.10.14.
*/
public class SkattegrunnlagHelper {
    public static KroneUttrykk<UttrykkTest.Skattegrunnlag> kr(int belop) {
        return KroneUttrykk.kr(belop);
    }

    public static ProsentUttrykk<UttrykkTest.Skattegrunnlag> prosent(double prosent) {
        return new ProsentUttrykk<>(Tall.prosent(prosent));
    }

}
