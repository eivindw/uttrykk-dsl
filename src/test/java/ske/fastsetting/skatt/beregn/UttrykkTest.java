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

        assertEquals(1, en.eval().intValue());

        beskriv(en);
    }

    @Test
    public void prosentUttrykk() {
        Uttrykk<Integer> ti = mult(kr(100), prosent(10));

        System.out.println(ti.eval());
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

        assertEquals(17, sum.eval().intValue());

        beskriv(sum);
    }

    private void beskriv(Uttrykk<?> uttrykk) {
        UttrykkContext ctx = new UttrykkContext();
        String id = uttrykk.beskriv(ctx);
        Map<String, Map> map = ctx.uttrykk();

        System.out.println("Topp: " + id);
        System.out.println("Verdi: " + map.get(id).get("verdi"));
        System.out.println("Uttrykk: " + map);
    }
}
