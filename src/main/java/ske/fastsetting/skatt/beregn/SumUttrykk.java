package ske.fastsetting.skatt.beregn;

import java.util.stream.Stream;

public class SumUttrykk implements Uttrykk<Integer> {
   private final Integer[] tall;

   public SumUttrykk(Integer... tall) {
      this.tall = tall;
   }

   @Override
   public Integer eval() {
      //return Stream.of(tall)
      return null;
   }
}
