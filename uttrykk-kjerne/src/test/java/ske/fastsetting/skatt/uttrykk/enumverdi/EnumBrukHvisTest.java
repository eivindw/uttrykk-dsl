package ske.fastsetting.skatt.uttrykk.enumverdi;

import org.junit.Test;
import ske.fastsetting.skatt.uttrykk.TestUttrykkContext;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr0;
import static ske.fastsetting.skatt.uttrykk.enumverdi.EnumBrukHvisTest.TestEnum.A;
import static ske.fastsetting.skatt.uttrykk.enumverdi.EnumBrukHvisTest.TestEnum.B;
import static ske.fastsetting.skatt.uttrykk.enumverdi.EnumBrukHvisUttrykk.bruk;
import static ske.fastsetting.skatt.uttrykk.enumverdi.EnumKonstUttrykk.valg;

/**
 * Created by jorn ola birkeland on 16.10.15.
 */
public class EnumBrukHvisTest {

    public static enum TestEnum {
        A,
        B,
        C
    }


    @Test
    public void test() {
        EnumUttrykk<TestEnum> a =
                bruk(valg(A)).hvis(kr(-1).erStorreEnn(kr0()))
                .ellersBruk(valg(B));

        assertEquals(B, TestUttrykkContext.beregne(a).verdi());
     }
}
