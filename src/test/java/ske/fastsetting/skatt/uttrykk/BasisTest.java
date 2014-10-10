package ske.fastsetting.skatt.uttrykk;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Tall;
import ske.fastsetting.skatt.uttrykk.belop.BelopSumUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk;
import ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk;
import ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ListUttrykkBeskriver;

public class BasisTest {

   @Test
   public void prosentUttrykk() {
      final ProsentUttrykk satsTrygdeavgift =
         new ProsentUttrykk(Tall.prosent(8.2)).medNavn("Sats trygdeavgift");

      final BelopUttrykk sumLonn = BelopSumUttrykk.sum(
         KroneUttrykk.kr(60_000).medNavn("Lønn"),
         KroneUttrykk.kr(40_000).medNavn("Bonus")
      ).medNavn("Sum lønn");

      final BelopUttrykk trygdeavgift = sumLonn.multiplisertMed(satsTrygdeavgift);

      System.out.println(trygdeavgift.evaluer());

      final ListUttrykkBeskriver beskriver = new ListUttrykkBeskriver();
      trygdeavgift.beskrivGenerisk(beskriver);

      System.out.println(beskriver.liste());

      // 8 200 (trygdeavgift) = 100 000 (sum lønn) * 8.2 % (sats trygdeavgift)

      //Uttrykk<Integer> trygdeavgift =
   }
}
