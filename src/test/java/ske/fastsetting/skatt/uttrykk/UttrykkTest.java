package ske.fastsetting.skatt.uttrykk;

import org.junit.Test;
import ske.fastsetting.skatt.domene.Belop;
import ske.fastsetting.skatt.uttrykk.belop.BelopSumUttrykk;
import ske.fastsetting.skatt.uttrykk.belop.BelopUttrykk;

import static org.junit.Assert.assertEquals;
import static ske.fastsetting.skatt.uttrykk.UttrykkContextImpl.*;
import static ske.fastsetting.skatt.uttrykk.belop.BelopSumUttrykk.sum;
import static ske.fastsetting.skatt.uttrykk.belop.KroneUttrykk.kr;
import static ske.fastsetting.skatt.uttrykk.tall.ProsentUttrykk.prosent;
import static ske.fastsetting.skatt.uttrykk.uttrykkbeskriver.ConsoleUttrykkBeskriver.print;

public class UttrykkTest {

    @Test
    public void tallUttrykk() {
        Uttrykk<Belop, ?> en = kr(1);

        assertEquals(new Belop(1), en.eval(null));
    }

    @Test
    public void prosentUttrykk() {
        final BelopUttrykk<?> ti = kr(100).multiplisertMed(prosent(10));
        final BelopSumUttrykk tjue = sum(ti, ti);

        assertEquals(new Belop(20), beregne(tjue).verdi());

        print(beregne(tjue));
        System.out.println("\n###");
        print(beskrive(tjue));
        System.out.println("\n###");
        print(beregneOgBeskrive(tjue));
    }

    @Test
    public void sumUttrykk() {
        final BelopUttrykk lonn = kr(6);
        final BelopSumUttrykk sum = sum(
            kr(2),
            sum(
                lonn,
                kr(2)
            ),
            kr(4),
            lonn.multiplisertMed(prosent(50))
        );

        UttrykkResultat ctx = beregneOgBeskrive(sum);

        assertEquals(new Belop(17), ctx.verdi());

        print(ctx);
    }
}
