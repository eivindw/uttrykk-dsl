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

   @Test
   public void beskrivUttrykk() {
      System.out.println(new SumUttrykk().beskriv(2, 3));

      System.out.println(new SumUttrykk().beskriv());
   }
}
