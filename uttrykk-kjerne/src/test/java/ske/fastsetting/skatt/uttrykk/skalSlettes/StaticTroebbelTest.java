package ske.fastsetting.skatt.uttrykk.skalSlettes;

import org.junit.Ignore;
import org.junit.Test;

import ske.fastsetting.skatt.uttrykk.TestUttrykkContext;

@Ignore
public class StaticTroebbelTest {

    @Test
    public void test() {
        TestUttrykkContext.beregne(StaticTroebbel2.ut3).verdi();
    }
}
