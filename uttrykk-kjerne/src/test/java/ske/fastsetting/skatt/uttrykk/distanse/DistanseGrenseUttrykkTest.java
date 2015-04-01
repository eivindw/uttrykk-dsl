package ske.fastsetting.skatt.uttrykk.distanse;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.distanse.DistanseGrenseUttrykk.begrens;
import static ske.fastsetting.skatt.uttrykk.distanse.KilometerUttrykk.km;

import org.junit.Test;

import ske.fastsetting.skatt.uttrykk.UttrykkContextImpl;

public class DistanseGrenseUttrykkTest {
    @Test
    public void skalBegrenseOppad() {
        DistanseUttrykk begrenset = begrens(km(50)).oppad(km(25));

        assertEquals(Distanse.km(25),UttrykkContextImpl.beregne(begrenset).verdi());
    }

    @Test
    public void skalBegrenseNedad() {
        DistanseUttrykk begrenset = begrens(km(50)).nedad(km(75));

        assertEquals(Distanse.km(75),UttrykkContextImpl.beregne(begrenset).verdi());
    }

}
