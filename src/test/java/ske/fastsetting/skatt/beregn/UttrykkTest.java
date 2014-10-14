package ske.fastsetting.skatt.beregn;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.beregn.HvisUttrykk.hvis;
import static ske.fastsetting.skatt.beregn.KrUttrykk.kr;
import static ske.fastsetting.skatt.beregn.MultiplikasjonsUttrykk.mult;
import static ske.fastsetting.skatt.beregn.ProsentUttrykk.prosent;
import static ske.fastsetting.skatt.beregn.SumUttrykk.sum;
import static ske.fastsetting.skatt.beregn.UttrykkContextImpl.*;
import static ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConsoleUttrykkBeskriver.print;

public class UttrykkTest {

    @Test
    public void tallUttrykk() {
        Uttrykk<Belop> en = kr(1);

        assertEquals(new Belop(1), en.eval(null));
    }

    @Test
    public void prosentUttrykk() {
        MultiplikasjonsUttrykk ti = mult(kr(100), prosent(10));
        SumUttrykk tjue = sum(ti, ti);

        assertEquals(new Belop(20), beregne(tjue).verdi());

        print(beregne(tjue));
        System.out.println("\n###");
        print(beskrive(tjue));
        System.out.println("\n###");
        print(beregneOgBeskrive(tjue));
    }

    @Test
    public void boolskUttrykk() {
        Uttrykk svar = hvis(new BoolskUttrykk(true))
            .saa(kr(10))
            .ellers(kr(20));

        assertEquals(new Belop(10), beregne(svar).verdi());
    }

    @Test
    public void sumUttrykk() {
        Uttrykk<Belop> lonn = kr(6).navn("lønn");
        Uttrykk sum = sum(
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

        UttrykkResultat ctx = beregneOgBeskrive(sum);

        assertEquals(new Belop(17), ctx.verdi());

        print(ctx);
    }
}
