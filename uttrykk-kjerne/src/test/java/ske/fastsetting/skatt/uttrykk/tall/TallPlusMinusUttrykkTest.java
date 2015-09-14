package ske.fastsetting.skatt.uttrykk.tall;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.TestUttrykkContext;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.tall.TallKonstantUttrykk.tall;

/**
 * Created by jorn ola birkeland on 01.06.15.
 */
public class TallPlusMinusUttrykkTest {
    @Test
    public void test() {
        TallUttrykk tall = tall(10).pluss(tall(4)).minus(tall(13)).pluss(tall(9));

        assertEquals(Tall.ukjent(BigDecimal.valueOf(10d)), TestUttrykkContext.beregne(tall).verdi());
    }
}
