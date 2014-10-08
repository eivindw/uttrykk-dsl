package ske.fastsetting.skatt.uttrykk;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SumUttrykk implements Uttrykk<Integer, Integer> {

   @Override
   public Integer eval(Integer... argumenter) {
      return Stream.of(argumenter).reduce(0, (x, y) -> x + y);
   }

   @Override
   public String beskriv(Integer... argumenter) {
      return eval(argumenter) + " = sum(" + Stream.of(argumenter).map(Object::toString).collect(Collectors.joining(",")) + ")";
   }
}
