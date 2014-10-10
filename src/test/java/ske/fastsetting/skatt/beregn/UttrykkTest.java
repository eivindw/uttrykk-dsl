package ske.fastsetting.skatt.beregn;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.beregn.KrUttrykk.kr;
import static ske.fastsetting.skatt.beregn.SumUttrykk.sum;

public class UttrykkTest {

    @Test
    public void tallUttrykk() {
        Uttrykk<Integer> en = kr(1);

        assertEquals(1, en.eval().intValue());

        System.out.println(en.beskriv());
    }

    @Test
    public void sumUttrykk() {
        Uttrykk<Integer> sum = sum(
            kr(2).navn("utbytte"),
            sum(
                kr(1).navn("lønn"),
                kr(2).navn("bonus")
            ).navn("inntekt"),
            kr(4).navn("renter")
        ).navn("skatt");

        assertEquals(9, sum.eval().intValue());

        System.out.println(sum.beskriv());

        /*
            kr(9) = sum(kr(2), kr(3) = sum(kr(1), kr(2)), kr(4))

            map(
                "id", utbytte
                "id", lønn
                "id", bonus
                "id", inntekt -> id#lønn, id#bonus
                "id", renter
                "id", skatt -> id#utbytte, id#inntekt, id#renter
            )
         */
    }
}
