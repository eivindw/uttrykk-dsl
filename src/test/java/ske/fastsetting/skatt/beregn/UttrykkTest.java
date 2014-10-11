package ske.fastsetting.skatt.beregn;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.beregn.KrUttrykk.kr;
import static ske.fastsetting.skatt.beregn.MultiplikasjonsUttrykk.mult;
import static ske.fastsetting.skatt.beregn.ProsentUttrykk.prosent;
import static ske.fastsetting.skatt.beregn.SumUttrykk.sum;
import static ske.fastsetting.skatt.beregn.UttrykkContextImpl.beregne;
import static ske.fastsetting.skatt.beregn.UttrykkContextImpl.beregneOgBeskrive;

public class UttrykkTest {

    @Test
    public void tallUttrykk() {
        Uttrykk<Integer> en = kr(1);

        assertEquals(Integer.valueOf(1), en.eval(null));
    }

    @Test
    public void prosentUttrykk() {
        Uttrykk<Integer> ti = mult(kr(100), prosent(10));
        Uttrykk<Integer> tjue = sum(ti, ti);

        assertEquals(Integer.valueOf(20), beregne(tjue).verdi());
    }

    @Test
    public void boolskUttrykk() {
        Uttrykk<Integer> svar = new HvisUttrykk(
            new BoolskUttrykk(true),
            kr(10),
            kr(20)
        );

        System.out.println(beregneOgBeskrive(svar).uttrykk());
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

        UttrykkResultat<Integer> ctx = beregneOgBeskrive(sum);

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

    private void beskriv(UttrykkResultat<?> ctx) {
        System.out.println("Topp: " + ctx.start());
        System.out.println("Verdi: " + ctx.verdi());
        System.out.println("Uttrykk: " + ctx.uttrykk());
    }
}
