package ske.fastsetting.skatt.beregn;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.beregn.KrUttrykk.kr;
import static ske.fastsetting.skatt.beregn.MultiplikasjonsUttrykk.mult;
import static ske.fastsetting.skatt.beregn.ProsentUttrykk.prosent;
import static ske.fastsetting.skatt.beregn.SumUttrykk.sum;

public class UttrykkTest {

    @Test
    public void tallUttrykk() {
        Uttrykk<Integer> en = kr(1);

        assertEquals(Integer.valueOf(1), en.eval());
    }

    @Test
    public void prosentUttrykk() {
        Uttrykk<Integer> ti = mult(kr(100), prosent(10));
        Uttrykk<Integer> tjue = sum(ti, ti);

        assertEquals(Integer.valueOf(20), tjue.eval());
    }

    @Test
    public void sumUttrykk() {
        Uttrykk<Integer> lonn = kr(6).navn("lønn");
        Uttrykk<Integer> sum = sum(
            kr(2).navn("utbytte"),
            sum(
                lonn,
                kr(2).navn("bonus")
            ).navn("inntekt"),
            kr(4).navn("renter"),
            mult(
                lonn,
                prosent(50).navn("superprosent")
            ).navn("superskatt")
        ).navn("skatt");

        UttrykkContext<Integer> ctx = sum.evalCtx();

        assertEquals(Integer.valueOf(17), ctx.verdi());

        beskriv(ctx);

        /*

        {
          start: id0,
          uttrykk: {
            id0: {
              navn: "skatt",
              verdi: 17,
              beskrivelse: "sum(<id1, id2, id3, id4>)"
            },
            id1: {
              navn: "utbytte",
              verdi: 2,
              beskrivelse: "2 kr"
            },
            id2: {
              navn: "inntekt",
              verdi: 8,
              beskrivelse: "sum(<id5, id6>)"
            },
            ...
          }
        }

         */
    }

    private void beskriv(UttrykkContext<?> ctx) {
        String id = ctx.start();
        Map<String, Map> map = ctx.uttrykk();

        System.out.println("Topp: " + id);
        System.out.println("Verdi: " + map.get(id).get("verdi"));
        System.out.println("Uttrykk: " + map);
    }
}
