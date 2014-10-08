package ske.fastsetting.skatt.uttrykk;

import java.util.Arrays;
import java.util.stream.Stream;

public class SumUttrykk implements Uttrykk<Integer, Integer> {

   @Override
   public Integer eval(Integer... argumenter) {
      return Stream.of(argumenter).reduce(0, (x, y) -> x + y);
   }

   @Override
   public String beskriv(Integer... argumenter) {
      if (argumenter.length == 0) {
         return "sum(arg1, arg2, arg3...)";
      } else {
         return eval(argumenter) + " = sum(" + Arrays.toString(argumenter) + ")";
      }
   }
}
