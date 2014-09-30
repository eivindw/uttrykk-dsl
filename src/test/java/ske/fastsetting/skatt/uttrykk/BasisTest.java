package ske.fastsetting.skatt.uttrykk;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BasisTest {

    @Test
    public void evaluerUttrykk() {
        Uttrykk<Integer, Integer> summering = new SumUttrykk();

        int svar = summering.eval(1, 2, 3);

        assertEquals(6, svar);
    }
}
