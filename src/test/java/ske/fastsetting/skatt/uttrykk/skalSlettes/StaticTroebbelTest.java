package ske.fastsetting.skatt.uttrykk.skalSlettes;

import org.junit.Ignore;
import org.junit.Test;
import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;

/**
 * Created by jorn ola birkeland on 22.03.15.
 */

@Ignore
public class StaticTroebbelTest {

    @Test
    public void test() {
        UttrykkContextImpl.beregne(StaticTroebbel2.ut3).verdi();
    }
}
