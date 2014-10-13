package ske.fastsetting.skatt.uttrykk;

import org.junit.Test;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.belop.BelopSumUttrykk.sum;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;

public class BasisTest {

   @Test
   public void prosentUttrykk() {
      final ProsentUttrykk satsTrygdeavgift = prosent(8.2).medNavn("Sats trygdeavgift");

      final BelopUttrykk sumLonn = sum(
          kr(60_000).medNavn("Lønn"),
          kr(40_000).medNavn("Bonus")
      ).medNavn("Sum lønn");

      final BelopUttrykk trygdeavgift = sumLonn.multiplisertMed(satsTrygdeavgift);

      assertEquals(8_200, trygdeavgift.evaluer().toInteger().intValue());
   }
}
